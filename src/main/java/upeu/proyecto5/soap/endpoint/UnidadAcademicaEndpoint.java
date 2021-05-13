package upeu.proyecto5.soap.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import pe.proyecto5.ws.services.unidadacademica.AddUnidadAcademicaRequest;
import pe.proyecto5.ws.services.unidadacademica.AddUnidadAcademicaResponse;
import pe.proyecto5.ws.services.unidadacademica.DeleteUnidadAcademicaRequest;
import pe.proyecto5.ws.services.unidadacademica.DeleteUnidadAcademicaResponse;
import pe.proyecto5.ws.services.unidadacademica.GetAllUnidadAcademicasRequest;
import pe.proyecto5.ws.services.unidadacademica.GetAllUnidadAcademicasResponse;
import pe.proyecto5.ws.services.unidadacademica.GetUnidadAcademicaByIdRequest;
import pe.proyecto5.ws.services.unidadacademica.GetUnidadAcademicaByIdResponse;
import pe.proyecto5.ws.services.unidadacademica.ObjectFactory;
import pe.proyecto5.ws.services.unidadacademica.UnidadAcademicaType;
import pe.proyecto5.ws.services.unidadacademica.ServiceStatus;
import pe.proyecto5.ws.services.unidadacademica.UpdateUnidadAcademicaRequest;
import pe.proyecto5.ws.services.unidadacademica.UpdateUnidadAcademicaResponse;
import upeu.proyecto5.soap.entity.UnidadAcademica;
import upeu.proyecto5.soap.service.UnidadAcademicaService;

@Endpoint
public class UnidadAcademicaEndpoint {

	private static final String NAMESPACE_URI="http://ws.proyecto5.pe/services/unidadAcademica/";
	
	@Autowired
	private UnidadAcademicaService service;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUnidadAcademicasRequest")
	@ResponsePayload
	public GetAllUnidadAcademicasResponse getAllMovies(@RequestPayload GetAllUnidadAcademicasRequest request) {
		GetAllUnidadAcademicasResponse response = new GetAllUnidadAcademicasResponse();
		List<UnidadAcademicaType> unidadAcademicaTypeList = new ArrayList<UnidadAcademicaType>();
		List<UnidadAcademica> unidadAcademicaList = service.lista();
		for (UnidadAcademica unidadAcademica : unidadAcademicaList) {
			UnidadAcademicaType unidadAcademicaType = new UnidadAcademicaType();
			BeanUtils.copyProperties(unidadAcademica, unidadAcademicaType);
			unidadAcademicaTypeList.add(unidadAcademicaType);
		}
		response.getUnidadAcademicaType().addAll(unidadAcademicaTypeList);
		
		return response;

	}
	
	@ResponsePayload
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUnidadAcademicaByIdRequest")
	public GetUnidadAcademicaByIdResponse buscarById(@RequestPayload GetUnidadAcademicaByIdRequest request) {
		var objectFactory = new ObjectFactory();
		var response = objectFactory.createGetUnidadAcademicaByIdResponse();
		Optional<UnidadAcademica> unidadAcademica = service.buscarById(request.getId());
		System.out.println("----id----");
		System.out.println(request.getId());
		if(unidadAcademica.isEmpty()) {
			return null;
		}
		var unidadAcademicaType = new UnidadAcademicaType();
		BeanUtils.copyProperties(unidadAcademica.get(), unidadAcademicaType);
		response.setUnidadAcademicaType(unidadAcademicaType);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUnidadAcademicaRequest")
	@ResponsePayload
	public AddUnidadAcademicaResponse addMovie(@RequestPayload AddUnidadAcademicaRequest request) {
		AddUnidadAcademicaResponse response = new AddUnidadAcademicaResponse();
		UnidadAcademicaType newUnidadAcademicaType = new UnidadAcademicaType();
		ServiceStatus serviceStatus = new ServiceStatus();

		UnidadAcademica newUnidadAcademicaEntity = new UnidadAcademica(
				request.getNombre(),
				request.getPadreId(),
				request.getTipoUaId()
				);
		UnidadAcademica savedUnidadAcademicaEntity = service.registrar(newUnidadAcademicaEntity);

		if (savedUnidadAcademicaEntity == null) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while adding UnidadAcademica");
		} else {

			BeanUtils.copyProperties(savedUnidadAcademicaEntity, newUnidadAcademicaType);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Added Successfully");
		}

		response.setUnidadAcademicaType(newUnidadAcademicaType);
		response.setServiceStatus(serviceStatus);
		return response;

	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUnidadAcademicaRequest")
	@ResponsePayload
	public UpdateUnidadAcademicaResponse updateMovie(@RequestPayload UpdateUnidadAcademicaRequest request) {
		UpdateUnidadAcademicaResponse response = new UpdateUnidadAcademicaResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		
		Optional<UnidadAcademica> unidadAcademica = service.buscarById(request.getUnidadAcademicaType().getId());
		
		unidadAcademica.get().setNombre(request.getUnidadAcademicaType().getNombre());
		unidadAcademica.get().setPadre_id(request.getUnidadAcademicaType().getPadreId());
		unidadAcademica.get().setTipo_ua_id(request.getUnidadAcademicaType().getTipoUaId());
		service.actualizar(unidadAcademica.get());
		
		var unidadAcademicaType = new UnidadAcademicaType();
		
		if (unidadAcademica.isEmpty()) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while updating UnidadAcademica");
		} else {
			
			BeanUtils.copyProperties(unidadAcademica.get(), unidadAcademicaType);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("UnidadAcademica updated Successfully");
		}
		
		response.setUnidadAcademicaType(unidadAcademicaType);
		response.setServiceStatus(serviceStatus);
		return response;
		
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUnidadAcademicaRequest")
	@ResponsePayload
	public DeleteUnidadAcademicaResponse deleteMovie(@RequestPayload DeleteUnidadAcademicaRequest request) {
		DeleteUnidadAcademicaResponse response = new DeleteUnidadAcademicaResponse();
		ServiceStatus serviceStatus = new ServiceStatus();

		Optional<UnidadAcademica> unidadAcademica = service.buscarById(request.getId());

		if (unidadAcademica.isEmpty()) {
			serviceStatus.setStatusCode("FAIL");
			serviceStatus.setMessage("Exception while deleting Entity id = " + request.getId());
		} else {
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Deleted Successfully");
		}

		response.setServiceStatus(serviceStatus);
		return response;
	}
	
}

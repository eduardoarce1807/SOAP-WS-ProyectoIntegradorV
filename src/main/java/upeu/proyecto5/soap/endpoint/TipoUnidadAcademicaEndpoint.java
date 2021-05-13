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

import pe.proyecto5.ws.services.tipounidadacademica.AddTipoUnidadAcademicaRequest;
import pe.proyecto5.ws.services.tipounidadacademica.AddTipoUnidadAcademicaResponse;
import pe.proyecto5.ws.services.tipounidadacademica.DeleteTipoUnidadAcademicaRequest;
import pe.proyecto5.ws.services.tipounidadacademica.DeleteTipoUnidadAcademicaResponse;
import pe.proyecto5.ws.services.tipounidadacademica.GetAllTipoUnidadAcademicasRequest;
import pe.proyecto5.ws.services.tipounidadacademica.GetAllTipoUnidadAcademicasResponse;
import pe.proyecto5.ws.services.tipounidadacademica.GetTipoUnidadAcademicaByIdRequest;
import pe.proyecto5.ws.services.tipounidadacademica.GetTipoUnidadAcademicaByIdResponse;
import pe.proyecto5.ws.services.tipounidadacademica.ObjectFactory;
import pe.proyecto5.ws.services.tipounidadacademica.TipoUnidadAcademicaType;
import pe.proyecto5.ws.services.tipounidadacademica.ServiceStatus;
import pe.proyecto5.ws.services.tipounidadacademica.UpdateTipoUnidadAcademicaRequest;
import pe.proyecto5.ws.services.tipounidadacademica.UpdateTipoUnidadAcademicaResponse;
import upeu.proyecto5.soap.entity.TipoUnidadAcademica;
import upeu.proyecto5.soap.service.TipoUnidadAcademicaService;

@Endpoint
public class TipoUnidadAcademicaEndpoint {

	private static final String NAMESPACE_URI="http://ws.proyecto5.pe/services/tipoUnidadAcademica/";
	
	@Autowired
	private TipoUnidadAcademicaService service;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllTipoUnidadAcademicasRequest")
	@ResponsePayload
	public GetAllTipoUnidadAcademicasResponse getAllMovies(@RequestPayload GetAllTipoUnidadAcademicasRequest request) {
		GetAllTipoUnidadAcademicasResponse response = new GetAllTipoUnidadAcademicasResponse();
		List<TipoUnidadAcademicaType> tipoUnidadAcademicaTypeList = new ArrayList<TipoUnidadAcademicaType>();
		List<TipoUnidadAcademica> tipoUnidadAcademicaList = service.lista();
		for (TipoUnidadAcademica tipoUnidadAcademica : tipoUnidadAcademicaList) {
			TipoUnidadAcademicaType tipoUnidadAcademicaType = new TipoUnidadAcademicaType();
			BeanUtils.copyProperties(tipoUnidadAcademica, tipoUnidadAcademicaType);
			tipoUnidadAcademicaTypeList.add(tipoUnidadAcademicaType);
		}
		response.getTipoUnidadAcademicaType().addAll(tipoUnidadAcademicaTypeList);
		
		return response;

	}
	
	@ResponsePayload
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTipoUnidadAcademicaByIdRequest")
	public GetTipoUnidadAcademicaByIdResponse buscarById(@RequestPayload GetTipoUnidadAcademicaByIdRequest request) {
		var objectFactory = new ObjectFactory();
		var response = objectFactory.createGetTipoUnidadAcademicaByIdResponse();
		Optional<TipoUnidadAcademica> tipoUnidadAcademica = service.buscarById(request.getId());
		System.out.println("----id----");
		System.out.println(request.getId());
		if(tipoUnidadAcademica.isEmpty()) {
			return null;
		}
		var tipoUnidadAcademicaType = new TipoUnidadAcademicaType();
		BeanUtils.copyProperties(tipoUnidadAcademica.get(), tipoUnidadAcademicaType);
		response.setTipoUnidadAcademicaType(tipoUnidadAcademicaType);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addTipoUnidadAcademicaRequest")
	@ResponsePayload
	public AddTipoUnidadAcademicaResponse addMovie(@RequestPayload AddTipoUnidadAcademicaRequest request) {
		AddTipoUnidadAcademicaResponse response = new AddTipoUnidadAcademicaResponse();
		TipoUnidadAcademicaType newTipoUnidadAcademicaType = new TipoUnidadAcademicaType();
		ServiceStatus serviceStatus = new ServiceStatus();

		TipoUnidadAcademica newTipoUnidadAcademicaEntity = new TipoUnidadAcademica(
				request.getNombre()
				);
		TipoUnidadAcademica savedTipoUnidadAcademicaEntity = service.registrar(newTipoUnidadAcademicaEntity);

		if (savedTipoUnidadAcademicaEntity == null) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while adding TipoUnidadAcademica");
		} else {

			BeanUtils.copyProperties(savedTipoUnidadAcademicaEntity, newTipoUnidadAcademicaType);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Added Successfully");
		}

		response.setTipoUnidadAcademicaType(newTipoUnidadAcademicaType);
		response.setServiceStatus(serviceStatus);
		return response;

	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateTipoUnidadAcademicaRequest")
	@ResponsePayload
	public UpdateTipoUnidadAcademicaResponse updateMovie(@RequestPayload UpdateTipoUnidadAcademicaRequest request) {
		UpdateTipoUnidadAcademicaResponse response = new UpdateTipoUnidadAcademicaResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		
		Optional<TipoUnidadAcademica> tipoUnidadAcademica = service.buscarById(request.getTipoUnidadAcademicaType().getId());
		
		tipoUnidadAcademica.get().setNombre(request.getTipoUnidadAcademicaType().getNombre());
		
		service.actualizar(tipoUnidadAcademica.get());
		
		var tipoUnidadAcademicaType = new TipoUnidadAcademicaType();
		
		if (tipoUnidadAcademica.isEmpty()) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while updating TipoUnidadAcademica");
		} else {
			
			BeanUtils.copyProperties(tipoUnidadAcademica.get(), tipoUnidadAcademicaType);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("TipoUnidadAcademica updated Successfully");
		}
		
		response.setTipoUnidadAcademicaType(tipoUnidadAcademicaType);
		response.setServiceStatus(serviceStatus);
		return response;
		
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteTipoUnidadAcademicaRequest")
	@ResponsePayload
	public DeleteTipoUnidadAcademicaResponse deleteMovie(@RequestPayload DeleteTipoUnidadAcademicaRequest request) {
		DeleteTipoUnidadAcademicaResponse response = new DeleteTipoUnidadAcademicaResponse();
		ServiceStatus serviceStatus = new ServiceStatus();

		boolean flag = service.eliminar(request.getId());

		if (flag == false) {
			serviceStatus.setStatusCode("FAIL");
			serviceStatus.setMessage("Exception while deletint Entity id=" + request.getId());
		} else {
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Deleted Successfully");
		}

		response.setServiceStatus(serviceStatus);
		return response;
	}
	
}

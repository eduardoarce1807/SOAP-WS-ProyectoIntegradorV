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

import pe.proyecto5.ws.services.docente.AddDocenteRequest;
import pe.proyecto5.ws.services.docente.AddDocenteResponse;
import pe.proyecto5.ws.services.docente.DeleteDocenteRequest;
import pe.proyecto5.ws.services.docente.DeleteDocenteResponse;
import pe.proyecto5.ws.services.docente.GetAllDocentesRequest;
import pe.proyecto5.ws.services.docente.GetAllDocentesResponse;
import pe.proyecto5.ws.services.docente.GetDocenteByIdRequest;
import pe.proyecto5.ws.services.docente.GetDocenteByIdResponse;
import pe.proyecto5.ws.services.docente.ObjectFactory;
import pe.proyecto5.ws.services.docente.DocenteType;
import pe.proyecto5.ws.services.docente.ServiceStatus;
import pe.proyecto5.ws.services.docente.UpdateDocenteRequest;
import pe.proyecto5.ws.services.docente.UpdateDocenteResponse;
import upeu.proyecto5.soap.entity.Docente;
import upeu.proyecto5.soap.service.DocenteService;

@Endpoint
public class DocenteEndpoint {

	private static final String NAMESPACE_URI="http://ws.proyecto5.pe/services/docente/";
	
	@Autowired
	private DocenteService service;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllDocentesRequest")
	@ResponsePayload
	public GetAllDocentesResponse getAllMovies(@RequestPayload GetAllDocentesRequest request) {
		GetAllDocentesResponse response = new GetAllDocentesResponse();
		List<DocenteType> docenteTypeList = new ArrayList<DocenteType>();
		List<Docente> docenteList = service.lista();
		for (Docente docente : docenteList) {
			DocenteType docenteType = new DocenteType();
			BeanUtils.copyProperties(docente, docenteType);
			docenteTypeList.add(docenteType);
		}
		response.getDocenteType().addAll(docenteTypeList);
		
		return response;

	}
	
	@ResponsePayload
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDocenteByIdRequest")
	public GetDocenteByIdResponse buscarById(@RequestPayload GetDocenteByIdRequest request) {
		var objectFactory = new ObjectFactory();
		var response = objectFactory.createGetDocenteByIdResponse();
		Optional<Docente> docente = service.buscarById(request.getId());
		System.out.println("----id----");
		System.out.println(request.getId());
		if(docente.isEmpty()) {
			return null;
		}
		var docenteType = new DocenteType();
		BeanUtils.copyProperties(docente.get(), docenteType);
		response.setDocenteType(docenteType);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addDocenteRequest")
	@ResponsePayload
	public AddDocenteResponse addMovie(@RequestPayload AddDocenteRequest request) {
		AddDocenteResponse response = new AddDocenteResponse();
		DocenteType newDocenteType = new DocenteType();
		ServiceStatus serviceStatus = new ServiceStatus();

		Docente newDocenteEntity = new Docente(
				request.getCodigo(),
				request.getOrdinario(),
				request.getCategoriaId(),
				request.getUaId()
				);
		Docente savedDocenteEntity = service.registrar(newDocenteEntity);

		if (savedDocenteEntity == null) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while adding Docente");
		} else {

			BeanUtils.copyProperties(savedDocenteEntity, newDocenteType);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Added Successfully");
		}

		response.setDocenteType(newDocenteType);
		response.setServiceStatus(serviceStatus);
		return response;

	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateDocenteRequest")
	@ResponsePayload
	public UpdateDocenteResponse updateMovie(@RequestPayload UpdateDocenteRequest request) {
		UpdateDocenteResponse response = new UpdateDocenteResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		
		Optional<Docente> docente = service.buscarById(request.getDocenteType().getId());
		
		docente.get().setCodigo(request.getDocenteType().getCodigo());
		docente.get().setOrdinario(request.getDocenteType().getOrdinario());
		docente.get().setCategoria_id(request.getDocenteType().getCategoriaId());
		docente.get().setUa_id(request.getDocenteType().getUaId());
		
		service.actualizar(docente.get());
		
		var docenteType = new DocenteType();
		
		if (docente.isEmpty()) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while updating Docente");
		} else {
			
			BeanUtils.copyProperties(docente.get(), docenteType);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Docente updated Successfully");
		}
		
		response.setDocenteType(docenteType);
		response.setServiceStatus(serviceStatus);
		return response;
		
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteDocenteRequest")
	@ResponsePayload
	public DeleteDocenteResponse deleteMovie(@RequestPayload DeleteDocenteRequest request) {
		DeleteDocenteResponse response = new DeleteDocenteResponse();
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

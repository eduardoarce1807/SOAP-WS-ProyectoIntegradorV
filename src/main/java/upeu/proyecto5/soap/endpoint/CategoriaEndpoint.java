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

import pe.proyecto5.ws.services.categoria.AddCategoriaRequest;
import pe.proyecto5.ws.services.categoria.AddCategoriaResponse;
import pe.proyecto5.ws.services.categoria.DeleteCategoriaRequest;
import pe.proyecto5.ws.services.categoria.DeleteCategoriaResponse;
import pe.proyecto5.ws.services.categoria.GetAllCategoriasRequest;
import pe.proyecto5.ws.services.categoria.GetAllCategoriasResponse;
import pe.proyecto5.ws.services.categoria.GetCategoriaByIdRequest;
import pe.proyecto5.ws.services.categoria.GetCategoriaByIdResponse;
import pe.proyecto5.ws.services.categoria.ObjectFactory;
import pe.proyecto5.ws.services.categoria.CategoriaType;
import pe.proyecto5.ws.services.categoria.ServiceStatus;
import pe.proyecto5.ws.services.categoria.UpdateCategoriaRequest;
import pe.proyecto5.ws.services.categoria.UpdateCategoriaResponse;
import upeu.proyecto5.soap.entity.Categoria;
import upeu.proyecto5.soap.service.CategoriaService;

@Endpoint
public class CategoriaEndpoint {

	private static final String NAMESPACE_URI="http://ws.proyecto5.pe/services/categoria/";
	
	@Autowired
	private CategoriaService service;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCategoriasRequest")
	@ResponsePayload
	public GetAllCategoriasResponse getAllMovies(@RequestPayload GetAllCategoriasRequest request) {
		GetAllCategoriasResponse response = new GetAllCategoriasResponse();
		List<CategoriaType> categoriaTypeList = new ArrayList<CategoriaType>();
		List<Categoria> categoriaList = service.lista();
		for (Categoria categoria : categoriaList) {
			CategoriaType categoriaType = new CategoriaType();
			BeanUtils.copyProperties(categoria, categoriaType);
			categoriaTypeList.add(categoriaType);
		}
		response.getCategoriaType().addAll(categoriaTypeList);
		
		return response;

	}
	
	@ResponsePayload
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCategoriaByIdRequest")
	public GetCategoriaByIdResponse buscarById(@RequestPayload GetCategoriaByIdRequest request) {
		var objectFactory = new ObjectFactory();
		var response = objectFactory.createGetCategoriaByIdResponse();
		Optional<Categoria> categoria = service.buscarById(request.getId());
		System.out.println("----id----");
		System.out.println(request.getId());
		if(categoria.isEmpty()) {
			return null;
		}
		var categoriaType = new CategoriaType();
		BeanUtils.copyProperties(categoria.get(), categoriaType);
		response.setCategoriaType(categoriaType);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addCategoriaRequest")
	@ResponsePayload
	public AddCategoriaResponse addMovie(@RequestPayload AddCategoriaRequest request) {
		AddCategoriaResponse response = new AddCategoriaResponse();
		CategoriaType newCategoriaType = new CategoriaType();
		ServiceStatus serviceStatus = new ServiceStatus();

		Categoria newCategoriaEntity = new Categoria(
				request.getNombre(),
				request.getDuracion()
				);
		Categoria savedCategoriaEntity = service.registrar(newCategoriaEntity);

		if (savedCategoriaEntity == null) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while adding Categoria");
		} else {

			BeanUtils.copyProperties(savedCategoriaEntity, newCategoriaType);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Added Successfully");
		}

		response.setCategoriaType(newCategoriaType);
		response.setServiceStatus(serviceStatus);
		return response;

	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCategoriaRequest")
	@ResponsePayload
	public UpdateCategoriaResponse updateMovie(@RequestPayload UpdateCategoriaRequest request) {
		UpdateCategoriaResponse response = new UpdateCategoriaResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		
		Optional<Categoria> categoria = service.buscarById(request.getCategoriaType().getId());
		
		categoria.get().setNombre(request.getCategoriaType().getNombre());
		categoria.get().setDuracion(request.getCategoriaType().getDuracion());
		
		service.actualizar(categoria.get());
		
		var categoriaType = new CategoriaType();
		
		if (categoria.isEmpty()) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while updating Categoria");
		} else {
			
			BeanUtils.copyProperties(categoria.get(), categoriaType);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Categoria updated Successfully");
		}
		
		response.setCategoriaType(categoriaType);
		response.setServiceStatus(serviceStatus);
		return response;
		
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCategoriaRequest")
	@ResponsePayload
	public DeleteCategoriaResponse deleteMovie(@RequestPayload DeleteCategoriaRequest request) {
		DeleteCategoriaResponse response = new DeleteCategoriaResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		
		Optional<Categoria> categoria = service.buscarById(request.getId());

		if (categoria.isEmpty()) {
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

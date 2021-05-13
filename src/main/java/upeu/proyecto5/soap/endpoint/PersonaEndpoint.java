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

import pe.proyecto5.ws.services.persona.AddPersonaRequest;
import pe.proyecto5.ws.services.persona.AddPersonaResponse;
import pe.proyecto5.ws.services.persona.DeletePersonaRequest;
import pe.proyecto5.ws.services.persona.DeletePersonaResponse;
import pe.proyecto5.ws.services.persona.GetAllPersonasRequest;
import pe.proyecto5.ws.services.persona.GetAllPersonasResponse;
import pe.proyecto5.ws.services.persona.GetPersonaByIdRequest;
import pe.proyecto5.ws.services.persona.GetPersonaByIdResponse;
import pe.proyecto5.ws.services.persona.ObjectFactory;
import pe.proyecto5.ws.services.persona.PersonaType;
import pe.proyecto5.ws.services.persona.ServiceStatus;
import pe.proyecto5.ws.services.persona.UpdatePersonaRequest;
import pe.proyecto5.ws.services.persona.UpdatePersonaResponse;
import upeu.proyecto5.soap.entity.Persona;
import upeu.proyecto5.soap.service.PersonaService;

@Endpoint
public class PersonaEndpoint {

	private static final String NAMESPACE_URI="http://ws.proyecto5.pe/services/persona/";
	
	@Autowired
	private PersonaService service;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPersonasRequest")
	@ResponsePayload
	public GetAllPersonasResponse getAllMovies(@RequestPayload GetAllPersonasRequest request) {
		GetAllPersonasResponse response = new GetAllPersonasResponse();
		List<PersonaType> personaTypeList = new ArrayList<PersonaType>();
		List<Persona> personaList = service.lista();
		for (Persona persona : personaList) {
			PersonaType personaType = new PersonaType();
			BeanUtils.copyProperties(persona, personaType);
			personaTypeList.add(personaType);
		}
		response.getPersonaType().addAll(personaTypeList);
		
		return response;

	}
	
	@ResponsePayload
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonaByIdRequest")
	public GetPersonaByIdResponse buscarById(@RequestPayload GetPersonaByIdRequest request) {
		var objectFactory = new ObjectFactory();
		var response = objectFactory.createGetPersonaByIdResponse();
		Optional<Persona> persona = service.buscarById(request.getId());
		System.out.println("----id----");
		System.out.println(request.getId());
		if(persona.isEmpty()) {
			return null;
		}
		var personaType = new PersonaType();
		BeanUtils.copyProperties(persona.get(), personaType);
		response.setPersonaType(personaType);
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addPersonaRequest")
	@ResponsePayload
	public AddPersonaResponse addMovie(@RequestPayload AddPersonaRequest request) {
		AddPersonaResponse response = new AddPersonaResponse();
		PersonaType newPersonaType = new PersonaType();
		ServiceStatus serviceStatus = new ServiceStatus();

		Persona newPersonaEntity = new Persona(
				request.getNombre(),
				request.getApellido(),
				request.getDni(),
				request.getTelefono(),
				request.getEmail()
				);
		Persona savedPersonaEntity = service.registrar(newPersonaEntity);

		if (savedPersonaEntity == null) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while adding Persona");
		} else {

			BeanUtils.copyProperties(savedPersonaEntity, newPersonaType);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Added Successfully");
		}

		response.setPersonaType(newPersonaType);
		response.setServiceStatus(serviceStatus);
		return response;

	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePersonaRequest")
	@ResponsePayload
	public UpdatePersonaResponse updateMovie(@RequestPayload UpdatePersonaRequest request) {
		UpdatePersonaResponse response = new UpdatePersonaResponse();
		ServiceStatus serviceStatus = new ServiceStatus();
		
		Optional<Persona> persona = service.buscarById(request.getPersonaType().getId());
		
		persona.get().setNombre(request.getPersonaType().getNombre());
		persona.get().setApellido(request.getPersonaType().getApellido());
		persona.get().setDni(request.getPersonaType().getDni());
		persona.get().setTelefono(request.getPersonaType().getTelefono());
		persona.get().setEmail(request.getPersonaType().getEmail());
		
		service.actualizar(persona.get());
		
		var personaType = new PersonaType();
		
		if (persona.isEmpty()) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while updating Persona");
		} else {
			
			BeanUtils.copyProperties(persona.get(), personaType);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Persona updated Successfully");
		}
		
		response.setPersonaType(personaType);
		response.setServiceStatus(serviceStatus);
		return response;
		
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePersonaRequest")
	@ResponsePayload
	public DeletePersonaResponse deleteMovie(@RequestPayload DeletePersonaRequest request) {
		DeletePersonaResponse response = new DeletePersonaResponse();
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

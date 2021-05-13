package upeu.proyecto5.soap.service;

import java.util.List;
import java.util.Optional;

import upeu.proyecto5.soap.entity.Persona;

public interface PersonaService {

	public List<Persona> lista();
	
	public Optional<Persona> buscarById(Integer id);
	
	public Persona registrar(Persona object);
	
	public Persona actualizar(Persona object);
	
	public Boolean eliminar(Integer id);
	
}

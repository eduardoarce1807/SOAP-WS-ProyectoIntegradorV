package upeu.proyecto5.soap.service;

import java.util.List;
import java.util.Optional;

import upeu.proyecto5.soap.entity.Docente;

public interface DocenteService {

	public List<Docente> lista();
	
	public Optional<Docente> buscarById(Integer id);
	
	public Docente registrar(Docente object);
	
	public Docente actualizar(Docente object);
	
	public Boolean eliminar(Integer id);
	
}
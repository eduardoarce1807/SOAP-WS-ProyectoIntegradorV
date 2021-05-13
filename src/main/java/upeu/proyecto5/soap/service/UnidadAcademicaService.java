package upeu.proyecto5.soap.service;

import java.util.List;
import java.util.Optional;

import upeu.proyecto5.soap.entity.UnidadAcademica;

public interface UnidadAcademicaService {

	public List<UnidadAcademica> lista();
	
	public Optional<UnidadAcademica> buscarById(Integer id);
	
	public UnidadAcademica registrar(UnidadAcademica object);
	
	public UnidadAcademica actualizar(UnidadAcademica object);
	
	public Boolean eliminar(Integer id);
	
}
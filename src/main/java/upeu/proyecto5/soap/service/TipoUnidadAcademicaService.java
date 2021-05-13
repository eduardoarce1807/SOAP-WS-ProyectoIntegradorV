package upeu.proyecto5.soap.service;

import java.util.List;
import java.util.Optional;

import upeu.proyecto5.soap.entity.TipoUnidadAcademica;

public interface TipoUnidadAcademicaService {

	public List<TipoUnidadAcademica> lista();
	
	public Optional<TipoUnidadAcademica> buscarById(Integer id);
	
	public TipoUnidadAcademica registrar(TipoUnidadAcademica object);
	
	public TipoUnidadAcademica actualizar(TipoUnidadAcademica object);
	
	public Boolean eliminar(Integer id);
	
}
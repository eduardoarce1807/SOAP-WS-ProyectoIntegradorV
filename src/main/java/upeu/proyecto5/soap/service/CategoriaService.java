package upeu.proyecto5.soap.service;

import java.util.List;
import java.util.Optional;

import upeu.proyecto5.soap.entity.Categoria;

public interface CategoriaService {

	public List<Categoria> lista();
	
	public Optional<Categoria> buscarById(Integer id);
	
	public Categoria registrar(Categoria object);
	
	public Categoria actualizar(Categoria object);
	
	public Boolean eliminar(Integer id);
	
}
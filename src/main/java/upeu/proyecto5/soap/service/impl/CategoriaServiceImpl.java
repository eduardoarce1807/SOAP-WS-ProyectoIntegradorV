package upeu.proyecto5.soap.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import upeu.proyecto5.soap.entity.Categoria;
import upeu.proyecto5.soap.repository.CategoriaRepository;
import upeu.proyecto5.soap.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	@Transactional(readOnly = true)
	@Override
	public List<Categoria> lista() {
		return repo.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Categoria> buscarById(Integer id) {
		return repo.findById(id);
	}

	@Transactional
	@Override
	public Categoria registrar(Categoria object) {
		return repo.save(object);
	}

	@Transactional
	@Override
	public Categoria actualizar(Categoria object) {
		return repo.save(object);
	}

	@Transactional
	@Override
	public Boolean eliminar(Integer id) {
		try {
            repo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
	}

}

package upeu.proyecto5.soap.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import upeu.proyecto5.soap.entity.UnidadAcademica;
import upeu.proyecto5.soap.repository.UnidadAcademicaRepository;
import upeu.proyecto5.soap.service.UnidadAcademicaService;

@Service
public class UnidadAcademicaServiceImpl implements UnidadAcademicaService {

	@Autowired
	private UnidadAcademicaRepository repo;
	
	@Transactional(readOnly = true)
	@Override
	public List<UnidadAcademica> lista() {
		return repo.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<UnidadAcademica> buscarById(Integer id) {
		return repo.findById(id);
	}

	@Transactional
	@Override
	public UnidadAcademica registrar(UnidadAcademica object) {
		return repo.save(object);
	}

	@Transactional
	@Override
	public UnidadAcademica actualizar(UnidadAcademica object) {
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

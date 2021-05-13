package upeu.proyecto5.soap.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import upeu.proyecto5.soap.entity.TipoUnidadAcademica;
import upeu.proyecto5.soap.repository.TipoUnidadAcademicaRepository;
import upeu.proyecto5.soap.service.TipoUnidadAcademicaService;

@Service
public class TipoUnidadAcademicaServiceImpl implements TipoUnidadAcademicaService {

	@Autowired
	private TipoUnidadAcademicaRepository repo;
	
	@Transactional(readOnly = true)
	@Override
	public List<TipoUnidadAcademica> lista() {
		return repo.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<TipoUnidadAcademica> buscarById(Integer id) {
		return repo.findById(id);
	}

	@Transactional
	@Override
	public TipoUnidadAcademica registrar(TipoUnidadAcademica object) {
		return repo.save(object);
	}

	@Transactional
	@Override
	public TipoUnidadAcademica actualizar(TipoUnidadAcademica object) {
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

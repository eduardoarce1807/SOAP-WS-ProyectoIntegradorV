package upeu.proyecto5.soap.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import upeu.proyecto5.soap.entity.Docente;
import upeu.proyecto5.soap.repository.DocenteRepository;
import upeu.proyecto5.soap.service.DocenteService;

@Service
public class DocenteServiceImpl implements DocenteService {

	@Autowired
	private DocenteRepository repo;
	
	@Transactional(readOnly = true)
	@Override
	public List<Docente> lista() {
		return repo.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Docente> buscarById(Integer id) {
		return repo.findById(id);
	}

	@Transactional
	@Override
	public Docente registrar(Docente object) {
		return repo.save(object);
	}

	@Transactional
	@Override
	public Docente actualizar(Docente object) {
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
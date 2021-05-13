package upeu.proyecto5.soap.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import upeu.proyecto5.soap.entity.Persona;
import upeu.proyecto5.soap.repository.PersonaRepository;
import upeu.proyecto5.soap.service.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	private PersonaRepository repo;
	
	@Transactional(readOnly = true)
	@Override
	public List<Persona> lista() {
		return repo.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Persona> buscarById(Integer id) {
		return repo.findById(id);
	}

	@Transactional
	@Override
	public Persona registrar(Persona object) {
		return repo.save(object);
	}

	@Transactional
	@Override
	public Persona actualizar(Persona object) {
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

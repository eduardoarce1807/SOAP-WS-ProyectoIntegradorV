package upeu.proyecto5.soap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import upeu.proyecto5.soap.entity.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {

}

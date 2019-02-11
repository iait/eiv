package iait.eiv.repository;

import org.springframework.data.repository.CrudRepository;

import iait.eiv.entity.Persona;
import iait.eiv.entity.PersonaPK;

public interface PersonaRepository extends CrudRepository<Persona, PersonaPK> {

}

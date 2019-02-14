package iait.eiv.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import iait.eiv.entity.PersonaPK;
import iait.eiv.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, PersonaPK> {

    Optional<Usuario> findByNombre(String nombre);

}

package iait.eiv.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import iait.eiv.entity.TipoDocumento;

public interface TipoDocumentoRepository extends CrudRepository<TipoDocumento, Integer> {

    Optional<TipoDocumento> findByAbreviatura(@Param("abreviatura") String abreviatura);

}

package py.com.nsa.api.commons.components.cfg.documento_agencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.documento_agencia.model.DocumentoAgencia;

@Repository
public interface DocumentoAgenciaRepository extends JpaRepository<DocumentoAgencia, Long> {

}

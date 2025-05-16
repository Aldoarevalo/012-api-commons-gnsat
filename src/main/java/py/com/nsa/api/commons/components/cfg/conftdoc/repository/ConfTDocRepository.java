package py.com.nsa.api.commons.components.cfg.conftdoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.cfg.conftdoc.model.ConfTDoc;

public interface ConfTDocRepository extends JpaRepository<ConfTDoc, Long> {
    boolean existsByDocNombreIgnoreCaseAndPaCod(String docNombre, Long paCod);
    boolean existsByDocNombreIgnoreCaseAndPaCodAndDocCodNot(String docNombre, Long paCod, Long docCod);

}

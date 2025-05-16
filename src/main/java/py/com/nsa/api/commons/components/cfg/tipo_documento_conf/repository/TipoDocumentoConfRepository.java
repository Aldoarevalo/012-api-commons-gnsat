package py.com.nsa.api.commons.components.cfg.tipo_documento_conf.repository;//package py.com.nsa.api.commons.components.cfg.tipo_documento_conf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.cfg.tipo_documento_conf.model.TipoDocumentoConf;

public interface TipoDocumentoConfRepository extends JpaRepository<TipoDocumentoConf, Long> {
    boolean existsByDocNombreIgnoreCaseAndPaCod(String docNombre, Long paCod);
}

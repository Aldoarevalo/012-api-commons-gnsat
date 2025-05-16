package py.com.nsa.api.commons.components.cfg.tipodocagencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.cfg.tipodocagencia.model.TipoDocAgencia;

public interface TipoDocAgenciaRepository extends JpaRepository<TipoDocAgencia, Long> {
    boolean existsByTcdNombreDocIgnoreCase(String tcdNombreDoc);

    // Verificar si ya existe un TipoDocAgencia con el mismo nombre pero con un tcdCod diferente
    boolean existsByTcdNombreDocIgnoreCaseAndTcdCodNot(String tcdNombreDoc, Long tcdCod);

}

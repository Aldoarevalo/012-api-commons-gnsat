package py.com.nsa.api.commons.components.cfg.permiso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.permiso.model.CfgPermiso;

@Repository
public interface CfgPermisoRepository extends JpaRepository<CfgPermiso, Long> {
    boolean existsByPerNombreIgnoreCaseAndPerTipoObj(String perNombre, Long perTipoObj);
}

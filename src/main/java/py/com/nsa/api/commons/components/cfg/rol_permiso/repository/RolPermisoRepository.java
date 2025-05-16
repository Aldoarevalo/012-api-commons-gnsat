package py.com.nsa.api.commons.components.cfg.rol_permiso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.rol_permiso.model.RolPermiso;

import java.util.List;

@Repository
public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {
    List<RolPermiso> findByRolRolCodigo(Long rolCodigo);
}

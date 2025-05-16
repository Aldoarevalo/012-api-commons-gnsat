package py.com.nsa.api.commons.components.ref.permiso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.ref.permiso.model.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {

    boolean existsByperNombreIgnoreCase(String perNombre);
}

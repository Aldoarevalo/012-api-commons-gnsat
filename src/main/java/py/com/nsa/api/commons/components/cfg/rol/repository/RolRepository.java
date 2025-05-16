package py.com.nsa.api.commons.components.cfg.rol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.rol.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

}

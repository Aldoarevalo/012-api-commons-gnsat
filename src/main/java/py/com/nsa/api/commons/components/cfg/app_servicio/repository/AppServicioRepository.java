package py.com.nsa.api.commons.components.cfg.app_servicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.app_servicio.model.AppServicio;
import py.com.nsa.api.commons.components.cfg.app_servicio.model.pk.AppServicioPK;

@Repository
public interface AppServicioRepository extends JpaRepository<AppServicio, AppServicioPK> {
}

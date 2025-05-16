package py.com.nsa.api.commons.components.cfg.perfil_servicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.cfg.perfil_servicio.model.PerfilServicio;
import py.com.nsa.api.commons.components.cfg.perfil_servicio.model.pk.PerfilServicioPK;

public interface PerfilServicioRepository extends JpaRepository<PerfilServicio, PerfilServicioPK> {
}

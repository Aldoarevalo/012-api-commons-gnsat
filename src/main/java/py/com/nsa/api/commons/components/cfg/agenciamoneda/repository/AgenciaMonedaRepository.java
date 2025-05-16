package py.com.nsa.api.commons.components.cfg.agenciamoneda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.cfg.agenciamoneda.model.AgenciaMoneda;
import py.com.nsa.api.commons.components.cfg.agenciamoneda.model.pk.AgenciaMonedaPK;

public interface AgenciaMonedaRepository extends JpaRepository<AgenciaMoneda, AgenciaMonedaPK> {
}
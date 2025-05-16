package py.com.nsa.api.commons.components.cfg.trx_cumplimiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.trx_cumplimiento.model.Cumplimiento;


@Repository
public interface CumplimientoRepository extends JpaRepository<Cumplimiento, Long> {
}

package py.com.nsa.api.commons.components.cfg.tipo_limite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.tipo_limite.model.TipoLimite;

@Repository
public interface TipoLimiteRepository extends JpaRepository<TipoLimite, Long> {
}

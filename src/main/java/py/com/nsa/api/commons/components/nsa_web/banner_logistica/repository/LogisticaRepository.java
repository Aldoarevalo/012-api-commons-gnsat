package py.com.nsa.api.commons.components.nsa_web.banner_logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.nsa_web.banner_logistica.model.Logistica;

@Repository
public interface LogisticaRepository extends JpaRepository<Logistica, Long> {
}
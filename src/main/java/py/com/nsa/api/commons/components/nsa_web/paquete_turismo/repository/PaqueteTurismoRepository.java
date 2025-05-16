package py.com.nsa.api.commons.components.nsa_web.paquete_turismo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.nsa_web.paquete_turismo.model.PaqueteTurismo;

@Repository
public interface PaqueteTurismoRepository extends JpaRepository<PaqueteTurismo, Long> {
}
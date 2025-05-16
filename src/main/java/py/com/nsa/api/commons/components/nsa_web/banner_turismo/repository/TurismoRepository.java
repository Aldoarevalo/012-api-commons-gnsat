package py.com.nsa.api.commons.components.nsa_web.banner_turismo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.nsa_web.banner_turismo.model.Turismo;

@Repository
public interface TurismoRepository extends JpaRepository<Turismo, Long> {
}
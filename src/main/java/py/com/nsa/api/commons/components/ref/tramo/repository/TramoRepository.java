package py.com.nsa.api.commons.components.ref.tramo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.ref.tramo.model.Tramo;

@Repository
public interface TramoRepository extends JpaRepository<Tramo, Long> {
}

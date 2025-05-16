package py.com.nsa.api.commons.components.ref.rutacab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.ref.rutacab.model.RutaCab;

@Repository
public interface RutaCabRepository extends JpaRepository<RutaCab, Long> {
}
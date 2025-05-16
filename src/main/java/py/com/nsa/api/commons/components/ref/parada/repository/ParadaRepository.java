package py.com.nsa.api.commons.components.ref.parada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.ref.parada.model.Parada;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {
}


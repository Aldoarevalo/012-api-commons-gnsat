package py.com.nsa.api.commons.components.nsa_web.banner_encomienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.nsa_web.banner_encomienda.model.Encomienda;

@Repository
public interface EncomiendaRepository extends JpaRepository<Encomienda, Long> {
}
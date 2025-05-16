package py.com.nsa.api.commons.components.cfg.pais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long> {
    boolean existsByPaDescripcionIgnoreCase(String paDescripcion);
}

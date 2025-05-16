package py.com.nsa.api.commons.components.cfg.detalleserie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.cfg.detalleserie.model.DetalleSerie;
import py.com.nsa.api.commons.components.cfg.detalleserie.model.clavecompuesta.DetalleSerieId;

import java.util.List;

public interface DetalleSerieRepository extends JpaRepository<DetalleSerie, DetalleSerieId> {
    // Opción 1: Usando Query explícita
    @Query("SELECT d FROM DetalleSerie d WHERE d.id.sSerie = :serie")
    List<DetalleSerie> findBysSerie(@Param("serie") String serie);

    // O Opción 2: Usando convención de nombres
    // List<DetalleSerie> findByIdSserie(String sSerie);
}
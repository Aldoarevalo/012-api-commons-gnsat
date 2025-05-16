package py.com.nsa.api.commons.components.cfg.barrio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import py.com.nsa.api.commons.components.cfg.barrio.model.Barrio;

import java.util.List;

public interface BarrioRepository extends JpaRepository<Barrio, Long> {

    boolean existsByBdescripcionIgnoreCaseAndCiuCod(String bdescripcion, Long ciuCod);

    @Query("SELECT u.bPostal FROM Barrio u WHERE u.bCod = :bCod")
    List<Object[]> findbPostal(@Param("bCod") Long bCod);
}

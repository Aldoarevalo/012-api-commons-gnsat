package py.com.nsa.api.commons.components.cfg.cotizacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.cfg.cotizacion.model.Cotizacion;

import java.util.Date;
import java.util.List;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {
    List<Cotizacion> findByCotMoneda(String cotMoneda);
    List<Cotizacion> findByCotTipoTransac(String cotTipoTransac);
    List<Cotizacion> findByCotEstado(String cotEstado);
    List<Cotizacion> findByCotMonedaAndCotTipoTransacAndCotEstado(String cotMoneda, String cotTipoTransac, String cotEstado);

    @Query("SELECT c FROM Cotizacion c WHERE c.cotFechaVigencia <= :fecha AND (c.cotFechaFinCoti IS NULL OR c.cotFechaFinCoti >= :fecha) AND c.cotEstado = 'A'")
    List<Cotizacion> findCotizacionesVigentes(@Param("fecha") Date fecha);

}

package py.com.nsa.api.commons.components.ref.listaprecio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.ref.listaprecio.model.ListaPrecio;

import java.util.Date;
import java.util.List;

public interface ListaPrecioRepository extends JpaRepository<ListaPrecio, Long> {

    @Query("SELECT lp FROM ListaPrecio lp WHERE " +
            "lp.proCod = :proCod " +
            "AND (:agCod IS NULL AND lp.agCod IS NULL OR lp.agCod = :agCod) " +
            "AND lp.parListaPrecio = :parListaPrecio " +
            "AND lp.parMoneda = :parMoneda " +
            "AND (lp.lpInicio <= :lpFin OR :lpFin IS NULL) " +
            "AND (lp.lpFin >= :lpInicio OR lp.lpFin IS NULL)")
    List<ListaPrecio> findOverlappingPrices(
            @Param("proCod") String proCod,
            @Param("agCod") Long agCod,
            @Param("parListaPrecio") String parListaPrecio,
            @Param("parMoneda") String parMoneda,
            @Param("lpInicio") Date lpInicio,
            @Param("lpFin") Date lpFin);
}
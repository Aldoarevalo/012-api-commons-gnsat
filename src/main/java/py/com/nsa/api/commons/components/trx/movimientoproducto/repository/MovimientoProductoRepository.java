package py.com.nsa.api.commons.components.trx.movimientoproducto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.trx.movimientoproducto.model.MovimientoProducto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimientoProductoRepository extends JpaRepository<MovimientoProducto, Long> {

    @Query("SELECT m FROM MovimientoProducto m WHERE m.mpCod = :mpCod")
    Optional<MovimientoProducto> findByMpCod(@Param("mpCod") Long mpCod);

    @Query("SELECT m FROM MovimientoProducto m WHERE m.proCod = :proCod")
    List<MovimientoProducto> findByProCod(@Param("proCod") String proCod);

    @Query("SELECT m FROM MovimientoProducto m WHERE m.alCod = :alCod")
    List<MovimientoProducto> findByAlCod(@Param("alCod") Long alCod);

    @Query("SELECT m FROM MovimientoProducto m WHERE m.parTipo = :parTipo")
    List<MovimientoProducto> findByParTipo(@Param("parTipo") String parTipo);

    @Query("SELECT m FROM MovimientoProducto m WHERE m.mpFecha BETWEEN :fechaInicio AND :fechaFin")
    List<MovimientoProducto> findByMpFechaBetween(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    @Query("SELECT m FROM MovimientoProducto m WHERE m.mpDoc = :mpDoc")
    List<MovimientoProducto> findByMpDoc(@Param("mpDoc") String mpDoc);

    @Query("SELECT m FROM MovimientoProducto m WHERE m.mpDoc = :mpDoc AND m.mpTdoc = :mpTdoc")
    List<MovimientoProducto> findByMpDocAndMpTdoc(@Param("mpDoc") String mpDoc, @Param("mpTdoc") String mpTdoc);
}
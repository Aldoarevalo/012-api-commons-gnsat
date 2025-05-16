package py.com.nsa.api.commons.components.trx.transferenciaproducto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.trx.transferenciaproducto.model.TransferenciaProducto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransferenciaProductoRepository extends JpaRepository<TransferenciaProducto, Long> {

    @Query("SELECT t FROM TransferenciaProducto t WHERE t.trfpCod = :trfpCod")
    Optional<TransferenciaProducto> findByTrfpCod(@Param("trfpCod") Long trfpCod);

    @Query("SELECT t FROM TransferenciaProducto t WHERE t.trfpOrigen = :alCod")
    List<TransferenciaProducto> findByTrfpOrigen(@Param("alCod") Long alCod);

    @Query("SELECT t FROM TransferenciaProducto t WHERE t.trfpDestino = :alCod")
    List<TransferenciaProducto> findByTrfpDestino(@Param("alCod") Long alCod);

    @Query("SELECT t FROM TransferenciaProducto t WHERE t.trfpTipo = :tipo")
    List<TransferenciaProducto> findByTrfpTipo(@Param("tipo") String tipo);

    @Query("SELECT t FROM TransferenciaProducto t WHERE t.trfpEstado = :estado")
    List<TransferenciaProducto> findByTrfpEstado(@Param("estado") String estado);

    @Query("SELECT t FROM TransferenciaProducto t WHERE t.trfpFecha BETWEEN :fechaInicio AND :fechaFin")
    List<TransferenciaProducto> findByTrfpFechaBetween(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    @Query("SELECT t FROM TransferenciaProducto t WHERE t.trfpRefEnv = :referenciaEnvio")
    List<TransferenciaProducto> findByTrfpRefEnv(@Param("referenciaEnvio") Long referenciaEnvio);
}
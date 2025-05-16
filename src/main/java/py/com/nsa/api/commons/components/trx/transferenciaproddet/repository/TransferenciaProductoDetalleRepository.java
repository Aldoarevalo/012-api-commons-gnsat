package py.com.nsa.api.commons.components.trx.transferenciaproddet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.trx.transferenciaproddet.model.TransferenciaProductoDetalle;
import py.com.nsa.api.commons.components.trx.transferenciaproddet.model.TransferenciaProductoDetalleId;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransferenciaProductoDetalleRepository extends JpaRepository<TransferenciaProductoDetalle, TransferenciaProductoDetalleId> {

    @Query("SELECT COUNT(t) FROM TransferenciaProductoDetalle t WHERE t.trfpCod = :trfpCod")
    long countByTrfpCod(@Param("trfpCod") Long trfpCod);

    @Query("SELECT t FROM TransferenciaProductoDetalle t WHERE t.trfpCod = :trfpCod AND t.trfpdLinea = :trfpdLinea")
    Optional<TransferenciaProductoDetalle> findByTrfpCodAndTrfpdLinea(@Param("trfpCod") Long trfpCod, @Param("trfpdLinea") Integer trfpdLinea);

    @Query("SELECT t FROM TransferenciaProductoDetalle t WHERE t.trfpCod = :trfpCod")
    List<TransferenciaProductoDetalle> findByTrfpCod(@Param("trfpCod") Long trfpCod);

    @Query("SELECT t FROM TransferenciaProductoDetalle t WHERE t.proCod = :proCod")
    List<TransferenciaProductoDetalle> findByProCod(@Param("proCod") String proCod);

    @Query("SELECT COALESCE(MAX(t.trfpdLinea), 0) + 1 FROM TransferenciaProductoDetalle t WHERE t.trfpCod = :trfpCod")
    Integer getNextLineNumber(@Param("trfpCod") Long trfpCod);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM TransferenciaProductoDetalle t WHERE t.trfpCod = :trfpCod AND t.trfpdLinea = :trfpdLinea")
    boolean existsByTrfpCodAndTrfpdLinea(@Param("trfpCod") Long trfpCod, @Param("trfpdLinea") Integer trfpdLinea);

    @Query("SELECT t FROM TransferenciaProductoDetalle t WHERE t.trfpCod = :trfpCod AND t.trfpdRefLinea = :trfpdRefLinea")
    List<TransferenciaProductoDetalle> findByTrfpCodAndTrfpdRefLinea(@Param("trfpCod") Long trfpCod, @Param("trfpdRefLinea") Integer trfpdRefLinea);
}
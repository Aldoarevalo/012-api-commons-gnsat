package py.com.nsa.api.commons.components.cfg.productoimpuesto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.productoimpuesto.model.ProductoImpuesto;
import py.com.nsa.api.commons.components.cfg.productoimpuesto.model.ProductoImpuestoId;

import java.util.List;

@Repository
public interface ProductoImpuestoRepository extends JpaRepository<ProductoImpuesto, ProductoImpuestoId> {

    @Query("SELECT pi FROM ProductoImpuesto pi WHERE pi.proCod = :proCod")
    List<ProductoImpuesto> findByProCod(@Param("proCod") String proCod);

    @Query("SELECT pi FROM ProductoImpuesto pi WHERE pi.paCod = :paCod")
    List<ProductoImpuesto> findByPaCod(@Param("paCod") Long paCod);

    @Query("SELECT pi FROM ProductoImpuesto pi WHERE pi.impCod = :impCod")
    List<ProductoImpuesto> findByImpCod(@Param("impCod") Long impCod);

    @Query("SELECT CASE WHEN COUNT(pi) > 0 THEN true ELSE false END FROM ProductoImpuesto pi WHERE pi.proCod = :proCod AND pi.paCod = :paCod")
    boolean existsByProCodAndPaCod(@Param("proCod") String proCod, @Param("paCod") Long paCod);
}
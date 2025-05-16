package py.com.nsa.api.commons.components.cfg.impuesto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.cfg.impuesto.model.Impuesto;

public interface ImpuestoRepository extends JpaRepository<Impuesto, Long> {

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Impuesto i WHERE i.impCod = :impCod")
    boolean existsByImpCod(@Param("impCod") Long impCod);

    @Query("SELECT i FROM Impuesto i WHERE i.impCod = :impCod")
    Impuesto findByImpCod(@Param("impCod") Long impCod);

    @Query("SELECT i FROM Impuesto i WHERE i.impDescripcion = :impDescripcion")
    Impuesto findByImpDescripcion(@Param("impDescripcion") String impDescripcion);
}
package py.com.nsa.api.commons.components.cfg.comision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.cfg.comision.model.Comision;

import java.util.List;

public interface ComisionRepository extends JpaRepository<Comision, Integer> {
    List<Comision> findByComTipoTransac(String comTipoTransac);
    List<Comision> findByComMoneda(String comMoneda);
    List<Comision> findByComEstado(String comEstado);
    List<Comision> findByComCodAg(Integer comCodAg);

    @Query("SELECT c FROM Comision c WHERE " +
            "(:tipoTransac IS NULL OR c.comTipoTransac = :tipoTransac) AND " +
            "(:moneda IS NULL OR c.comMoneda = :moneda) AND " +
            "(:estado IS NULL OR c.comEstado = :estado) AND " +
            "(:codAg IS NULL OR c.comCodAg = :codAg)")
    List<Comision> findByMultiplesFiltros(
            @Param("tipoTransac") String tipoTransac,
            @Param("moneda") String moneda,
            @Param("estado") String estado,
            @Param("codAg") Integer codAg
    );
}
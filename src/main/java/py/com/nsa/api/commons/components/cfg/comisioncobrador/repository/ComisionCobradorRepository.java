package py.com.nsa.api.commons.components.cfg.comisioncobrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.cfg.comisioncobrador.modelo.ComisionCobrador;

public interface ComisionCobradorRepository extends JpaRepository<ComisionCobrador, Long> {
    //boolean existsByComDescripcionIgnoreCase(String comDescripcion);

    @Query("SELECT COALESCE(MAX(c.comCodigo), 0) + 1 FROM ComisionCobrador c WHERE c.comCodigo = :comCodigo")
    Long getNextComCodigo(@Param("comCodigo") Long comCodigo);
}
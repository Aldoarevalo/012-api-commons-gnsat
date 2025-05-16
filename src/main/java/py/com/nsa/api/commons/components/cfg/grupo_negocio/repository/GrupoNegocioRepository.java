package py.com.nsa.api.commons.components.cfg.grupo_negocio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.grupo_negocio.model.GrupoNegocio;

import java.util.List;

@Repository
public interface GrupoNegocioRepository extends JpaRepository<GrupoNegocio, Long> {

    @Query("SELECT g FROM GrupoNegocio g WHERE LOWER(g.grnNombre) LIKE LOWER(CONCAT(:keyword, '%')) OR CAST(g.grnCodigo AS string) = :keyword")
    List<GrupoNegocio> findByGrnNombreStartingWith(@Param("keyword") String keyword);
    boolean existsByGrnNombreIgnoreCase(String grnNombre);

    @Query("SELECT COALESCE(MAX(g.grnCodigo), 0) + 1 FROM GrupoNegocio g")
    Long getNextgrnCodigo();
}

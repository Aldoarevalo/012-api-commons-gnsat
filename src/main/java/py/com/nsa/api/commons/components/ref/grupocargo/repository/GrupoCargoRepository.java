package py.com.nsa.api.commons.components.ref.grupocargo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.ref.grupocargo.model.GrupoCargo;

import java.util.List;

public interface GrupoCargoRepository extends JpaRepository<GrupoCargo, Integer> {

    // Búsqueda insensible a mayúsculas/minúsculas que empieza con la cadena dada
    @Query("SELECT g FROM GrupoCargo g WHERE LOWER(g.gcaDescripcion) LIKE LOWER(CONCAT(:keyword, '%')) OR CAST(g.gcaCodigo AS string) = :keyword")
    List<GrupoCargo> findByGcaDescripcionStartingWith(@Param("keyword") String keyword);

    boolean existsByGcaDescripcionIgnoreCase(String gcaDescripcion);
}

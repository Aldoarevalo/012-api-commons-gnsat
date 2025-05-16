package py.com.nsa.api.commons.components.ref.estadocivil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.ref.estadocivil.model.EstadoCivil;

import java.util.List;

public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, Long> {

    // Búsqueda insensible a mayúsculas/minúsculas que empieza con la cadena dada
    @Query("SELECT e FROM EstadoCivil e WHERE LOWER(e.eciDescripcion) LIKE LOWER(CONCAT(:keyword, '%')) OR CAST(e.eciCodigo AS string) = :keyword")
    List<EstadoCivil> findByEciDescripcionStartingWith(@Param("keyword") String keyword);

    boolean existsByEciDescripcionIgnoreCase(String eciDescripcion);

}

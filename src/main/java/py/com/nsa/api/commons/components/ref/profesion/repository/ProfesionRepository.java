package py.com.nsa.api.commons.components.ref.profesion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.ref.profesion.model.Profesion;

import java.util.List;

public interface ProfesionRepository extends JpaRepository<Profesion, Long> {

    // Búsqueda por descripción o abreviatura (insensible a mayúsculas/minúsculas)

    @Query("SELECT p FROM Profesion p WHERE LOWER(p.profDescripcion) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.profAbreviatura) LIKE LOWER(CONCAT('%', :keyword, '%')) OR CAST(p.profCodigo AS string) = :keyword")
    List<Profesion> findByProfDescripcionOrAbreviatura(@Param("keyword") String keyword);

    boolean existsByProfDescripcionIgnoreCaseAndProfAbreviaturaIgnoreCase(String profDescripcion,
            String profAbreviatura);

}

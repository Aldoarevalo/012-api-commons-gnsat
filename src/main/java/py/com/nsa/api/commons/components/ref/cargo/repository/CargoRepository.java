package py.com.nsa.api.commons.components.ref.cargo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.ref.cargo.model.Cargo;

import java.util.List;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

    // Búsqueda insensible a mayúsculas/minúsculas que empieza con la cadena dada
    @Query("SELECT c FROM Cargo c " +
           "JOIN c.grupoCargo g " +
           "WHERE LOWER(c.carDescripcion) LIKE LOWER(CONCAT(:keyword, '%')) " +
           "OR LOWER(g.gcaDescripcion) LIKE LOWER(CONCAT(:keyword, '%')) " +
           "OR CAST(c.carCodigo AS string) = :keyword")
    List<Cargo> findByCarDescripcionOrGrupoCargoDescripcionStartingWith(@Param("keyword") String keyword);

    boolean existsByCarDescripcionIgnoreCase(String carDescripcion);
}

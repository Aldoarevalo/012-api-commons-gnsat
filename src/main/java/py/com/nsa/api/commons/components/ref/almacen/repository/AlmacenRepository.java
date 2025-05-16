package py.com.nsa.api.commons.components.ref.almacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.ref.almacen.model.Almacen;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {

    @Query("SELECT a FROM Almacen a WHERE a.alCod = :alCod")
    Optional<Almacen> findByAlCod(@Param("alCod") Long alCod);

    @Query("SELECT a FROM Almacen a WHERE a.agCod = :agCod")
    List<Almacen> findByAgCod(@Param("agCod") Long agCod);

    @Query("SELECT a FROM Almacen a WHERE UPPER(a.alDescripcion) LIKE UPPER(:descripcion)")
    List<Almacen> findByAlDescripcionContainingIgnoreCase(@Param("descripcion") String descripcion);

    boolean existsByAlDescripcionIgnoreCase(String alDescripcion);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Almacen a WHERE UPPER(a.alDescripcion) = UPPER(:descripcion) AND a.alCod <> :alCod")
    boolean existsByAlDescripcionIgnoreCaseAndAlCodNot(@Param("descripcion") String descripcion, @Param("alCod") Long alCod);
}
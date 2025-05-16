package py.com.nsa.api.commons.components.ref.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.ref.producto.model.Producto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {

    boolean existsByProCodIgnoreCase(String proCod);

    boolean existsByProDescripcionIgnoreCase(String proDescripcion);

    @Query("SELECT p FROM Producto p WHERE UPPER(p.proCod) = UPPER(:proCod)")
    Optional<Producto> findByProCodIgnoreCase(@Param("proCod") String proCod);

    @Query("SELECT p FROM Producto p WHERE UPPER(p.proDescripcion) LIKE UPPER(:descripcion)")
    List<Producto> findByProDescripcionContainingIgnoreCase(@Param("descripcion") String descripcion);

    @Query("SELECT p FROM Producto p WHERE p.parTipo = :tipo")
    List<Producto> findByParTipo(@Param("tipo") String tipo);

    @Query("SELECT p FROM Producto p WHERE p.parServicio = :servicio")
    List<Producto> findByParServicio(@Param("servicio") String servicio);

    @Query("SELECT p FROM Producto p WHERE p.proEstado = :estado")
    List<Producto> findByProEstado(@Param("estado") String estado);
}
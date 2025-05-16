package py.com.nsa.api.commons.components.ref.parvalor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;

import java.util.List;

public interface ValorRepository extends JpaRepository<ParValor, String> {
    boolean existsByParValorIgnoreCase(String parValor);

    @Query("SELECT v.parValor, v.parDescripcion, v.parComentario, p.pmCod, p.pmNombre " +
            "FROM ParValor v " +
            "INNER JOIN v.parametro p " +
            "WHERE UPPER(p.pmNombre) = UPPER(:pmNombre)")
    List<Object[]> findByDescripcion(@Param("pmNombre") String pmNombre);

}
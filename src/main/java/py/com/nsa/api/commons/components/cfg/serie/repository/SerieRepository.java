package py.com.nsa.api.commons.components.cfg.serie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.cfg.serie.model.Serie;

import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, String> {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Serie s WHERE s.sCod = :sCod")
    boolean existsByscod(@Param("sCod") String sCod);

    @Query("SELECT s FROM Serie s LEFT JOIN FETCH s.pais WHERE s.sCod = :sCod")
    Serie findBySCod(@Param("sCod") String sCod);

    @Query("SELECT s FROM Serie s LEFT JOIN FETCH s.pais")
    List<Serie> findAllWithPais();
}
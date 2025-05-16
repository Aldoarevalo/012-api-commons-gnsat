package py.com.nsa.api.commons.components.ref.materialensamblado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.ref.materialensamblado.model.MaterialEnsamblado;
import py.com.nsa.api.commons.components.ref.materialensamblado.model.MaterialEnsambladoId;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialEnsambladoRepository extends JpaRepository<MaterialEnsamblado, MaterialEnsambladoId> {

    @Query("SELECT m FROM MaterialEnsamblado m WHERE m.proCod = :proCod")
    List<MaterialEnsamblado> findByProCod(@Param("proCod") String proCod);

    @Query("SELECT m FROM MaterialEnsamblado m WHERE m.meLinea = :meLinea AND m.proCod = :proCod")
    Optional<MaterialEnsamblado> findByMeLineaAndProCod(@Param("meLinea") Integer meLinea, @Param("proCod") String proCod);

    @Query("SELECT COALESCE(MAX(m.meLinea), 0) + 1 FROM MaterialEnsamblado m WHERE m.proCod = :proCod")
    Integer getNextLineNumber(@Param("proCod") String proCod);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MaterialEnsamblado m WHERE m.meLinea = :meLinea AND m.proCod = :proCod")
    boolean existsByMeLineaAndProCod(@Param("meLinea") Integer meLinea, @Param("proCod") String proCod);
}
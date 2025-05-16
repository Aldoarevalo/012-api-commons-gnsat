package py.com.nsa.api.commons.components.cfg.asignacionserie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.asignacionserie.model.AsignacionSerie;
import py.com.nsa.api.commons.components.cfg.asignacionserie.model.clavecompuesta.AsignacionSerieId;

@Repository
public interface AsignacionSerieRepository extends JpaRepository<AsignacionSerie, AsignacionSerieId> {

    @Query(value = "SELECT COUNT(*) FROM GNSAT.CFG_ASIGNACION_SERIE WHERE S_COD = :sCod", nativeQuery = true)
    long countBySCod(@Param("sCod") String sCod);

    @Query(value = "SELECT COUNT(*) FROM GNSAT.CFG_ASIGNACION_SERIE " +
            "WHERE AG_COD = :agCod " +
            "AND S_COD = :sCod " +
            "AND AS_USUARIO = :asUsuario " +
            "AND AS_T_DOC = :asTDoc",
            nativeQuery = true)
    int countByCompositePrimaryKey(@Param("agCod") Long agCod,
                                   @Param("sCod") String sCod,
                                   @Param("asUsuario") Long asUsuario,
                                   @Param("asTDoc") String asTDoc);
}
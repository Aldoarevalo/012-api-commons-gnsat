package py.com.nsa.api.commons.components.cfg.agencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Long> {

    @Query("SELECT a FROM Agencia a " +
            "JOIN UsuarioAgencia ua ON (a.agAgenciaPadre = ua.agencia.agCod OR a.agCod = ua.agencia.agCod) " +
            "WHERE ua.usuario.usuCod = :usuCod")
    List<Agencia> findAllWithFilters(@Param("usuCod") Long usuCod);

    @Query(value = "SELECT DISTINCT AG.PA_COD AS codPais, PA.PA_DESCRIPCION AS paDescripcion " +
            "FROM GNSAT.CFG_AGENCIA AG " +
            "JOIN GNSAT.CFG_PAIS PA ON PA.PA_COD = AG.PA_COD",
            nativeQuery = true)
    List<Map<String, Object>> findDistinctAgenciasPais();

    @Query(value = "SELECT DISTINCT AG.CIU_COD AS codCiudad, CI.CIU_DESCRIPCION AS ciuDescripcion " +
            "FROM GNSAT.CFG_AGENCIA AG " +
            "INNER JOIN GNSAT.CFG_PAIS PA ON PA.PA_COD = AG.PA_COD " +
            "INNER JOIN GNSAT.CFG_CIUDAD CI ON CI.CIU_COD = AG.CIU_COD " +
            "INNER JOIN GNSAT.CFG_DEPARTAMENTO DE ON DE.DP_COD = CI.DP_COD " +
            "AND DE.PA_COD = PA.PA_COD " +
            "WHERE PA.PA_COD = :paCod",
            nativeQuery = true)
    List<Map<String, Object>> findDistinctCiudades(@Param("paCod") Long paCod);

    @Query(value = "SELECT DISTINCT AG.PAR_TIPOAGENCIA AS codTipo, VA.PAR_DESCRIPCION AS parDescripcion " +
            "FROM GNSAT.CFG_AGENCIA AG " +
            "JOIN GNSAT.PAR_VALOR VA ON VA.PAR_VALOR = AG.PAR_TIPOAGENCIA",
            nativeQuery = true)
    List<Map<String, Object>> findDistinctTipoAgencias();


    /// filtros avanzados

    // Nuevos métodos para filtros avanzados

    List<Agencia> findByAgNombreFantasiaContainingIgnoreCase(String nombre);

    List<Agencia> findByParTipoAgencia(String tipoAgencia);

    List<Agencia> findByAgEstado(String estado);

    List<Agencia> findByParBloqueo(String bloqueo);

    List<Agencia> findByPaCod(Long paCod);

    List<Agencia> findByCiuCod(Long ciuCod);

    @Query(value = "SELECT a.* FROM GNSAT.CFG_AGENCIA a " +
            "INNER JOIN GNSAT.CFG_USUARIO_AGENCIA ua ON a.AG_COD = ua.USU_AGE_AGE_COD " +
            "WHERE ua.USU_AGE_USU_COD = :usuCod",
            nativeQuery = true)
    List<Agencia> findAgenciasByUsuario(@Param("usuCod") Long usuCod);





List<Agencia> findByAgAgenciaPadre(Long agenciaPadre);

    List<Agencia> findByParRubro(String rubro);

    List<Agencia> findByAgFechaCreacionBetween(Date fechaInicio, Date fechaFin);

    // Método para encontrar agencias por múltiples criterios usando consulta dinámica
    @Query("SELECT a FROM Agencia a " +
            "WHERE (:agNombreFantasia IS NULL OR LOWER(a.agNombreFantasia) LIKE LOWER(CONCAT('%', :agNombreFantasia, '%'))) " +
            "AND (:parTipoAgencia IS NULL OR a.parTipoAgencia = :parTipoAgencia) " +
            "AND (:agEstado IS NULL OR a.agEstado = :agEstado) " +
            "AND (:parBloqueo IS NULL OR a.parBloqueo = :parBloqueo) " +
            "AND (:paCod IS NULL OR a.paCod = :paCod) " +
            "AND (:ciuCod IS NULL OR a.ciuCod = :ciuCod) " +
            "AND (:bCod IS NULL OR a.bCod = :bCod) " +
            "AND (:agAgenciaPadre IS NULL OR a.agAgenciaPadre = :agAgenciaPadre) " +
            "AND (:parRubro IS NULL OR a.parRubro = :parRubro)")
    List<Agencia> findByMultipleCriteria(
            @Param("agNombreFantasia") String agNombreFantasia,
            @Param("parTipoAgencia") String parTipoAgencia,
            @Param("agEstado") String agEstado,
            @Param("parBloqueo") String parBloqueo,
            @Param("paCod") Long paCod,
            @Param("ciuCod") Long ciuCod,
            @Param("bCod") Long bCod,
            @Param("agAgenciaPadre") Long agAgenciaPadre,
            @Param("parRubro") String parRubro
    );

}

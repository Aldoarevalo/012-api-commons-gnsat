package py.com.nsa.api.commons.components.cfg.cajaagencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.cajaagencia.model.CajaAgencia;

import java.util.List;

@Repository
public interface CajaAgenciaRepository extends JpaRepository<CajaAgencia, Long> {

    @Query(value = "SELECT " +
            "PA.PA_DESCRIPCION AS PAIS, " +
            "CI.CIU_DESCRIPCION AS CIUDAD, " +
            "VA.PAR_DESCRIPCION AS TIPO_AGENCIA, " +
            "AG.AG_NOMBRE_FANTASIA AS AGENCIA, " +
            "US.USU_NOMBRE AS USUARIO, " +
            "PE.P_NOMBRE ||' '|| PE.P_APELLIDO AS TESORERO, " +
            "CA.CJA_DESCRIPCION AS CAJA, " +
            "MO.PAR_DESCRIPCION AS MONEDA, " +
            "CASE " +
            "    WHEN CA.CJA_OPERACION = 'C' THEN 'Cierre' " +
            "    WHEN CA.CJA_OPERACION = 'A' THEN 'Apertura' " +
            "END AS ESTADO, " +
            "CA.CJA_SALDO_AG AS SALDO " +
            "FROM GNSAT.CFG_USUARIO_AGENCIA UA " +
            "INNER JOIN GNSAT.CFG_USUARIO US ON US.USU_COD = UA.USU_AGE_USU_COD " +
            "INNER JOIN GNSAT.CFG_AGENCIA AG ON AG.AG_COD = UA.USU_AGE_AGE_COD " +
            "INNER JOIN GNSAT.CFG_PAIS PA ON PA.PA_COD = AG.PA_COD " +
            "INNER JOIN GNSAT.CFG_CIUDAD CI ON CI.CIU_COD = AG.CIU_COD " +
            "INNER JOIN GNSAT.CFG_DEPARTAMENTO DE ON DE.DP_COD = CI.DP_COD " +
            "    AND DE.PA_COD = PA.PA_COD " +
            "INNER JOIN GNSAT.PAR_VALOR VA ON VA.PAR_VALOR = AG.PAR_TIPOAGENCIA " +
            "INNER JOIN GNSAT.REF_PERSONA PE ON PE.P_COD = AG.P_COD_AGENCIA " +
            "LEFT JOIN GNSAT.CFG_CAJA_AGENCIA CA " +
            "    ON CA.CJA_AGENCIA = AG.AG_COD " +
            "    AND CA.CJA_PAIS = AG.PA_COD " +
            "    AND CA.CJA_CIUDAD = AG.CIU_COD " +
            "INNER JOIN GNSAT.CFG_CAJA_USUARIO CU ON CU.CJU_COD_USUARIO = UA.USU_AGE_USU_COD " +
            "      AND CU.CJU_COD_AGENCIA = CA.CJA_COD_CAJA " +
            "LEFT JOIN GNSAT.CFG_AGENCIA_MONEDA AM ON AM.AG_COD = AG.AG_COD " +
            "LEFT JOIN GNSAT.PAR_VALOR MO ON MO.PAR_VALOR = AM.PAR_MONEDA " +
            "WHERE (:paisCod IS NULL OR AG.PA_COD = :paisCod) " +
            "AND (:ciudadCod IS NULL OR AG.CIU_COD = :ciudadCod) " +
            "AND (:tipoAgencia IS NULL OR AG.PAR_TIPOAGENCIA = :tipoAgencia) " +
            "AND (:agenciaCod IS NULL OR AG.AG_COD = :agenciaCod) " +
            "AND (:usuarioCod IS NULL OR UA.USU_AGE_USU_COD = :usuarioCod) ",
            nativeQuery = true)
    List<Object[]> findCajasAgenciasByFiltros(
            @Param("paisCod") Long paisCod,
            @Param("ciudadCod") Long ciudadCod,
            @Param("tipoAgencia") String tipoAgencia,
            @Param("agenciaCod") Long agenciaCod,
            @Param("usuarioCod") Long usuarioCod);



    @Query(value = "SELECT\n" +
            "\tp.PA_DESCRIPCION AS PAIS,\n" +
            "\tCI.CIU_DESCRIPCION AS CIUDAD,\n" +
            "\tVA.PAR_DESCRIPCION AS TIPO_AGENCIA,\n" +
            "\ta.AG_NOMBRE_FANTASIA AS AGENCIA,\n" +
            "\tU.USU_NOMBRE AS USUARIO,\n" +
            "\tPE.P_NOMBRE || ' ' || PE.P_APELLIDO AS TESORERO,\n" +
            "\tc.CJA_DESCRIPCION AS CAJA,\n" +
            "\tMO.PAR_DESCRIPCION AS MONEDA,\n" +
            "\ta.AG_COD,\n" +
            "\ta.ag_cod,\n" +
            "\tCASE\n" +
            "\t\tWHEN c.CJA_OPERACION = 'C' THEN 'Cerrado'\n" +
            "\t\tWHEN c.CJA_OPERACION = 'A' THEN 'Abierto'\n" +
            "\tEND AS ESTADO,\n" +
            "\tc.CJA_SALDO_AG AS SALDO,\n" +
            "\tc.CJA_COD_caja AS id\n" +
            "FROM\n" +
            "\tGNSAT.CFG_CAJA_AGENCIA C\n" +
            "INNER JOIN\n" +
            "        GNSAT.CFG_AGENCIA A  \n" +
            "            ON\n" +
            "\tA.AG_COD = C.CJA_AGENCIA\n" +
            "INNER JOIN\n" +
            "        GNSAT.CFG_USUARIO_AGENCIA UA \n" +
            "            ON\n" +
            "\tUA.USU_AGE_AGE_COD = C.CJA_AGENCIA\n" +
            "INNER JOIN GNSAT.REF_PERSONA PE \n" +
            "            ON\n" +
            "\tPE.P_COD = A.P_COD_AGENCIA\n" +
            "LEFT JOIN\n" +
            "        GNSAT.PAR_VALOR MO  \n" +
            "            ON\n" +
            "\tMO.PAR_VALOR = c.CJA_MONEDA\n" +
            "LEFT JOIN\n" +
            "        GNSAT.CFG_USUARIO U \n" +
            "            ON\n" +
            "\tU.USU_COD = UA.USU_AGE_USU_COD\n" +
            "LEFT JOIN\n" +
            "        GNSAT.CFG_PAIS P   \n" +
            "            ON\n" +
            "\tP.PA_COD = A.PA_COD\n" +
            "LEFT JOIN\n" +
            "        GNSAT.CFG_CIUDAD CI  \n" +
            "            ON\n" +
            "\tCI.CIU_COD = A.CIU_COD\n" +
            "LEFT JOIN\n" +
            "        GNSAT.CFG_DEPARTAMENTO DE  \n" +
            "            ON\n" +
            "\tDE.DP_COD = CI.DP_COD\n" +
            "\tAND DE.PA_COD = P.PA_COD\n" +
            "LEFT JOIN\n" +
            "        GNSAT.PAR_VALOR VA \n" +
            "            ON\n" +
            "\tVA.PAR_VALOR = A.PAR_TIPOAGENCIA\n" +
            "WHERE\n" +
            "\tUA.USU_AGE_USU_COD = :usuarioCod\n" +
            "\tAND A.AG_COD = :agenciaCod",
            nativeQuery = true)
    List<Object[]> findCajasAgenciasByUser(
            @Param("agenciaCod") Long agenciaCod,
            @Param("usuarioCod") Long usuarioCod);



            @Modifying
            @Query("UPDATE CajaAgencia c SET c.cjaOperacion = :operacion WHERE c.cjaCodCaja IN :cajaIds")
            int updateOperacionByCajaIds(@Param("cajaIds") List<Long> cajaIds, @Param("operacion") String operacion);
}



package py.com.nsa.api.commons.components.ref.pdoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.ref.pdoc.model.PDoc;
import py.com.nsa.api.commons.components.ref.pdoc.model.pk.PDocPK;

import java.util.Date;
import java.util.List;

public interface PDocRepository extends JpaRepository<PDoc, PDocPK> {

    // Verificar si existe un documento con el mismo número para una persona
    // específica, ignorando mayúsculas y minúsculas

    List<PDoc> findByPDocNroDocIgnoreCase(String PDocNroDoc);

    List<PDoc> findBypcod(Long pcod);


    @Query(value = "SELECT pdoc.DOC_COD, " +
            "pdoc.P_DOC_NRO_DOC, " +
            "pdoc.P_COD, " +
            "pdoc.P_VENCIMIENTO " +
            "FROM GNSAT.REF_P_DOC pdoc " +
            "WHERE (:pcod IS NULL OR pdoc.P_COD = :pcod)",
            nativeQuery = true)
    List<Object[]> findPDocs(@Param("pcod") Long pcod);


    @Query(value = "SELECT " +
            "pdoc.DOC_COD AS pdoc_codigo, " +
            "pdoc.P_DOC_NRO_DOC AS pdoc_nro_doc, " +
            "pdoc.P_COD AS p_cod, " +
            "pdoc.P_VENCIMIENTO AS p_vencimiento, " +
            "conftdoc.DOC_COD AS conftdoc_codigo, " +
            "conftdoc.DOC_NOMBRE AS conftdoc_nombre, " +
            "conftdoc.DOC_DESCRIPCION AS conftdoc_descripcion, " +
            "conftdoc.PA_COD AS conftdoc_pa_cod, pais.PA_DESCRIPCION AS pais_padescripcion " +
            "FROM GNSAT.CFG_CONF_T_DOC conftdoc " +
            "LEFT JOIN GNSAT.REF_P_DOC pdoc " +
            "ON conftdoc.DOC_COD = pdoc.DOC_COD " +
            "INNER JOIN GNSAT.CFG_PAIS pais ON pais.PA_COD = conftdoc.PA_COD " + // Espacio agregado al final
            "AND (pdoc.P_COD = :pcod) " +
            "WHERE :pcod IS NOT NULL " +
            "UNION " +
            "SELECT " +
            "NULL AS pdoc_codigo, " +
            "NULL AS pdoc_nro_doc, " +
            "NULL AS p_cod, " +
            "NULL AS p_vencimiento, " +
            "conftdoc.DOC_COD AS conftdoc_codigo, " +
            "conftdoc.DOC_NOMBRE AS conftdoc_nombre, " +
            "conftdoc.DOC_DESCRIPCION AS conftdoc_descripcion, " +
            "conftdoc.PA_COD AS conftdoc_pa_cod, pais.PA_DESCRIPCION AS pais_padescripcion " +
            "FROM GNSAT.CFG_CONF_T_DOC conftdoc " +
            "INNER JOIN GNSAT.CFG_PAIS pais ON pais.PA_COD = conftdoc.PA_COD " + // Espacio agregado al final
            "WHERE :pcod IS NULL", nativeQuery = true)
    List<Object[]> findConfTDocsWithPDoc(@Param("pcod") Long pcod);

    @Modifying
    @Transactional
    @Query("UPDATE PDoc p SET " +
            "p.pVencimiento = :pVencimiento, " +
            "p.docCod = :docCod, " +  // Actualizar docCod
            "p.PDocNroDoc = :pDocNroDoc, " +  // Actualizar PDocNroDoc
            "p.pcod = :pcod " +  // Actualizar pcod
            "WHERE p.docCod = :docCodOld " +  // Identificar el registro por valores antiguos
            "AND p.PDocNroDoc = :pDocNroDocOld " +
            "AND p.pcod = :pcodOld")
    int actualizarPDoc(
            @Param("pVencimiento") Date pVencimiento,
            @Param("docCod") Long docCod,  // Nuevo valor para docCod
            @Param("pDocNroDoc") String pDocNroDoc,  // Nuevo valor para PDocNroDoc
            @Param("pcod") Long pcod,  // Nuevo valor para pcod
            @Param("docCodOld") Long docCodOld,  // Valor antiguo para identificar el registro
            @Param("pDocNroDocOld") String pDocNroDocOld,
            @Param("pcodOld") Long pcodOld
    );


}
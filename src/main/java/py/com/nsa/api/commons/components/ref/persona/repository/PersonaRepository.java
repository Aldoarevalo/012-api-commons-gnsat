package py.com.nsa.api.commons.components.ref.persona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.ref.pdoc.model.PDoc;
import py.com.nsa.api.commons.components.ref.persona.model.Persona;

import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Long> {



        // Consulta existente (si las tienes)
        List<Persona> findAll();

        // Nueva consulta para obtener los documentos asociados a una persona
        @Query(value = "SELECT " +
                "pdoc.DOC_COD AS doc_cod, " +
                "pdoc.P_DOC_NRO_DOC AS pdoc_nro_doc, " +
                "pdoc.P_COD AS p_cod, " +
                "pdoc.P_VENCIMIENTO AS p_vencimiento, " +
                "conftdoc.DOC_NOMBRE AS doc_nombre, " +
                "conftdoc.DOC_DESCRIPCION AS doc_descripcion " +
                "FROM GNSAT.REF_P_DOC pdoc " +
                "LEFT JOIN GNSAT.CFG_CONF_T_DOC conftdoc " +
                "ON pdoc.DOC_COD = conftdoc.DOC_COD " +
                "WHERE pdoc.P_COD = :pcod", nativeQuery = true)
        List<Object[]> findDocumentosByPcod(@Param("pcod") Long pcod);




        @Query("SELECT p.pcod, p.bCod, p.paCod,d.PDocNroDoc, p.pnombre, p.papellido, p.pdireccion, p.pemail, p.pesFisica, "
                        + "p.pfechaNacimiento, p.psexo, p.ptelefono, e.eCod, e.eFechContratacion, e.eEsPropio, e.cCodCargo, "
                        + "u.usuCod, b.ciuCod, c.cCod, t.docNombre, t.docDescripcion, d.docCod " // Incluimos el campo ciuCod desde Barrio
                        + "FROM Persona p "
                        + "LEFT JOIN Empleado e ON e.pcod = p.pcod "
                        + "LEFT JOIN Usuario u ON e.eCod = u.eCod "
                        + "LEFT JOIN Barrio b ON p.bCod = b.bCod " // Join adicional con Barrio para obtener ciuCod
                        + "LEFT JOIN PDoc d ON d.pcod = p.pcod "
                        + "LEFT JOIN Cliente c ON c.pCod = p.pcod "
                        + "LEFT JOIN TipoDocumentoConf t ON t.docCod = d.docCod "
                        + "WHERE UPPER(d.PDocNroDoc) = UPPER(:PDocNroDoc)")
        List<Object[]> findByCi(@Param("PDocNroDoc") String PDocNroDoc);


        List<Persona> findByPemailIgnoreCase(String pemail);

        @Query("SELECT  c.ciuCod, c.ciuDescripcion, dp.dpCod, dp.dpDescripcion, p.paCod, p.paDescripcion "
                        +
                        "FROM Ciudad c " +
                        "JOIN c.departamento dp " +
                        "JOIN dp.pais p " +
                        "WHERE p.paCod = :paCod")
        List<Object[]> findCiudadByPais(@Param("paCod") Long paCod);

        @Query("SELECT b.bCod, b.bdescripcion, c.ciuCod, c.ciuDescripcion, dp.dpCod, dp.dpDescripcion, p.paCod, p.paDescripcion "
                        +
                        "FROM Barrio b " +
                        "JOIN b.ciudad c " +
                        "JOIN c.departamento dp " +
                        "JOIN dp.pais p " +
                        "WHERE b.ciuCod = :ciuCod")
        List<Object[]> findBarriosByCiudad(@Param("ciuCod") Long ciuCod);

        @Query("SELECT b.bPostal " +
                        "FROM Barrio b " +
                        "WHERE b.bCod = :bCod")
        List<Object[]> findbPostal(@Param("bCod") Long bCod);


        @Query("SELECT pd FROM PDoc pd JOIN pd.persona p WHERE UPPER(p.pnombre) = UPPER(:pnombre) AND UPPER(p.papellido) = UPPER(:papellido) AND pd.PDocNroDoc = :PDocNroDoc")
        List<PDoc> findByNombreAndApellidoAndPDocNroDoc(@Param("pnombre") String pnombre, @Param("papellido") String papellido, @Param("PDocNroDoc") String PDocNroDoc);

        List<Persona> findAllByPcodNotIn(List<Long> peCodList);

        @Modifying
        @Transactional
        @Query("UPDATE Persona p SET " +
                "p.pdireccion = :pdireccion, " +
                "p.ptelefono = :ptelefono, " +
                "p.bCod = :bCod, " +
                "p.paCod = :paCod " +
                "WHERE p.pcod = :pcod")
        int updatePersonaFields(
                @Param("pcod") Long pcod,
                @Param("pdireccion") String pdireccion,
                @Param("ptelefono") String ptelefono,
                @Param("bCod") Long bCod,
                @Param("paCod") Long paCod
        );
}

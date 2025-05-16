package py.com.nsa.api.commons.components.ref.tipodocumento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.ref.tipodocumento.model.TipoDocumento;

import java.util.List;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {
    @Query("SELECT t FROM TipoDocumento t " +
            "WHERE (t.tdoDocumentoWu = :tdoDocumentoWu AND 'I' = :tdoDocumentoWu) " +
            "OR (t.tdoEstado = 'A' AND 'I' <> :tdoDocumentoWu) " +
            "ORDER BY t.tdoCodigo")
    List<TipoDocumento> getListaDocumentos(@Param("tdoDocumentoWu") String tdoDocumentoWu);
}

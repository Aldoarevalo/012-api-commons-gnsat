package py.com.nsa.api.commons.components.cfg.cliente_docs.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.cliente_docs.model.ClienteDocs;

import java.io.InputStream;
import java.sql.Date;

@Repository
public class JdbcClienteDocsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveClienteDocsWithBlob(ClienteDocs doc, InputStream inputStream, long fileSize) {
        String sql = "INSERT INTO GNSAT.CFG_CLIENTE_DOCS " +
                "(CD_COD, AG_COD, CD_CODCLIENTE, CD_TIPO_DOC, CD_FECHA_VTO, CD_DESCRIPCION, CD_ARCHIVOS) " +
                "VALUES (GNSAT.SEQ_CFG_CLIENTE_DOCS.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(connection -> {
                java.sql.PreparedStatement ps = connection.prepareStatement(sql);
                ps.setLong(1, doc.getAgCod());
                ps.setLong(2, doc.getCdCodCliente());
                ps.setString(3, doc.getCdTipoDoc());
                ps.setDate(4, doc.getCdFechaVto() != null ? new Date(doc.getCdFechaVto().getTime()) : null);
                ps.setString(5, doc.getCdDescripcion());
                // Insert the file as BLOB
                ps.setBlob(6, inputStream, fileSize);
                return ps;
            });
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar el documento del cliente: " + e.getMessage(), e);
        }
    }

    public void updateClienteDocsWithBlob(ClienteDocs doc, InputStream inputStream, long fileSize) {
        String sql = "UPDATE GNSAT.CFG_CLIENTE_DOCS " +
                "SET AG_COD = ?, CD_CODCLIENTE = ?, CD_TIPO_DOC = ?, CD_FECHA_VTO = ?, CD_DESCRIPCION = ?, CD_ARCHIVOS = ? " +
                "WHERE CD_COD = ?";

        try {
            jdbcTemplate.update(connection -> {
                java.sql.PreparedStatement ps = connection.prepareStatement(sql);
                ps.setLong(1, doc.getAgCod());
                ps.setLong(2, doc.getCdCodCliente());
                ps.setString(3, doc.getCdTipoDoc());
                ps.setDate(4, doc.getCdFechaVto() != null ? new Date(doc.getCdFechaVto().getTime()) : null);
                ps.setString(5, doc.getCdDescripcion());
                // Update the file as BLOB
                ps.setBlob(6, inputStream, fileSize);
                ps.setLong(7, doc.getCdCod());
                return ps;
            });
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el documento del cliente: " + e.getMessage(), e);
        }
    }
}
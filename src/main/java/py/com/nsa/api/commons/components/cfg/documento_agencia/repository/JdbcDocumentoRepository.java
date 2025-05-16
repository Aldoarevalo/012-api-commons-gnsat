package py.com.nsa.api.commons.components.cfg.documento_agencia.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.documento_agencia.model.DocumentoAgencia;

import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Repository
public class JdbcDocumentoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveDocumentoConBlob(DocumentoAgencia doc, InputStream inputStream, long fileSize) {
        String sql = "INSERT INTO GNSAT.CFG_DOC_AGENCIA (AG_COD, TCD_COD, DC_DESCRIPCION, DC_VENCIMIENTO, DC_ADJUNTO) VALUES (?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setLong(1, doc.getAgCod());
                ps.setLong(2, doc.getTcdCod());
                ps.setString(3, doc.getDcDescripcion());
                //ps.setDate(4, new java.sql.Date(doc.getDcVencimiento().getTime()));
                java.sql.Date vencimientoDate = null;
                if (doc.getDcVencimiento() != null && !doc.getDcVencimiento().equals(java.sql.Date.valueOf("1800-01-01"))) {
                    vencimientoDate = new java.sql.Date(doc.getDcVencimiento().getTime());
                }
                ps.setDate(4, vencimientoDate); // Usar la fecha o NULL si corresponde
                // Insertar el archivo como BLOB
                ps.setBlob(5, inputStream, fileSize);
                return ps;
            });
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar el documento: " + e.getMessage(), e);
        }
    }

    public void updateDocumentoConBlob(DocumentoAgencia doc, InputStream inputStream, long fileSize) {
        String sql = "UPDATE GNSAT.CFG_DOC_AGENCIA SET AG_COD = ?, TCD_COD = ?, DC_DESCRIPCION = ?, DC_VENCIMIENTO = ?, DC_ADJUNTO = ? WHERE DC_DOC = ?";

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setLong(1, doc.getAgCod());
                ps.setLong(2, doc.getTcdCod());
                ps.setString(3, doc.getDcDescripcion());
                //ps.setDate(4, new java.sql.Date(doc.getDcVencimiento().getTime()));
                // Verificar si la fecha es "1970-01-01" y asignar NULL si es el caso
                java.sql.Date vencimientoDate = null;
                if (doc.getDcVencimiento() != null && !doc.getDcVencimiento().equals(java.sql.Date.valueOf("1800-01-01"))) {
                    vencimientoDate = new java.sql.Date(doc.getDcVencimiento().getTime());
                }
                ps.setDate(4, vencimientoDate); // Usar la fecha o NULL si corresponde
                // Insertar el archivo como BLOB
                ps.setBlob(5, inputStream, fileSize);
                ps.setLong(6, doc.getDcDoc()); // Asegúrate de que 'DC_DOC' sea la columna de identificación para la
                                               // actualización
                return ps;
            });
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el documento: " + e.getMessage(), e);
        }
    }


}

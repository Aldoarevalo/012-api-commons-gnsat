package py.com.nsa.api.commons.components.ref.tipodocumento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.tipodocumento.model.TipoDocumento;
import py.com.nsa.api.commons.components.ref.tipodocumento.repository.TipoDocumentoRepository;

import java.util.List;

@Service
public class TipoDocumentoService {

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumento.MensajeRespuesta getTipoDocumentosAll(String tdoDocumentoWu) {
        try {
            List<TipoDocumento> documentos;
            if (tdoDocumentoWu == null || tdoDocumentoWu.isEmpty()) {
                documentos = tipoDocumentoRepository.findAll();
            } else {
                documentos = tipoDocumentoRepository.getListaDocumentos(tdoDocumentoWu);
            }
            if (documentos.isEmpty()) {
                return new TipoDocumento.MensajeRespuesta(204L, "No se encontraron Tipos de Documentos.", null);
            }
            return new TipoDocumento.MensajeRespuesta(200L, "Tipos de Documentos obtenidos exitosamente.", documentos);
        } catch (Exception e) {
            System.err.println("Error al obtener los documentos: " + e.getMessage());
            e.printStackTrace();
            return new TipoDocumento.MensajeRespuesta(500L, "Error al obtener los Tipos de Documentos: " + e.getMessage(), null);
        }
    }


    public TipoDocumento.MensajeRespuesta insertTipoDocumento(TipoDocumento tipoDocumento) {

        try {
            TipoDocumento insertedTipoDocumento = tipoDocumentoRepository.save(tipoDocumento);
            return new TipoDocumento.MensajeRespuesta(200L, "Tipo de documento insertado exitosamente.", List.of(insertedTipoDocumento));
        } catch (Exception e) {
            System.err.println("Error al insertar el tipo de documento: " + e.getMessage());
            e.printStackTrace();
            return new TipoDocumento.MensajeRespuesta(500L, "Error al insertar el tipo de documento: " + e.getMessage(), null);
        }
    }

    public TipoDocumento.MensajeRespuesta updateTipoDocumento(TipoDocumento tipoDocumento) {

        try {
            TipoDocumento updatedTipoDocumento = tipoDocumentoRepository.save(tipoDocumento);
            return new TipoDocumento.MensajeRespuesta(200L, "Tipo de documento actualizado exitosamente.", List.of(updatedTipoDocumento));
        } catch (Exception e) {
            System.err.println("Error al actualizar el tipo de documento: " + e.getMessage());
            e.printStackTrace();
            return new TipoDocumento.MensajeRespuesta(500L, "Error al actualizar el tipo de documento: " + e.getMessage(), null);
        }
    }

    public TipoDocumento.MensajeRespuesta deleteTipoDocumento(Long tdoCodigo) {
        try {
            if (tipoDocumentoRepository.existsById(tdoCodigo)) {
                tipoDocumentoRepository.deleteById(tdoCodigo);
                return new TipoDocumento.MensajeRespuesta(200L, "Tipo de documento con código " + tdoCodigo + " eliminado exitosamente", null);
            } else {
                return new TipoDocumento.MensajeRespuesta(204L, "Tipo de documento con código " + tdoCodigo + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el Tipo de Documento porque está referenciado por otros registros.";
            return new TipoDocumento.MensajeRespuesta(204L, "Error al eliminar el Tipo de Documento: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new TipoDocumento.MensajeRespuesta(500L, "Error al eliminar el tipo de documento con código " + tdoCodigo + ": " + e.getMessage(), null);
        }
    }

    public boolean deleteById(Long tdoCodigo) {
        try {
            tipoDocumentoRepository.deleteById(tdoCodigo);
            return true;
        } catch (EmptyResultDataAccessException e) {
            // El tipo de documento no existe
            return false;
        } catch (DataIntegrityViolationException e) {
            // El tipo de documento no se puede eliminar debido a restricciones de integridad
            return false;
        } catch (Exception e) {
            // Cualquier otra excepción
            return false;
        }
    }

}

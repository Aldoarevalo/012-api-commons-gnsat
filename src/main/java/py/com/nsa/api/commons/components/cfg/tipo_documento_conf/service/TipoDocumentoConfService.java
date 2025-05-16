package py.com.nsa.api.commons.components.cfg.tipo_documento_conf.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.tipo_documento_conf.model.TipoDocumentoConf;
import py.com.nsa.api.commons.components.cfg.tipo_documento_conf.repository.TipoDocumentoConfRepository;

import java.util.List;

@Service
public class TipoDocumentoConfService {

    @Autowired
    private TipoDocumentoConfRepository tipoDocumentoConfRepository;

    public TipoDocumentoConf.MensajeRespuesta getTiposDocumentoAll() {
        try {
            List<TipoDocumentoConf> tiposDocumento = tipoDocumentoConfRepository.findAll();
            if (tiposDocumento.isEmpty()) {
                return new TipoDocumentoConf.MensajeRespuesta(204L, "No se encontraron tipos de documento.", null);
            }
            return new TipoDocumentoConf.MensajeRespuesta(200L, "Tipos de documento obtenidos exitosamente.", tiposDocumento);
        } catch (Exception e) {
            return new TipoDocumentoConf.MensajeRespuesta(500L, "Error al obtener los tipos de documento: " + e.getMessage(), null);
        }
    }

    public TipoDocumentoConf.MensajeRespuesta insertarTipoDocumento(TipoDocumentoConf tipoDocumento) {
        try {
//            if (tipoDocumentoConfRepository.existsByDocNombreIgnoreCaseAndPaCod(tipoDocumento.getDocNombre(), tipoDocumento.getPaCod())) {
//                return new TipoDocumentoConf.MensajeRespuesta(204L, "Ya existe un tipo de documento con el mismo nombre y país.", null);
//            }
            TipoDocumentoConf nuevoTipoDocumento = tipoDocumentoConfRepository.save(tipoDocumento);
            return new TipoDocumentoConf.MensajeRespuesta(200L, "Tipo de documento creado exitosamente.", List.of(nuevoTipoDocumento));
        } catch (Exception e) {
            return new TipoDocumentoConf.MensajeRespuesta(500L, "Error al insertar el tipo de documento: " + e.getMessage(), null);
        }
    }

    public TipoDocumentoConf.MensajeRespuesta updateTipoDocumento(TipoDocumentoConf tipoDocumento) {
        try {
            if (!tipoDocumentoConfRepository.existsById(tipoDocumento.getDocCod())) {
                return new TipoDocumentoConf.MensajeRespuesta(204L, "Tipo de documento no encontrado.", null);
            }
//            if (tipoDocumentoConfRepository.existsByDocNombreIgnoreCaseAndPaCod(tipoDocumento.getDocNombre(), tipoDocumento.getPaCod())) {
//                return new TipoDocumentoConf.MensajeRespuesta(204L, "Ya existe un tipo de documento con el mismo nombre y país.", null);
//            }
            TipoDocumentoConf updatedTipoDocumento = tipoDocumentoConfRepository.save(tipoDocumento);
            return new TipoDocumentoConf.MensajeRespuesta(200L, "Tipo de documento actualizado exitosamente.", List.of(updatedTipoDocumento));
        } catch (Exception e) {
            return new TipoDocumentoConf.MensajeRespuesta(500L, "Error al actualizar el tipo de documento: " + e.getMessage(), null);
        }
    }

    @Transactional
    public TipoDocumentoConf.MensajeRespuesta deleteTipoDocumento(Long docCod) {
        System.out.println("El codigo ingresado es :" + docCod );
        try {
            if (tipoDocumentoConfRepository.existsById(docCod)) {
                System.out.println("Entro en el if existsById");
                tipoDocumentoConfRepository.deleteById(docCod);
                return new TipoDocumentoConf.MensajeRespuesta(200L, "Tipo de documento eliminado exitosamente", null);
            } else {
                return new TipoDocumentoConf.MensajeRespuesta(204L, "Tipo de documento con código " + docCod + " no encontrado", null);
            }
        } catch (DataIntegrityViolationException e) {
            System.out.println("Error de integridad referencial: " + e.getMessage());
            String mensaje = "No se puede eliminar el tipo de documento porque está referenciado por otros registros";
            return new TipoDocumentoConf.MensajeRespuesta(204L, mensaje, null);
        } catch (Exception e) {
            return new TipoDocumentoConf.MensajeRespuesta(500L, "Error al eliminar el tipo de documento con código " + docCod + ": " + e.getMessage(), null);
        }
    }
}

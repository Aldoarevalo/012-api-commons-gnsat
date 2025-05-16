package py.com.nsa.api.commons.components.cfg.conftdoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.conftdoc.model.ConfTDoc;
import py.com.nsa.api.commons.components.cfg.conftdoc.repository.ConfTDocRepository;

import java.util.List;

@Service
public class ConfTDocService {

    @Autowired
    private ConfTDocRepository confTDocRepository;

    public ConfTDoc.MensajeRespuesta getAllDocs() {
        try {
            List<ConfTDoc> docs = confTDocRepository.findAll();

            if (docs.isEmpty()) {
                return new ConfTDoc.MensajeRespuesta(204L, "No se encontraron tipos de documentos.", null);
            }
            return new ConfTDoc.MensajeRespuesta(200L, "Tipos de Documentos obtenidos exitosamente.", docs);
        } catch (Exception e) {
            return new ConfTDoc.MensajeRespuesta(500L, "Error al obtener los tipos de documentos: " + e.getMessage(), null);
        }
    }

    public ConfTDoc.MensajeRespuesta insertDoc(ConfTDoc doc) {
        try {

            // Verificar si existe un documento con el mismo nombre para el mismo país
            boolean exists = confTDocRepository.existsByDocNombreIgnoreCaseAndPaCod(doc.getDocNombre(), doc.getPaCod());
            if (exists) {
                return new ConfTDoc.MensajeRespuesta(204L,
                        "Ya existe un documento con el mismo nombre para este país.", null);
            }

            ConfTDoc nuevoDoc = confTDocRepository.save(doc);
            return new ConfTDoc.MensajeRespuesta(200L, "Tipo de Documento creado exitosamente.", List.of(nuevoDoc));
        } catch (Exception e) {
            return new ConfTDoc.MensajeRespuesta(500L, "Error al insertar el tipo de documento: " + e.getMessage(), null);
        }
    }

    public ConfTDoc.MensajeRespuesta updateDoc(ConfTDoc doc) {
        try {
            if (doc.getDocCod() == null || !confTDocRepository.existsById(doc.getDocCod())) {
                return new ConfTDoc.MensajeRespuesta(204L, "Tipo de Documento no encontrado.", null);
            }

            // Verificar si existe otro documento con el mismo nombre y país, excluyendo el actual
            boolean exists = confTDocRepository.existsByDocNombreIgnoreCaseAndPaCodAndDocCodNot(
                    doc.getDocNombre(), doc.getPaCod(), doc.getDocCod());
            if (exists) {
                return new ConfTDoc.MensajeRespuesta(204L,
                        "Ya existe un documento con el mismo nombre para este país.", null);
            }

            ConfTDoc updatedDoc = confTDocRepository.save(doc);
            return new ConfTDoc.MensajeRespuesta(200L, "Tipo de Documento actualizado exitosamente.", List.of(updatedDoc));
        } catch (Exception e) {
            return new ConfTDoc.MensajeRespuesta(500L, "Error al actualizar el tipo de documento: " + e.getMessage(), null);
        }
    }

    public ConfTDoc.MensajeRespuesta deleteDoc(Long docCod) {
        try {
            if (confTDocRepository.existsById(docCod)) {
                confTDocRepository.deleteById(docCod);
                return new ConfTDoc.MensajeRespuesta(200L, "Tipo de Documento eliminado exitosamente", null);
            } else {
                return new ConfTDoc.MensajeRespuesta(204L, "Tipo de Documento con código " + docCod + " no encontrado", null);
            }
        } catch (JpaSystemException | DataIntegrityViolationException e) {
            String mensaje = "No se puede eliminar el tipo de documento porque está referenciado por otros registros.";
            return new ConfTDoc.MensajeRespuesta(204L, "Error al eliminar el documento: " + mensaje, null);
        } catch (Exception e) {
            return new ConfTDoc.MensajeRespuesta(500L,
                    "Error al eliminar el tipo de documento con código " + docCod + ": " + e.getMessage(), null);
        }
    }
}

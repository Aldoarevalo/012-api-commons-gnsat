package py.com.nsa.api.commons.components.ref.pdoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.ref.pdoc.model.PDoc;
import py.com.nsa.api.commons.components.ref.pdoc.model.pk.PDocPK;
import py.com.nsa.api.commons.components.ref.pdoc.repository.PDocRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PDocService {

    @Autowired
    private PDocRepository PDocRepository;

    @Autowired
    private PDocRepository pDocRepository;

    public PDoc.MensajeRespuesta getAllDocs(Long pcod) {
        try {
            List<PDoc> docs;

            // If pCod is null, retrieve all documents
            if (pcod == null) {
                docs = PDocRepository.findAll();
            } else {
                // If pCod is provided, filter documents by person code
                docs = PDocRepository.findBypcod(pcod);
            }

            if (docs.isEmpty()) {
                return new PDoc.MensajeRespuesta(204L, "No se encontraron documentos.", null);
            }

            return new PDoc.MensajeRespuesta(200L, "Documentos obtenidos exitosamente.", docs);
        } catch (Exception e) {
            return new PDoc.MensajeRespuesta(500L, "Error al obtener los documentos: " + e.getMessage(), null);
        }
    }

    public Map<String, Object> getAllDocs2(Long pcod) {
        try {
            List<Object[]> results = pDocRepository.findPDocs(pcod);
            Map<String, Object> response = new HashMap<>();
            List<Map<String, Object>> pdocs = new ArrayList<>();

            for (Object[] result : results) {
                Map<String, Object> detalle = new HashMap<>();
                detalle.put("docCod", result[0]);
                detalle.put("pdocNroDoc", result[1]);
                detalle.put("pcod", result[2]);
                detalle.put("pvencimiento", result[3]);
                pdocs.add(detalle);
            }

            if (pdocs.isEmpty()) {
                response.put("status", 204);
                response.put("mensaje", "No se encontraron documentos.");
                response.put("detalles", null);
            } else {
                response.put("status", 200);
                response.put("mensaje", "Documentos obtenidos exitosamente.");
                response.put("detalles", pdocs);
            }

            return response;
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("mensaje", "Error al obtener los documentos: " + e.getMessage());
            errorResponse.put("detalles", null);
            return errorResponse;
        }
    }

    //@Transactional
    public PDoc.MensajeRespuesta insertDoc(PDoc doc) {
        try {
            // Verificar si ya existe un documento con los mismos datos pero de otra persona
            List<PDoc> docsExistentes = pDocRepository.findByPDocNroDocIgnoreCase(doc.getPDocNroDoc());
            for (PDoc docExistente : docsExistentes) {
                if (!docExistente.getPcod().equals(doc.getPcod())) {
                    return new PDoc.MensajeRespuesta(
                            204L,
                            "Ya existe un documento con el número " + doc.getPDocNroDoc()
                                    + " pero de otra persona.",
                            null);
                }
            }

            // Guardar el documento
            PDoc docInsertado = pDocRepository.save(doc);

            return new PDoc.MensajeRespuesta(200L, "Documento insertado exitosamente.", List.of(docInsertado));
        } catch (Exception e) {
            return new PDoc.MensajeRespuesta(
                    500L,
                    "Error al insertar el documento: " + e.getMessage(),
                    null);
        }
    }

    //@Transactional
    public PDoc.MensajeRespuesta updateDocs(PDoc doc) {
        try {
            // Validación de valores nulos
            if (doc.getDocCod() == null || doc.getPDocNroDoc() == null || doc.getPcod() == null) {
                return new PDoc.MensajeRespuesta(
                        204L, "Código de documento, número de documento o código de persona no pueden ser nulos.", null
                );
            }

            // Verificar si ya existe un documento con los mismos datos pero de otra persona
            List<PDoc> docsExistentes = pDocRepository.findByPDocNroDocIgnoreCase(doc.getPDocNroDoc());
            for (PDoc docExistente : docsExistentes) {
                if (!docExistente.getPcod().equals(doc.getPcod()) &&
                        !docExistente.getDocCod().equals(doc.getDocCod())) {
                    return new PDoc.MensajeRespuesta(
                            204L,
                            "Ya existe un documento con el número " + doc.getPDocNroDoc()
                                    + " pero de otra persona.",
                            null);
                }
            }

            // Crear la clave primaria compuesta con los valores antiguos
            PDocPK pdocPK = new PDocPK(doc.getDocCodold(), doc.getPdocNroDocold(), doc.getPerCodold());

            // Verificar si el documento actual existe en la base de datos
            if (!pDocRepository.existsById(pdocPK)) {
                return new PDoc.MensajeRespuesta(
                        204L,
                        "Documento con código " + doc.getDocCodold() + ", " +
                                doc.getPdocNroDocold() + ", " + doc.getPerCodold() + " no encontrado.",
                        null);
            }

            // Actualizar el documento usando el método JPQL
            int registrosActualizados = pDocRepository.actualizarPDoc(
                    doc.getPVencimiento(),
                    doc.getDocCod(),
                    doc.getPDocNroDoc(),
                    doc.getPcod(),
                    doc.getDocCodold(),
                    doc.getPdocNroDocold(),
                    doc.getPerCodold()
            );

            // Comprobar si se actualizaron registros
            if (registrosActualizados > 0) {
                PDocPK newPdocPK = new PDocPK(doc.getDocCod(), doc.getPDocNroDoc(), doc.getPcod());
                PDoc updatedDoc = pDocRepository.findById(newPdocPK).orElse(null);

                if (updatedDoc != null) {
                    return new PDoc.MensajeRespuesta(200L,
                            "Documento actualizado exitosamente.",
                            List.of(updatedDoc));
                } else {
                    return new PDoc.MensajeRespuesta(500L,
                            "Documento actualizado, pero no se pudieron recuperar los datos.",
                            null);
                }
            } else {
                return new PDoc.MensajeRespuesta(500L,
                        "No se pudo actualizar el documento.",
                        null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new PDoc.MensajeRespuesta(500L,
                    "Error al actualizar el documento: " + e.getMessage(), null);
        }
    }



    public PDoc.MensajeRespuesta deleteDoc(Long docCod, String pDocNroDoc, Long pcod) {
        try {

            // Crear la clave primaria compuesta
            PDocPK pdocPK = new PDocPK(docCod, pDocNroDoc, pcod);
            if (PDocRepository.existsById(pdocPK)) {
                PDocRepository.deleteById(pdocPK);
                return new PDoc.MensajeRespuesta(200L, "Documento con codigo:" + docCod + " eliminado exitosamente",
                        null);
            } else {
                return new PDoc.MensajeRespuesta(204L, "Documento no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el registro porque está referenciado por otros registros.";
            return new PDoc.MensajeRespuesta(400L,
                    "Error al eliminar el documento: " + mensaje, null);
        } catch (Exception e) {
            return new PDoc.MensajeRespuesta(500L, "Error al eliminar el documento: " + e.getMessage(), null);
        }
    }
}

package py.com.nsa.api.commons.components.cfg.detalleserie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.detalleserie.model.DetalleSerie;
import py.com.nsa.api.commons.components.cfg.detalleserie.model.clavecompuesta.DetalleSerieId;
import py.com.nsa.api.commons.components.cfg.detalleserie.repository.DetalleSerieRepository;
import py.com.nsa.api.commons.components.cfg.serie.repository.SerieRepository;

import java.util.List;
import java.util.Optional;


@Service
public class DetalleSerieService {

    @Autowired
    private DetalleSerieRepository detalleSerieRepository;

    @Autowired
    private SerieRepository serieRepository;

    public DetalleSerie.MensajeRespuesta getDetalleSeriesAll() {
        try {
            List<DetalleSerie> detalles = detalleSerieRepository.findAll();
            if (detalles.isEmpty()) {
                return new DetalleSerie.MensajeRespuesta(204L, "No se encontraron detalles de serie.", null);
            }
            return new DetalleSerie.MensajeRespuesta(200L, "Detalles obtenidos exitosamente.", detalles);
        } catch (Exception e) {
            return new DetalleSerie.MensajeRespuesta(500L, "Error al obtener los detalles: " + e.getMessage(), null);
        }
    }

    public DetalleSerie.MensajeRespuesta getDetalleSerieBysCod(String sCod) {
        try {
            List<DetalleSerie> detalles = detalleSerieRepository.findBysSerie(sCod);  // Actualizado aquí

            if (!detalles.isEmpty()) {
                return new DetalleSerie.MensajeRespuesta(200L, "Detalles de Serie encontrados.", detalles);
            } else {
                return new DetalleSerie.MensajeRespuesta(204L, "No se encontraron detalles para la serie.", null);
            }
        } catch (Exception e) {
            return new DetalleSerie.MensajeRespuesta(500L, "Error al buscar detalles de serie: " + e.getMessage(), null);
        }
    }

    public DetalleSerie.MensajeRespuesta insertarDetalleSerie(DetalleSerie detalle) {
        try {
            if (detalle == null || detalle.getId() == null) {
                return new DetalleSerie.MensajeRespuesta(400L, "El objeto 'detalle' y 'id' son obligatorios.", null);
            }

            String serieCode = detalle.getId().getSSerie();

            // Verificar si existe algún detalle con la misma serie (solo para logs, opcional)
            List<DetalleSerie> detallesExistentes = detalleSerieRepository.findBysSerie(serieCode);

            // Imprimir detalles para verificación (esto es opcional, puedes mantenerlo o eliminarlo)
            if (!detallesExistentes.isEmpty()) {
                System.out.println("Detalles encontrados para serie " + serieCode + ": " + detallesExistentes.size());
                for (DetalleSerie d : detallesExistentes) {
                    System.out.println("- Detalle línea: " + d.getId().getDetLinea() + ", detCerrado: " + d.getDetCerrado());
                }
            } else {
                System.out.println("No se encontraron detalles para la serie " + serieCode);
            }

            // Verificar si ya existe un registro con el mismo ID
            if (detalleSerieRepository.existsById(detalle.getId())) {
                return new DetalleSerie.MensajeRespuesta(409L, "Ya existe un registro con los valores proporcionados.", null);
            }

            DetalleSerie nuevoDetalle = detalleSerieRepository.save(detalle);
            return new DetalleSerie.MensajeRespuesta(200L, "Detalle de serie creado exitosamente.", List.of(nuevoDetalle));

        } catch (Exception e) {
            return new DetalleSerie.MensajeRespuesta(500L, "Error al insertar el detalle: " + e.getMessage(), null);
        }
    }

    public DetalleSerie.MensajeRespuesta updateDetalleSerie(DetalleSerie detalleSerie) {
        try {
            DetalleSerieId id = new DetalleSerieId(detalleSerie.getId().getSSerie(), detalleSerie.getId().getDetLinea());

            if (!detalleSerieRepository.existsById(id)) {
                return new DetalleSerie.MensajeRespuesta(204L, "DetalleSerie no encontrado.", null);
            }

            String serieCode = detalleSerie.getId().getSSerie();

            // Si se intenta actualizar a detCerrado = "N", verificar si ya existe otro con ese estado
            if ("N".equalsIgnoreCase(detalleSerie.getDetCerrado())) { // Ignorar mayúsculas/minúsculas
                List<DetalleSerie> detallesExistentes = detalleSerieRepository.findBysSerie(serieCode);

                // Normalizar el valor de detCerrado para la comparación
                boolean existeOtroSinCerrar = detallesExistentes.stream()
                        .filter(d -> !d.getId().equals(id)) // Excluir el registro actual
                        .anyMatch(d -> d.getDetCerrado() != null && "N".equalsIgnoreCase(d.getDetCerrado().trim()));

                if (existeOtroSinCerrar) {
                    return new DetalleSerie.MensajeRespuesta(409L,
                            "No se puede actualizar a 'no cerrado' porque ya existe otro detalle no cerrado para esta serie.", null);
                }
            }

            DetalleSerie updatedDetalleSerie = detalleSerieRepository.save(detalleSerie);
            return new DetalleSerie.MensajeRespuesta(200L, "DetalleSerie actualizado exitosamente.", List.of(updatedDetalleSerie));

        } catch (Exception e) {
            return new DetalleSerie.MensajeRespuesta(500L, "Error al actualizar DetalleSerie: " + e.getMessage(), null);
        }
    }

}

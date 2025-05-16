package py.com.nsa.api.commons.components.cfg.serie.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.asignacionserie.repository.AsignacionSerieRepository;
import py.com.nsa.api.commons.components.cfg.detalleserie.model.DetalleSerie;
import py.com.nsa.api.commons.components.cfg.detalleserie.repository.DetalleSerieRepository;
import py.com.nsa.api.commons.components.cfg.pais.repository.PaisRepository;
import py.com.nsa.api.commons.components.cfg.serie.model.Serie;
import py.com.nsa.api.commons.components.cfg.serie.repository.SerieRepository;
import java.util.List;

@Service
public class SerieService {
    private static final Logger logger = LoggerFactory.getLogger(SerieService.class);

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private DetalleSerieRepository detalleSerieRepository;

    @Autowired
    private AsignacionSerieRepository asignacionSerieRepository;

    public Serie.MensajeRespuesta getSeriesAll() {
        try {
            List<Serie> series = serieRepository.findAllWithPais();
            if(series.isEmpty()) {
                return new Serie.MensajeRespuesta(204L, "No se encontraron series.", null);
            }
            return new Serie.MensajeRespuesta(200L, "Series encontradas.", series);
        } catch (Exception e) {
            return new Serie.MensajeRespuesta(500L, "Error al obtener series" + e.getMessage(), null);
        }
    }

    public Serie.MensajeRespuesta getSerieBySCod(String sCod) {
        try {
            Serie serie = serieRepository.findBySCod(sCod);

            if (serie == null) {
                return new Serie.MensajeRespuesta(204L, "No se encontró la serie con el código: " + sCod, null);
            }

            return new Serie.MensajeRespuesta(200L, "Serie encontrada.", List.of(serie));
        } catch (Exception e) {
            return new Serie.MensajeRespuesta(500L, "Error al obtener la serie: " + e.getMessage(), null);
        }
    }

    public Serie.MensajeRespuesta insertarSeries(Serie serie) {
        try {
            logger.debug("Intentando insertar serie. Valores recibidos - sCod: '{}', sPref: '{}', pais: '{}'",
                    serie.getSCod(), serie.getSPref(), serie.getPais());

            if (serie.getSCod() == null || serie.getSCod().trim().isEmpty()) {
                logger.error("El sCod es nulo o vacío");
                return new Serie.MensajeRespuesta(400L, "El código de serie no puede ser nulo o vacío", null);
            }

            if (serieRepository.existsByscod(serie.getSCod())) {
                logger.debug("Serie ya existe con sCod: {}", serie.getSCod());
                return new Serie.MensajeRespuesta(204L, "Ya existe una serie con el mismo código.", null);
            }

            // Validar que el país exista
            if (serie.getPais() == null || serie.getPais().getPaCod() == null) {
                logger.error("El país es nulo o inválido");
                return new Serie.MensajeRespuesta(400L, "Debe especificar un país válido", null);
            }

            if (!paisRepository.existsById(serie.getPais().getPaCod())) {
                logger.error("El país con código {} no existe", serie.getPais().getPaCod());
                return new Serie.MensajeRespuesta(404L, "El país especificado no existe", null);
            }

            logger.debug("Serie no existe, procediendo a guardar: {}", serie);
            Serie nuevaSerie = serieRepository.save(serie);
            logger.debug("Serie guardada exitosamente: {}", nuevaSerie);

            return new Serie.MensajeRespuesta(200L, "Serie creada exitosamente.", List.of(nuevaSerie));
        } catch (Exception e) {
            logger.error("Error al insertar la serie: ", e);
            return new Serie.MensajeRespuesta(500L, "Error al insertar la serie: " + e.getMessage(), null);
        }
    }

    public Serie.MensajeRespuesta updateSerie(Serie serie) {
        try {
            // Validar si el código de serie existe en la base de datos
            if (serie.getSCod() == null || serie.getSCod().trim().isEmpty()) {
                return new Serie.MensajeRespuesta(400L, "El código de serie no puede ser nulo o vacío.", null);
            }

            if (!serieRepository.existsByscod(serie.getSCod())) {
                return new Serie.MensajeRespuesta(404L, "Serie no encontrada con el código especificado.", null);
            }

            // Validar que el país exista
            if (serie.getPais() == null || serie.getPais().getPaCod() == null) {
                logger.error("El país es nulo o inválido");
                return new Serie.MensajeRespuesta(400L, "Debe especificar un país válido", null);
            }

            if (!paisRepository.existsById(serie.getPais().getPaCod())) {
                logger.error("El país con código {} no existe", serie.getPais().getPaCod());
                return new Serie.MensajeRespuesta(404L, "El país especificado no existe", null);
            }

            // Guardar los cambios
            Serie serieActualizada = serieRepository.save(serie);
            return new Serie.MensajeRespuesta(200L, "Serie actualizada exitosamente.", List.of(serieActualizada));

        } catch (Exception e) {
            logger.error("Error al actualizar la serie: ", e);
            return new Serie.MensajeRespuesta(500L, "Error al actualizar la serie: " + e.getMessage(), null);
        }
    }

    public Serie.MensajeRespuesta deleteSerie(String sCod) {
        try {
            // Verificar si la serie existe
            if (!serieRepository.existsByscod(sCod)) {
                return new Serie.MensajeRespuesta(404L, "No se encontró la serie con el código: " + sCod, null);
            }

            // Verificar si hay detalles asociados
            List<DetalleSerie> detallesAsociados = detalleSerieRepository.findBysSerie(sCod);
            long cantidadDetalles = detallesAsociados.size();

            // Verificar si hay asignaciones a agencias usando countBySCod
            long cantidadAsignaciones = asignacionSerieRepository.countBySCod(sCod);

            // Construir mensaje según las condiciones
            StringBuilder mensaje = new StringBuilder();
            boolean hayRestricciones = false;

            if (cantidadDetalles > 0) {
                mensaje.append("No se puede eliminar la serie con código ").append(sCod)
                        .append(" porque tiene ").append(cantidadDetalles)
                        .append(" detalle(s) asociado(s). Elimine primero los detalles");
                hayRestricciones = true;
            }

            if (cantidadAsignaciones > 0) {
                if (hayRestricciones) {
                    mensaje.append(" y ");
                }
                mensaje.append("está asignada a ").append(cantidadAsignaciones)
                        .append(" agencia(s). Elimine primero las asignaciones");
                hayRestricciones = true;
            }

            if (hayRestricciones) {
                mensaje.append(" antes de proceder.");
                return new Serie.MensajeRespuesta(409L, mensaje.toString(), null);
            }

            // Si no hay restricciones, proceder con la eliminación
            serieRepository.deleteById(sCod);
            return new Serie.MensajeRespuesta(200L, "Serie eliminada exitosamente.", null);

        } catch (Exception e) {
            logger.error("Error al eliminar la serie: ", e); // Añadir log detallado
            return new Serie.MensajeRespuesta(500L, "Error inesperado al eliminar la serie: " + e.getMessage(), null);
        }
    }
}
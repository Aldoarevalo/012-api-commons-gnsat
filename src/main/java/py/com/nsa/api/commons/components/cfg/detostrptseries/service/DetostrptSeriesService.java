package py.com.nsa.api.commons.components.cfg.detostrptseries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.cfg.detostrptseries.model.DetostrptSeries;
import py.com.nsa.api.commons.components.cfg.detostrptseries.model.DetostrptSeriesId;
import py.com.nsa.api.commons.components.cfg.detostrptseries.repository.DetostrptSeriesRepository;

import java.util.List;

@Service
public class DetostrptSeriesService {

    @Autowired
    private DetostrptSeriesRepository detostrptSeriesRepository;

    public DetostrptSeries.MensajeRespuesta getAllDetostrptSeries() {
        try {
            List<DetostrptSeries> series = detostrptSeriesRepository.findAll();
            if (series.isEmpty()) {
                return new DetostrptSeries.MensajeRespuesta(204L, "No se encontraron series.", null);
            }
            return new DetostrptSeries.MensajeRespuesta(200L, "Series obtenidas exitosamente.", series);
        } catch (Exception e) {
            return new DetostrptSeries.MensajeRespuesta(500L, "Error al obtener las series: " + e.getMessage(), null);
        }
    }

    public DetostrptSeries.MensajeRespuesta getSeriesByOrderAndDetail(String wmsStorerkey, String wmsExternorderkey, Long wmsIddet) {
        try {
            List<DetostrptSeries> series = detostrptSeriesRepository.findByOrderAndDetail(wmsStorerkey, wmsExternorderkey, wmsIddet);
            if (series.isEmpty()) {
                return new DetostrptSeries.MensajeRespuesta(204L, "No se encontraron series para el pedido y detalle especificados.", null);
            }
            return new DetostrptSeries.MensajeRespuesta(200L, "Series obtenidas exitosamente.", series);
        } catch (Exception e) {
            return new DetostrptSeries.MensajeRespuesta(500L, "Error al obtener las series: " + e.getMessage(), null);
        }
    }

    @Transactional
    public DetostrptSeries.MensajeRespuesta insertDetostrptSeries(DetostrptSeries detostrptSeries) {
        try {
            // Obtener el siguiente ID de serie
            Long nextSerieId = detostrptSeriesRepository.getNextSerieId(
                    detostrptSeries.getWmsStorerkey(),
                    detostrptSeries.getWmsExternorderkey(),
                    detostrptSeries.getWmsIddet()
            );
            detostrptSeries.setWmsIdserie(nextSerieId);

            DetostrptSeries savedSeries = detostrptSeriesRepository.save(detostrptSeries);
            return new DetostrptSeries.MensajeRespuesta(200L, "Serie creada exitosamente.", List.of(savedSeries));
        } catch (Exception e) {
            return new DetostrptSeries.MensajeRespuesta(500L, "Error al insertar la serie: " + e.getMessage(), null);
        }
    }

    @Transactional
    public DetostrptSeries.MensajeRespuesta updateDetostrptSeries(DetostrptSeries detostrptSeries) {
        try {
            DetostrptSeriesId id = new DetostrptSeriesId(
                    detostrptSeries.getWmsStorerkey(),
                    detostrptSeries.getWmsExternorderkey(),
                    detostrptSeries.getWmsIddet(),
                    detostrptSeries.getWmsIdserie()
            );

            if (!detostrptSeriesRepository.existsById(id)) {
                return new DetostrptSeries.MensajeRespuesta(204L, "Serie no encontrada.", null);
            }

            DetostrptSeries updatedSeries = detostrptSeriesRepository.save(detostrptSeries);
            return new DetostrptSeries.MensajeRespuesta(200L, "Serie actualizada exitosamente.", List.of(updatedSeries));
        } catch (Exception e) {
            return new DetostrptSeries.MensajeRespuesta(500L, "Error al actualizar la serie: " + e.getMessage(), null);
        }
    }

    @Transactional
    public DetostrptSeries.MensajeRespuesta deleteDetostrptSeries(String wmsStorerkey, String wmsExternorderkey, Long wmsIddet, Long wmsIdserie) {
        try {
            DetostrptSeriesId id = new DetostrptSeriesId(wmsStorerkey, wmsExternorderkey, wmsIddet, wmsIdserie);
            if (detostrptSeriesRepository.existsById(id)) {
                detostrptSeriesRepository.deleteById(id);
                return new DetostrptSeries.MensajeRespuesta(200L, "Serie eliminada exitosamente.", null);
            }
            return new DetostrptSeries.MensajeRespuesta(204L, "Serie no encontrada.", null);
        } catch (Exception e) {
            return new DetostrptSeries.MensajeRespuesta(500L, "Error al eliminar la serie: " + e.getMessage(), null);
        }
    }

    public DetostrptSeries.MensajeRespuesta getSeriesByLpn(String wmsLpn) {
        try {
            List<DetostrptSeries> series = detostrptSeriesRepository.findByWmsLpn(wmsLpn);
            if (series.isEmpty()) {
                return new DetostrptSeries.MensajeRespuesta(204L, "No se encontraron series para el LPN especificado.", null);
            }
            return new DetostrptSeries.MensajeRespuesta(200L, "Series obtenidas exitosamente.", series);
        } catch (Exception e) {
            return new DetostrptSeries.MensajeRespuesta(500L, "Error al obtener las series por LPN: " + e.getMessage(), null);
        }
    }
}

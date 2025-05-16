package py.com.nsa.api.commons.components.cfg.detrecadvseries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.cfg.detrecadvseries.model.DetrecadvSeries;
import py.com.nsa.api.commons.components.cfg.detrecadvseries.model.DetrecadvSeriesId;
import py.com.nsa.api.commons.components.cfg.detrecadvseries.repository.DetrecadvSeriesRepository;

import java.util.List;

@Service
public class DetrecadvSeriesService {

    @Autowired
    private DetrecadvSeriesRepository detrecadvSeriesRepository;

    public DetrecadvSeries.MensajeRespuesta getAllDetrecadvSeries() {
        try {
            List<DetrecadvSeries> series = detrecadvSeriesRepository.findAll();
            if (series.isEmpty()) {
                return new DetrecadvSeries.MensajeRespuesta(204L, "No se encontraron series.", null);
            }
            return new DetrecadvSeries.MensajeRespuesta(200L, "Series obtenidas exitosamente.", series);
        } catch (Exception e) {
            return new DetrecadvSeries.MensajeRespuesta(500L, "Error al obtener las series: " + e.getMessage(), null);
        }
    }

    public DetrecadvSeries.MensajeRespuesta getSeriesByDetrecadvId(String wmsStorerkey, String wmsExternreceiptkey, Long wmsId) {
        try {
            List<DetrecadvSeries> series = detrecadvSeriesRepository.findByDetrecadvId(wmsStorerkey, wmsExternreceiptkey, wmsId);
            if (series.isEmpty()) {
                return new DetrecadvSeries.MensajeRespuesta(204L, "No se encontraron series para el detalle de recepción especificado.", null);
            }
            return new DetrecadvSeries.MensajeRespuesta(200L, "Series obtenidas exitosamente.", series);
        } catch (Exception e) {
            return new DetrecadvSeries.MensajeRespuesta(500L, "Error al obtener las series: " + e.getMessage(), null);
        }
    }

    @Transactional
    public DetrecadvSeries.MensajeRespuesta insertDetrecadvSeries(DetrecadvSeries detrecadvSeries) {
        try {
            DetrecadvSeries savedSeries = detrecadvSeriesRepository.save(detrecadvSeries);
            return new DetrecadvSeries.MensajeRespuesta(200L, "Serie creada exitosamente.", List.of(savedSeries));
        } catch (Exception e) {
            return new DetrecadvSeries.MensajeRespuesta(500L, "Error al insertar la serie: " + e.getMessage(), null);
        }
    }

    @Transactional
    public DetrecadvSeries.MensajeRespuesta updateDetrecadvSeries(DetrecadvSeries detrecadvSeries) {
        try {
            DetrecadvSeriesId id = new DetrecadvSeriesId(
                    detrecadvSeries.getWmsStorerkey(),
                    detrecadvSeries.getWmsExternreceiptkey(),
                    detrecadvSeries.getWmsId(),
                    detrecadvSeries.getWmsIdseries()
            );

            if (!detrecadvSeriesRepository.existsById(id)) {
                return new DetrecadvSeries.MensajeRespuesta(204L, "Serie no encontrada.", null);
            }

            DetrecadvSeries updatedSeries = detrecadvSeriesRepository.save(detrecadvSeries);
            return new DetrecadvSeries.MensajeRespuesta(200L, "Serie actualizada exitosamente.", List.of(updatedSeries));
        } catch (Exception e) {
            return new DetrecadvSeries.MensajeRespuesta(500L, "Error al actualizar la serie: " + e.getMessage(), null);
        }
    }

    @Transactional
    public DetrecadvSeries.MensajeRespuesta deleteDetrecadvSeries(String wmsStorerkey, String wmsExternreceiptkey, Long wmsId, Long wmsIdseries) {
        try {
            DetrecadvSeriesId id = new DetrecadvSeriesId(wmsStorerkey, wmsExternreceiptkey, wmsId, wmsIdseries);
            if (!detrecadvSeriesRepository.existsById(id)) {
                return new DetrecadvSeries.MensajeRespuesta(204L, "Serie no encontrada.", null);
            }

            detrecadvSeriesRepository.deleteById(id);
            return new DetrecadvSeries.MensajeRespuesta(200L, "Serie eliminada exitosamente.", null);
        } catch (Exception e) {
            return new DetrecadvSeries.MensajeRespuesta(500L, "Error al eliminar la serie: " + e.getMessage(), null);
        }
    }

    public DetrecadvSeries.MensajeRespuesta getSeriesByLpn(String wmsLpn) {
        try {
            List<DetrecadvSeries> series = detrecadvSeriesRepository.findByWmsLpn(wmsLpn);
            if (series.isEmpty()) {
                return new DetrecadvSeries.MensajeRespuesta(204L, "No se encontraron series para el LPN especificado.", null);
            }
            return new DetrecadvSeries.MensajeRespuesta(200L, "Series obtenidas exitosamente.", series);
        } catch (Exception e) {
            return new DetrecadvSeries.MensajeRespuesta(500L, "Error al obtener las series: " + e.getMessage(), null);
        }
    }

    public DetrecadvSeries.MensajeRespuesta getSeriesBySerialNumber(String wmsSerialNumber) {
        try {
            List<DetrecadvSeries> series = detrecadvSeriesRepository.findByWmsSerialNumber(wmsSerialNumber);
            if (series.isEmpty()) {
                return new DetrecadvSeries.MensajeRespuesta(204L, "No se encontraron series para el número de serie especificado.", null);
            }
            return new DetrecadvSeries.MensajeRespuesta(200L, "Series obtenidas exitosamente.", series);
        } catch (Exception e) {
            return new DetrecadvSeries.MensajeRespuesta(500L, "Error al obtener las series: " + e.getMessage(), null);
        }
    }
}
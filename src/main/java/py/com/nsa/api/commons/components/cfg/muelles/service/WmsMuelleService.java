package py.com.nsa.api.commons.components.cfg.muelles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.muelles.model.WmsMuelle;
import py.com.nsa.api.commons.components.cfg.muelles.repository.WmsMuelleRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class WmsMuelleService {

    @Autowired
    private WmsMuelleRepository wmsMuelleRepository;

    /**
     * Obtiene el muelle más cercano a las coordenadas especificadas.
     *
     * @param latitud La latitud de la ubicación
     * @param longitud La longitud de la ubicación
     * @return Mensaje de respuesta con el muelle más cercano
     */
    public WmsMuelle.MensajeRespuesta getNearestMuelle(Double latitud, Double longitud) {
        try {
            // Obtener todos los muelles ordenados por distancia
            List<Object[]> result = wmsMuelleRepository.findAllOrderByDistance(latitud, longitud);

            if (result.isEmpty()) {
                return new WmsMuelle.MensajeRespuesta(204L, "No se encontraron muelles.", null);
            }

            // Obtener solo el primer resultado (el más cercano)
            Object[] firstResult = result.get(0);
            WmsMuelle muelle = mapResultToWmsMuelle(firstResult);

            return new WmsMuelle.MensajeRespuesta(200L, "Muelle más cercano obtenido exitosamente.", List.of(muelle));
        } catch (Exception e) {
            return new WmsMuelle.MensajeRespuesta(500L, "Error al obtener el muelle más cercano: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene todos los muelles ordenados por distancia desde las coordenadas especificadas.
     *
     * @param latitud La latitud de la ubicación
     * @param longitud La longitud de la ubicación
     * @return Mensaje de respuesta con todos los muelles ordenados por distancia
     */
    public WmsMuelle.MensajeRespuesta getAllMuellesByDistance(Double latitud, Double longitud) {
        try {
            List<Object[]> results = wmsMuelleRepository.findAllOrderByDistance(latitud, longitud);

            if (results.isEmpty()) {
                return new WmsMuelle.MensajeRespuesta(204L, "No se encontraron muelles.", null);
            }

            List<WmsMuelle> muelles = new ArrayList<>();
            for (Object[] result : results) {
                muelles.add(mapResultToWmsMuelle(result));
            }

            return new WmsMuelle.MensajeRespuesta(200L, "Muelles obtenidos exitosamente.", muelles);
        } catch (Exception e) {
            return new WmsMuelle.MensajeRespuesta(500L, "Error al obtener los muelles: " + e.getMessage(), null);
        }
    }

    /**
     * Convierte un array de objetos resultante de la consulta nativa a un objeto WmsMuelle.
     *
     * @param result El array de objetos resultante de la consulta
     * @return Objeto WmsMuelle con los datos del resultado
     */
    private WmsMuelle mapResultToWmsMuelle(Object[] result) {
        WmsMuelle muelle = new WmsMuelle();

        muelle.setWmsId(((Number) result[0]).longValue());
        muelle.setWmsCiudad((String) result[1]);
        muelle.setWmsDepartamento((String) result[2]);
        muelle.setWmsLatitud(((Number) result[3]).doubleValue());
        muelle.setWmsLongitud(((Number) result[4]).doubleValue());
        muelle.setWmsCoordenadas(((Number) result[5]).doubleValue());
        muelle.setWmsMuelle(((Number) result[6]).longValue());
        muelle.setWmsUbZonaEspera((String) result[7]);
        muelle.setWmsZonaPack((String) result[8]);
        muelle.setWmsUbPack((String) result[9]);

        // Obtener la distancia calculada (el último campo)
        BigDecimal distancia = (BigDecimal) result[10];
        muelle.setDistancia(distancia.doubleValue());

        return muelle;
    }
}
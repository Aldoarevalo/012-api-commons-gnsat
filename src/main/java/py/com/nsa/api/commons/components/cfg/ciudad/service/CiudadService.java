package py.com.nsa.api.commons.components.cfg.ciudad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.ciudad.model.Ciudad;
import py.com.nsa.api.commons.components.cfg.ciudad.repository.CiudadRepository;

import java.util.List;

@Service
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    public Ciudad.MensajeRespuesta getCiudadesAll() {
        try {
            List<Ciudad> ciudades = ciudadRepository.findAll();

            if (ciudades.isEmpty()) {
                return new Ciudad.MensajeRespuesta(204L, "No se encontraron ciudades.", null);
            }
            return new Ciudad.MensajeRespuesta(200L, "Ciudades obtenidas exitosamente.", ciudades);
        } catch (Exception e) {
            return new Ciudad.MensajeRespuesta(500L, "Error al obtener las ciudades: " + e.getMessage(), null);
        }
    }

    public Ciudad.MensajeRespuesta insertarCiudad(Ciudad ciudad) {
        try {
            if (ciudadRepository.existsByCiuDescripcionIgnoreCaseAndDpCod(ciudad.getCiuDescripcion(), ciudad.getDpCod())) {
                return new Ciudad.MensajeRespuesta(204L, "Ya existe una ciudad con la misma descripción y mismo departamento.", null);
            }
            Ciudad nuevaCiudad = ciudadRepository.save(ciudad);
            return new Ciudad.MensajeRespuesta(200L, "Ciudad creada exitosamente.", List.of(nuevaCiudad));
        } catch (Exception e) {
            return new Ciudad.MensajeRespuesta(500L, "Error al insertar la ciudad: " + e.getMessage(), null);
        }
    }

    public Ciudad.MensajeRespuesta updateCiudad(Ciudad ciudad) {
        try {
            if (ciudadRepository.existsByCiuDescripcionIgnoreCaseAndDpCod(ciudad.getCiuDescripcion(), ciudad.getDpCod())) {
                return new Ciudad.MensajeRespuesta(204L, "Ya existe una ciudad con la misma descripción y mismo departamento.", null);
            }
            if (ciudad.getCiuCod() == null || !ciudadRepository.existsById(ciudad.getCiuCod())) {
                return new Ciudad.MensajeRespuesta(204L, "Ciudad no encontrada.", null);
            }
            Ciudad updatedCiudad = ciudadRepository.save(ciudad);
            return new Ciudad.MensajeRespuesta(200L, "Ciudad actualizada exitosamente.", List.of(updatedCiudad));
        } catch (Exception e) {
            return new Ciudad.MensajeRespuesta(500L, "Error al actualizar la ciudad: " + e.getMessage(), null);
        }
    }

    public Ciudad.MensajeRespuesta deleteCiudad(Long ciuCod) {
        try {
            if (ciudadRepository.existsById(ciuCod)) {
                ciudadRepository.deleteById(ciuCod);
                return new Ciudad.MensajeRespuesta(200L, "Ciudad eliminada exitosamente", null);
            } else {
                return new Ciudad.MensajeRespuesta(204L, "Ciudad con código " + ciuCod + " no encontrada", null);
            }
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error si está referenciada en otras tablas
            String mensaje = "No se puede eliminar la Ciudad porque está referenciada por otros registros";
            return new Ciudad.MensajeRespuesta(204L, "Error al eliminar la Ciudad: " + mensaje, null);
        } catch (DataIntegrityViolationException e) {
            // Controlamos la excepción de integridad de datos
            String mensaje = "No se puede eliminar la Ciudad porque está referenciada por otros registros.";
            return new Ciudad.MensajeRespuesta(204L, "Error al eliminar la Ciudad: " + mensaje, null);
        } catch (Exception e) {
            return new Ciudad.MensajeRespuesta(500L, "Error al eliminar la ciudad con código " + ciuCod + ": " + e.getMessage(), null);
        }
    }
}

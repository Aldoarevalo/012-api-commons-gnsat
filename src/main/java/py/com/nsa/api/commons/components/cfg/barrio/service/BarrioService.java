package py.com.nsa.api.commons.components.cfg.barrio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.barrio.model.Barrio;
import py.com.nsa.api.commons.components.cfg.barrio.repository.BarrioRepository;

import java.util.List;

@Service
public class BarrioService {

    @Autowired
    private BarrioRepository barrioRepository;

    public Barrio.MensajeRespuesta getBarriosAll() {
        try {
            List<Barrio> barrios = barrioRepository.findAll();
            if (barrios.isEmpty()) {
                return new Barrio.MensajeRespuesta(204L, "No se encontraron barrios.", null);
            }
            return new Barrio.MensajeRespuesta(200L, "Barrios obtenidos exitosamente.", barrios);
        } catch (Exception e) {
            return new Barrio.MensajeRespuesta(500L, "Error al obtener los barrios: " + e.getMessage(), null);
        }
    }

    public Barrio.MensajeRespuesta insertarBarrio(Barrio barrio) {
        try {
            if (barrioRepository.existsByBdescripcionIgnoreCaseAndCiuCod(barrio.getBdescripcion(),
                    barrio.getCiuCod())) {
                return new Barrio.MensajeRespuesta(204L, "Ya existe un barrio con la misma descripción y ciudad.",
                        null);
            }
            Barrio nuevoBarrio = barrioRepository.save(barrio);
            return new Barrio.MensajeRespuesta(200L, "Barrio creado exitosamente.", List.of(nuevoBarrio));
        } catch (Exception e) {
            return new Barrio.MensajeRespuesta(500L, "Error al insertar el barrio: " + e.getMessage(), null);
        }
    }

    public Barrio.MensajeRespuesta updateBarrio(Barrio barrio) {
        try {
            if (barrioRepository.existsByBdescripcionIgnoreCaseAndCiuCod(barrio.getBdescripcion(),
                    barrio.getCiuCod())) {
                return new Barrio.MensajeRespuesta(204L, "Ya existe un barrio con la misma descripción y ciudad.",
                        null);
            }
            if (barrio.getBCod() == null || !barrioRepository.existsById(barrio.getBCod())) {
                return new Barrio.MensajeRespuesta(204L, "Barrio no encontrado.", null);
            }
            Barrio updatedBarrio = barrioRepository.save(barrio);
            return new Barrio.MensajeRespuesta(200L, "Barrio actualizado exitosamente.", List.of(updatedBarrio));
        } catch (Exception e) {
            return new Barrio.MensajeRespuesta(500L, "Error al actualizar el barrio: " + e.getMessage(), null);
        }
    }

    public Barrio.MensajeRespuesta deleteBarrio(Long bCod) {
        try {
            if (barrioRepository.existsById(bCod)) {
                barrioRepository.deleteById(bCod);
                return new Barrio.MensajeRespuesta(200L, "Barrio eliminado exitosamente", null);
            } else {
                return new Barrio.MensajeRespuesta(204L, "Barrio con código " + bCod + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error si está referenciado en otras tablas
            String mensaje = "No se puede eliminar el Barrio porque está referenciado por otros registros.";
            return new Barrio.MensajeRespuesta(204L, "Error al eliminar el Barrio: " + mensaje, null);
        } catch (DataIntegrityViolationException e) {
            // Controlamos la excepción de integridad de datos
            String mensaje = "No se puede eliminar el Barrio porque está referenciado por otros registros.";
            return new Barrio.MensajeRespuesta(204L, "Error al eliminar el Barrio: " + mensaje, null);
        } catch (Exception e) {
            return new Barrio.MensajeRespuesta(500L,
                    "Error al eliminar el barrio con código " + bCod + ": " + e.getMessage(), null);
        }
    }



}

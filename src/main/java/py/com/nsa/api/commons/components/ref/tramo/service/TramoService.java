package py.com.nsa.api.commons.components.ref.tramo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.tramo.model.Tramo;
import py.com.nsa.api.commons.components.ref.tramo.repository.TramoRepository;

import java.util.List;

@Service
public class TramoService {

    @Autowired
    private TramoRepository repository;

    public Tramo.MensajeRespuesta getAllTramos() {
        try {
            List<Tramo> tramos = repository.findAll();
            if (tramos.isEmpty()) {
                return new Tramo.MensajeRespuesta(204L, "No se encontraron tramos.", null);
            }
            return new Tramo.MensajeRespuesta(200L, "Tramos obtenidos exitosamente.", tramos);
        } catch (Exception e) {
            return new Tramo.MensajeRespuesta(500L, "Error al obtener los tramos: " + e.getMessage(), null);
        }
    }

    public Tramo.MensajeRespuesta insert(Tramo tramo) {
        try {
            Tramo insertedTramo = repository.save(tramo);
            return new Tramo.MensajeRespuesta(200L, "Tramo insertado exitosamente.", List.of(insertedTramo));
        } catch (Exception e) {
            return new Tramo.MensajeRespuesta(500L, "Error al insertar el tramo: " + e.getMessage(), null);
        }
    }

    public Tramo.MensajeRespuesta update(Tramo tramo) {
        try {
            Tramo updatedTramo = repository.save(tramo);
            return new Tramo.MensajeRespuesta(200L, "Tramo actualizado exitosamente.", List.of(updatedTramo));
        } catch (Exception e) {
            return new Tramo.MensajeRespuesta(500L, "Error al actualizar el tramo: " + e.getMessage(), null);
        }
    }

    public Tramo.MensajeRespuesta deleteTramo(Long trCod) {
        try {
            // Verificar si el tramo existe antes de eliminarlo
            if (!repository.existsById(trCod)) {
                return new Tramo.MensajeRespuesta(404L, "Tramo con código " + trCod + " no encontrado.", null);
            }

            // Intentar eliminar el tramo
            repository.deleteById(trCod);
            return new Tramo.MensajeRespuesta(200L, "Tramo eliminado exitosamente.", null);

        } catch (DataIntegrityViolationException e) {
            // Manejar el caso en que el Tramo esté referenciado en otra tabla
            return new Tramo.MensajeRespuesta(409L,
                    "No se puede eliminar el tramo con código " + trCod + " porque está referenciado en otra tabla.", null);
        } catch (Exception e) {
            return new Tramo.MensajeRespuesta(500L,
                    "Error al eliminar el tramo con código " + trCod + ": " + e.getMessage(), null);
        }
    }

}

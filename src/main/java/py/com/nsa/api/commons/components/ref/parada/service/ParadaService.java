package py.com.nsa.api.commons.components.ref.parada.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.parada.model.Parada;
import py.com.nsa.api.commons.components.ref.parada.repository.ParadaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParadaService {

    @Autowired
    private ParadaRepository repository;

    public Parada.MensajeRespuesta getAllParadas() {
        try {
            List<Parada> paradas = repository.findAll();
            if (paradas.isEmpty()) {
                return new Parada.MensajeRespuesta(204L, "No se encontraron paradas.", null);
            }
            return new Parada.MensajeRespuesta(200L, "Paradas obtenidas exitosamente.", paradas);
        } catch (Exception e) {
            return new Parada.MensajeRespuesta(500L, "Error al obtener las paradas: " + e.getMessage(), null);
        }
    }

    public Parada.MensajeRespuesta insert(Parada parada) {
        try {
            Parada insertedParada = repository.save(parada);
            return new Parada.MensajeRespuesta(200L, "Parada insertada exitosamente.", List.of(insertedParada));
        } catch (Exception e) {
            return new Parada.MensajeRespuesta(500L, "Error al insertar la parada: " + e.getMessage(), null);
        }
    }

    public Parada.MensajeRespuesta update(Parada parada) {
        try {
            Parada updatedParada = repository.save(parada);
            return new Parada.MensajeRespuesta(200L, "Parada actualizada exitosamente.", List.of(updatedParada));
        } catch (Exception e) {
            return new Parada.MensajeRespuesta(500L, "Error al actualizar la parada: " + e.getMessage(), null);
        }
    }

    public Parada.MensajeRespuesta deleteParada(Long pcodigo) {
        try {
            if (repository.existsById(pcodigo)) {
                repository.deleteById(pcodigo);
                return new Parada.MensajeRespuesta(200L, "Parada eliminada exitosamente.", null);
            } else {
                return new Parada.MensajeRespuesta(404L, "Parada con código " + pcodigo + " no encontrada.", null);
            }
        } catch (JpaSystemException e) {
            String mensaje = "No se puede eliminar la parada porque está referenciada por otros registros.";
            return new Parada.MensajeRespuesta(409L, "Error al eliminar la parada: " + mensaje, null);
        } catch (Exception e) {
            return new Parada.MensajeRespuesta(500L, "Error al eliminar parada con código " + pcodigo + ": " + e.getMessage(), null);
        }
    }


}

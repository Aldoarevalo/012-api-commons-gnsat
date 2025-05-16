package py.com.nsa.api.commons.components.cfg.pais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;
import py.com.nsa.api.commons.components.cfg.pais.repository.PaisRepository;

import java.util.List;

@Service
public class PaisService {

    @Autowired
    private PaisRepository paisRepository;

    public Pais.MensajeRespuesta getPaisesAll() {
        try {
            List<Pais> paises = paisRepository.findAll();
            if (paises.isEmpty()) {
                return new Pais.MensajeRespuesta(204L, "No se encontraron países.", null);
            }
            return new Pais.MensajeRespuesta(200L, "Países obtenidos exitosamente.", paises);
        } catch (Exception e) {
            return new Pais.MensajeRespuesta(500L, "Error al obtener los países: " + e.getMessage(), null);
        }
    }

    public Pais.MensajeRespuesta insertarPais(Pais pais) {
        try {
            if (paisRepository.existsByPaDescripcionIgnoreCase(pais.getPaDescripcion())) {
                return new Pais.MensajeRespuesta(204L, "Ya existe un país con la misma descripción.", null);
            }
            Pais nuevoPais = paisRepository.save(pais);
            return new Pais.MensajeRespuesta(200L, "País creado exitosamente.", List.of(nuevoPais));
        } catch (Exception e) {
            return new Pais.MensajeRespuesta(500L, "Error al insertar el país: " + e.getMessage(), null);
        }
    }

    public Pais.MensajeRespuesta updatePais(Pais pais) {
        try {
            if (paisRepository.existsByPaDescripcionIgnoreCase(pais.getPaDescripcion())) {
                return new Pais.MensajeRespuesta(204L, "Ya existe un país con la misma descripción.", null);
            }
            if (pais.getPaCod() == null || !paisRepository.existsById(pais.getPaCod())) {
                return new Pais.MensajeRespuesta(204L, "País no encontrado.", null);
            }
            Pais updatedPais = paisRepository.save(pais);
            return new Pais.MensajeRespuesta(200L, "País actualizado exitosamente.", List.of(updatedPais));
        } catch (Exception e) {
            return new Pais.MensajeRespuesta(500L, "Error al actualizar el país: " + e.getMessage(), null);
        }
    }

    public Pais.MensajeRespuesta deletePais(Long paCod) {
        try {
            if (paisRepository.existsById(paCod)) {
                paisRepository.deleteById(paCod);
                return new Pais.MensajeRespuesta(200L, "País eliminado exitosamente", null);
            } else {
                return new Pais.MensajeRespuesta(204L, "País con código " + paCod + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error
            String mensaje = "No se puede eliminar el Pais porque está referenciado por otros registros"; // Mensaje
            // personalizado
            return new Pais.MensajeRespuesta(204L,
                    "Error al eliminar el Pais: " + mensaje, null);
        } catch (Exception e) {
            return new Pais.MensajeRespuesta(500L, "Error al eliminar el país con código " + paCod + ": " + e.getMessage(), null);
        }
    }
}

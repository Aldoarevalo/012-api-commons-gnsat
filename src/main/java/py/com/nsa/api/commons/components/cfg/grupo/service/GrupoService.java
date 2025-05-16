// Servicio (GrupoService.java)
package py.com.nsa.api.commons.components.cfg.grupo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.grupo.model.Grupo;
import py.com.nsa.api.commons.components.cfg.grupo.repository.GrupoRepository;
import java.util.List;

@Service
public class GrupoService {
    @Autowired
    private GrupoRepository repository;

    public Grupo.MensajeRespuesta getGruposAll() {
        try {
            List<Grupo> grupos = repository.findAll();

            if (grupos.isEmpty()) {
                return new Grupo.MensajeRespuesta(204L, "No se encontraron Grupos.", null);
            }
            return new Grupo.MensajeRespuesta(200L, "Grupos obtenidos exitosamente.", grupos);
        } catch (Exception e) {
            System.err.println("Error al obtener los Grupos: " + e.getMessage());
            e.printStackTrace();
            return new Grupo.MensajeRespuesta(500L, "Error al obtener los Grupos: " + e.getMessage(), null);
        }
    }

    public Grupo.MensajeRespuesta insert(Grupo grupo) {
        try {
            Grupo insertedGrupo = repository.save(grupo);
            return new Grupo.MensajeRespuesta(200L, "Grupo insertado exitosamente.", List.of(insertedGrupo));
        } catch (Exception e) {
            System.err.println("Error al insertar el grupo: " + e.getMessage());
            e.printStackTrace();
            return new Grupo.MensajeRespuesta(500L, "Error al insertar el grupo: " + e.getMessage(), null);
        }
    }

    public Grupo.MensajeRespuesta update(Grupo grupo) {
        try {
            Grupo updatedGrupo = repository.save(grupo);
            return new Grupo.MensajeRespuesta(200L, "Grupo actualizado exitosamente.", List.of(updatedGrupo));
        } catch (Exception e) {
            System.err.println("Error al actualizar el grupo: " + e.getMessage());
            e.printStackTrace();
            return new Grupo.MensajeRespuesta(500L, "Error al actualizar grupo: " + e.getMessage(), null);
        }
    }

    public boolean deleteById(Integer codigo) {
        try {
            repository.deleteById(codigo);
            return true;
        } catch (EmptyResultDataAccessException e) {
            // El grupo no existe
            return false;
        } catch (DataIntegrityViolationException e) {
            // El grupo no se puede eliminar debido a restricciones de integridad
            return false;
        } catch (Exception e) {
            // Cualquier otra excepción
            return false;
        }
    }
}

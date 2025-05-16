// Servicio (GrupoUsuarioService.java)
package py.com.nsa.api.commons.components.cfg.grupo_usuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.grupo_usuario.model.GrupoUsuario;
import py.com.nsa.api.commons.components.cfg.grupo_usuario.repository.GrupoUsuarioRepository;
import org.springframework.orm.jpa.JpaSystemException;
import java.util.List;

@Service
public class GrupoUsuarioService {

    @Autowired
    private GrupoUsuarioRepository grupoUsuarioRepository;

    public GrupoUsuario.MensajeRespuesta getGruposUsuariosAll() {
        try {
            List<GrupoUsuario> gruposUsuarios = grupoUsuarioRepository.findAll();
            if (gruposUsuarios.isEmpty()) {
                return new GrupoUsuario.MensajeRespuesta(204L, "No se encontraron asignaciones de grupos a usuarios.", null);
            }
            return new GrupoUsuario.MensajeRespuesta(200L, "Asignaciones obtenidas exitosamente.", gruposUsuarios);
        } catch (Exception e) {
            return new GrupoUsuario.MensajeRespuesta(500L, "Error al obtener las asignaciones: " + e.getMessage(), null);
        }
    }

    public GrupoUsuario.MensajeRespuesta insertarGrupoUsuario(GrupoUsuario grupoUsuario) {
        try {
            if (grupoUsuarioRepository.existsByUsuCodAndGruCod(grupoUsuario.getUsuCod(), grupoUsuario.getGruCod())) {
                return new GrupoUsuario.MensajeRespuesta(204L, "El usuario ya está asignado a este grupo.", null);
            }
            GrupoUsuario nuevoGrupoUsuario = grupoUsuarioRepository.save(grupoUsuario);
            return new GrupoUsuario.MensajeRespuesta(200L, "Asignación creada exitosamente.", List.of(nuevoGrupoUsuario));
        } catch (Exception e) {
            return new GrupoUsuario.MensajeRespuesta(500L, "Error al crear la asignación: " + e.getMessage(), null);
        }
    }

    public GrupoUsuario.MensajeRespuesta updateGrupoUsuario(GrupoUsuario grupoUsuario) {
        try {
            if (!grupoUsuarioRepository.existsById(grupoUsuario.getUsuGruCod())) {
                return new GrupoUsuario.MensajeRespuesta(204L, "Asignación no encontrada.", null);
            }
            GrupoUsuario updatedGrupoUsuario = grupoUsuarioRepository.save(grupoUsuario);
            return new GrupoUsuario.MensajeRespuesta(200L, "Asignación actualizada exitosamente.", List.of(updatedGrupoUsuario));
        } catch (Exception e) {
            return new GrupoUsuario.MensajeRespuesta(500L, "Error al actualizar la asignación: " + e.getMessage(), null);
        }
    }

    public GrupoUsuario.MensajeRespuesta deleteGrupoUsuario(Integer usuGruCod) {
        try {
            if (grupoUsuarioRepository.existsById(usuGruCod)) {
                grupoUsuarioRepository.deleteById(usuGruCod);
                return new GrupoUsuario.MensajeRespuesta(200L, "Asignación eliminada exitosamente", null);
            } else {
                return new GrupoUsuario.MensajeRespuesta(204L, "Asignación no encontrada", null);
            }
        } catch (JpaSystemException e) {
            return new GrupoUsuario.MensajeRespuesta(204L, "Error al eliminar la asignación: No se puede eliminar porque está siendo referenciada", null);
        } catch (Exception e) {
            return new GrupoUsuario.MensajeRespuesta(500L, "Error al eliminar la asignación: " + e.getMessage(), null);
        }
    }
}

package py.com.nsa.api.commons.components.ref.permiso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.permiso.model.Permiso;
import py.com.nsa.api.commons.components.ref.permiso.repository.PermisoRepository;

import java.util.List;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public Permiso.MensajeRespuesta getPermisosall() {
        try {
            List<Permiso> permisos = permisoRepository.findAll();
            if (permisos != null && !permisos.isEmpty()) {
                return new Permiso.MensajeRespuesta("ok", "Permisos obtenidos exitosamente.", permisos);
            } else {
                return new Permiso.MensajeRespuesta("info", "No se encontraron permisos.", null);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los permisos: " + e.getMessage());
            e.printStackTrace();
            return new Permiso.MensajeRespuesta("error", "Error al obtener los permisos: " + e.getMessage(), null);
        }
    }

    public Permiso.MensajeRespuesta insertPermiso(Permiso permiso) {
        if (permisoRepository.existsByperNombreIgnoreCase(permiso.getPerNombre())) {
            return new Permiso.MensajeRespuesta("error", "Ya existe un permiso con el mismo nombre.", null);
        }
        try {
            Permiso insertedPermiso = permisoRepository.save(permiso);
            return new Permiso.MensajeRespuesta("ok", "Permiso insertado exitosamente.", List.of(insertedPermiso));
        } catch (Exception e) {
            System.err.println("Error al insertar el permiso: " + e.getMessage());
            e.printStackTrace();
            return new Permiso.MensajeRespuesta("error", "Error al insertar el permiso: " + e.getMessage(), null);
        }
    }

    public Permiso.MensajeRespuesta updatePermiso(Permiso permiso) {
        if (permisoRepository.existsByperNombreIgnoreCase(permiso.getPerNombre())) {
            return new Permiso.MensajeRespuesta("error", "Ya existe un permiso con el mismo nombre.", null);
        }
        try {

            Permiso updatedPermiso = permisoRepository.save(permiso);
            return new Permiso.MensajeRespuesta("ok", "Permiso actualizado exitosamente.", List.of(updatedPermiso));
        } catch (Exception e) {
            System.err.println("Error al actualizar el permiso: " + e.getMessage());
            e.printStackTrace();
            return new Permiso.MensajeRespuesta("error", "Error al actualizar el permiso: " + e.getMessage(), null);
        }
    }

    public Permiso.MensajeRespuesta deletePermiso(Long perCodigo) {
        try {
            if (permisoRepository.existsById(perCodigo)) {
                permisoRepository.deleteById(perCodigo);
                return new Permiso.MensajeRespuesta("ok", "Permiso con código " + perCodigo + " eliminado exitosamente",
                        null);
            } else {
                return new Permiso.MensajeRespuesta("error", "Permiso con código " + perCodigo + " no encontrado",
                        null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el Permiso porque está referenciado por otros registros.";
            return new Permiso.MensajeRespuesta("error", "Error al eliminar el Permiso: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Permiso.MensajeRespuesta("error",
                    "Error al eliminar el permiso con código " + perCodigo + ": " + e.getMessage(), null);
        }
    }
}

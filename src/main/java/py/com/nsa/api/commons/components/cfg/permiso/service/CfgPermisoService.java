package py.com.nsa.api.commons.components.cfg.permiso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.permiso.model.CfgPermiso;
import py.com.nsa.api.commons.components.cfg.permiso.repository.CfgPermisoRepository;

import java.util.List;

@Service
public class CfgPermisoService {
    @Autowired
    private CfgPermisoRepository repository;

    public CfgPermiso.MensajeRespuesta getPermisosAll() {
        try {
            List<CfgPermiso> roles = repository.findAll();

            if (roles.isEmpty()) {
                return new CfgPermiso.MensajeRespuesta(204L, "No se encontraron Permisos.", null);
            }
            return new CfgPermiso.MensajeRespuesta(200L, "Permisos obtenidos exitosamente.", roles);
        } catch (Exception e) {
            System.err.println("Error al obtener los Permisos: " + e.getMessage());
            e.printStackTrace();
            return new CfgPermiso.MensajeRespuesta(500L, "Error al obtener los Permisos: " + e.getMessage(), null);
        }
    }

    public CfgPermiso.MensajeRespuesta insert(CfgPermiso permiso) {
        try {
            if (repository.existsByPerNombreIgnoreCaseAndPerTipoObj(permiso.getPerNombre(), permiso.getPerTipoObj())) {
                return new CfgPermiso.MensajeRespuesta(204L,
                        "Ya existe un permiso con el mismo nombre y tipo de objeto.", null);
            }
            CfgPermiso inserted = repository.save(permiso);
            return new CfgPermiso.MensajeRespuesta(200L, "Permiso insertado exitosamente.", List.of(inserted));
        } catch (Exception e) {
            System.err.println("Error al insertar el permiso: " + e.getMessage());
            e.printStackTrace();
            return new CfgPermiso.MensajeRespuesta(500L, "Error al insertar el permiso: " + e.getMessage(), null);
        }
    }

    public CfgPermiso.MensajeRespuesta update(CfgPermiso permiso) {
        try {
            if (repository.existsByPerNombreIgnoreCaseAndPerTipoObj(permiso.getPerNombre(), permiso.getPerTipoObj())) {
                return new CfgPermiso.MensajeRespuesta(204L,
                        "Ya existe un permiso con el mismo nombre y tipo de objeto.", null);
            }
            CfgPermiso updatedAgencia = repository.save(permiso);
            return new CfgPermiso.MensajeRespuesta(200L, "Permiso actualizado exitosamente. ", List.of(updatedAgencia));
        } catch (Exception e) {
            System.err.println("Error al actualizar el permiso: " + e.getMessage());
            e.printStackTrace();
            return new CfgPermiso.MensajeRespuesta(500L, "Error al actualizar permiso: " + e.getMessage(), null);
        }
    }

    public CfgPermiso.MensajeRespuesta deletePermiso(Long perCodigo) {
        try {
            if (repository.existsById(perCodigo)) {
                repository.deleteById(perCodigo);
                return new CfgPermiso.MensajeRespuesta(200L, "Permiso eliminado exitosamente.", null);
            } else {
                return new CfgPermiso.MensajeRespuesta(204L, "Permiso con código " + perCodigo + " no encontrado.",
                        null);
            }
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error
            String mensaje = "No se puede eliminar el permiso porque está referenciado por otros registros"; // Mensaje
                                                                                                             // personalizado
            return new CfgPermiso.MensajeRespuesta(204L,
                    "Error al eliminar el permiso: " + mensaje, null);
        } catch (Exception e) {
            // Captura genérica para cualquier otra excepción
            System.err.println("Clase de la excepción lanzada: " + e.getClass().getName());
            e.printStackTrace(); // Ayuda a identificar la excepción en tiempo de ejecución
            return new CfgPermiso.MensajeRespuesta(500L,
                    "Error al eliminar el permiso con código " + perCodigo + ": " + e.getMessage(), null);
        }
    }

}
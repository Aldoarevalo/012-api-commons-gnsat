package py.com.nsa.api.commons.components.ref.estadotarea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.estadotarea.model.EstadoTarea;
import py.com.nsa.api.commons.components.ref.estadotarea.repository.EstadoTareaRepository;

import java.util.List;

@Service
public class EstadoTareaService {

    @Autowired
    private EstadoTareaRepository estadotareaRepository;

    public EstadoTarea.MensajeRespuesta getTareaAll() {
        try {
            List<EstadoTarea> estadotareas = estadotareaRepository.findAll();
            if (estadotareas != null && !estadotareas.isEmpty()) {
                return new EstadoTarea.MensajeRespuesta("ok", "Estados de tarea obtenidos exitosamente.", estadotareas);
            } else {
                return new EstadoTarea.MensajeRespuesta("info", "No se encontraron estados de tarea.", null);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los estados de tarea: " + e.getMessage());
            e.printStackTrace();
            return new EstadoTarea.MensajeRespuesta("error", "Error al obtener los estados de tarea: " + e.getMessage(),
                    null);
        }
    }

    public EstadoTarea.MensajeRespuesta insertEstadoTarea(EstadoTarea estadotarea) {
        // Verifica si existe una tarea con el mismo nombre
        if (estadotareaRepository.existsByEtaNombreIgnoreCase(estadotarea.getEtaNombre())) {
            return new EstadoTarea.MensajeRespuesta("error", "Ya existe una tarea con el mismo nombre.", null);
        }
        try {
            EstadoTarea insertedEstadoTarea = estadotareaRepository.save(estadotarea);
            return new EstadoTarea.MensajeRespuesta("ok", "Estado de tarea insertado exitosamente.",
                    List.of(insertedEstadoTarea));
        } catch (Exception e) {
            System.err.println("Error al insertar el estado de tarea: " + e.getMessage());
            e.printStackTrace();
            return new EstadoTarea.MensajeRespuesta("error", "Error al insertar el estado de tarea: " + e.getMessage(),
                    null);
        }
    }

    public EstadoTarea.MensajeRespuesta updateEstadoTarea(EstadoTarea estadotarea) {
        if (estadotareaRepository.existsByEtaNombreIgnoreCase(estadotarea.getEtaNombre())) {
            return new EstadoTarea.MensajeRespuesta("error", "Ya existe una tarea con el mismo nombre.", null);
        }
        try {
            EstadoTarea updatedEstadoTarea = estadotareaRepository.save(estadotarea);
            return new EstadoTarea.MensajeRespuesta("ok", "Estado de tarea actualizado exitosamente.",
                    List.of(updatedEstadoTarea));
        } catch (Exception e) {
            System.err.println("Error al actualizar el estado de tarea: " + e.getMessage());
            e.printStackTrace();
            return new EstadoTarea.MensajeRespuesta("error",
                    "Error al actualizar el estado de tarea: " + e.getMessage(), null);
        }
    }

    public EstadoTarea.MensajeRespuesta deleteEstadoTarea(Long etaCodigo) {
        try {
            if (estadotareaRepository.existsById(etaCodigo)) {
                estadotareaRepository.deleteById(etaCodigo);
                return new EstadoTarea.MensajeRespuesta("ok",
                        "Estado de tarea con código " + etaCodigo + " eliminado exitosamente", null);
            } else {
                return new EstadoTarea.MensajeRespuesta("error",
                        "Estado de tarea con código " + etaCodigo + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el Estado de Tarea porque está referenciado por otros registros.";
            return new EstadoTarea.MensajeRespuesta("error", "Error al eliminar el Estado de Tarea: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoTarea.MensajeRespuesta("error",
                    "Error al eliminar el estado de tarea con código " + etaCodigo + ": " + e.getMessage(), null);
        }
    }
}

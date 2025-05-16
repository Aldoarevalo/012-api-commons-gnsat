package py.com.nsa.api.commons.components.ref.recorrido.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.recorrido.model.Recorrido;
import py.com.nsa.api.commons.components.ref.recorrido.repository.RecorridoRepository;

import java.util.List;

@Service
public class RecorridoService {

    @Autowired
    private RecorridoRepository recorridoRepository;

    public Recorrido.MensajeRespuesta getRecorridosAll() {
        try {
            List<Recorrido> recorridos = recorridoRepository.findAll();
            if (recorridos != null && !recorridos.isEmpty()) {
                return new Recorrido.MensajeRespuesta("ok", "Recorridos obtenidos exitosamente.", recorridos);
            } else {
                return new Recorrido.MensajeRespuesta("info", "No se encontraron recorridos.", null);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los recorridos: " + e.getMessage());
            e.printStackTrace();
            return new Recorrido.MensajeRespuesta("error", "Error al obtener los recorridos: " + e.getMessage(), null);
        }
    }

    public Recorrido.MensajeRespuesta insertRecorrido(Recorrido recorrido) {
        try {
            Recorrido insertedRecorrido = recorridoRepository.save(recorrido);
            return new Recorrido.MensajeRespuesta("ok", "Recorrido insertado exitosamente.", List.of(insertedRecorrido));
        } catch (Exception e) {
            System.err.println("Error al insertar el recorrido: " + e.getMessage());
            e.printStackTrace();
            return new Recorrido.MensajeRespuesta("error", "Error al insertar el recorrido: " + e.getMessage(), null);
        }
    }

    public Recorrido.MensajeRespuesta updateRecorrido(Recorrido recorrido) {
        try {
            Recorrido updatedRecorrido = recorridoRepository.save(recorrido);
            return new Recorrido.MensajeRespuesta("ok", "Recorrido actualizado exitosamente.", List.of(updatedRecorrido));
        } catch (Exception e) {
            System.err.println("Error al actualizar el recorrido: " + e.getMessage());
            e.printStackTrace();
            return new Recorrido.MensajeRespuesta("error", "Error al actualizar el recorrido: " + e.getMessage(), null);
        }
    }

    public Recorrido.MensajeRespuesta deleteRecorrido(Long recCodigo) {
        try {
            if (recorridoRepository.existsById(recCodigo)) {
                recorridoRepository.deleteById(recCodigo);
                return new Recorrido.MensajeRespuesta("ok", "Recorrido con código " + recCodigo + " eliminado exitosamente", null);
            } else {
                return new Recorrido.MensajeRespuesta("error", "Recorrido con código " + recCodigo + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el Recorrido porque está referenciado por otros registros.";
            return new Recorrido.MensajeRespuesta("error", "Error al eliminar el Recorrido: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Recorrido.MensajeRespuesta("error", "Error al eliminar el recorrido con código " + recCodigo + ": " + e.getMessage(), null);
        }
    }
}

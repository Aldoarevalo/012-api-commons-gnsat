package py.com.nsa.api.commons.components.ref.tiposervicio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.tiposervicio.model.TipoServicio;
import py.com.nsa.api.commons.components.ref.tiposervicio.repository.TipoServicioRepository;

import java.util.List;

@Service
public class TipoServicioService {

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    public TipoServicio.MensajeRespuesta getTipoServiciosAll() {
        try {
            List<TipoServicio> tiposervicios = tipoServicioRepository.findAll();
            if (tiposervicios != null && !tiposervicios.isEmpty()) {
                return new TipoServicio.MensajeRespuesta("ok", "Tipos de Servicios obtenidos exitosamente.",
                        tiposervicios);
            } else {
                return new TipoServicio.MensajeRespuesta("info", "No se encontraron tipos de servicios.", null);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los tipos de servicios: " + e.getMessage());
            e.printStackTrace();
            return new TipoServicio.MensajeRespuesta("error",
                    "Error al obtener los tipos de servicios: " + e.getMessage(), null);
        }
    }

    public TipoServicio.MensajeRespuesta insertTisServicio(TipoServicio tipoServicio) {
        try {
            TipoServicio insertedTipoServicio = tipoServicioRepository.save(tipoServicio);
            return new TipoServicio.MensajeRespuesta("ok", "Tipo de servicio insertado exitosamente.",
                    List.of(insertedTipoServicio));
        } catch (Exception e) {
            System.err.println("Error al insertar el tipo de servicio: " + e.getMessage());
            e.printStackTrace();
            return new TipoServicio.MensajeRespuesta("error",
                    "Error al insertar el tipo de servicio: " + e.getMessage(), null);
        }
    }

    public TipoServicio.MensajeRespuesta updateTisServicio(TipoServicio tipoServicio) {
        try {
            TipoServicio updatedTipoServicio = tipoServicioRepository.save(tipoServicio);
            return new TipoServicio.MensajeRespuesta("ok", "Tipo de servicio actualizado exitosamente.",
                    List.of(updatedTipoServicio));
        } catch (Exception e) {
            System.err.println("Error al actualizar el tipo de servicio: " + e.getMessage());
            e.printStackTrace();
            return new TipoServicio.MensajeRespuesta("error",
                    "Error al actualizar el tipo de servicio: " + e.getMessage(), null);
        }
    }

    public TipoServicio.MensajeRespuesta deleteTipoServicio(Long codigo) {
        try {
            if (tipoServicioRepository.existsById(codigo)) {
                tipoServicioRepository.deleteById(codigo);
                return new TipoServicio.MensajeRespuesta("ok",
                        "Tipo de servicio con código " + codigo + " eliminado exitosamente", null);
            } else {
                return new TipoServicio.MensajeRespuesta("error",
                        "Tipo de servicio con código " + codigo + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            String mensaje = "No se puede eliminar el Tipo de Servicio porque está referenciado por otros registros.";
            return new TipoServicio.MensajeRespuesta("error", "Error al eliminar el Tipo de Servicio: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new TipoServicio.MensajeRespuesta("error",
                    "Error al eliminar el tipo de servicio con código " + codigo + ": " + e.getMessage(), null);
        }
    }
}

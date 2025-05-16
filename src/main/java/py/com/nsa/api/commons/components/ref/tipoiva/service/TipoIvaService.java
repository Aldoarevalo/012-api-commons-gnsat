package py.com.nsa.api.commons.components.ref.tipoiva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.tipoiva.modelo.TipoIva;
import py.com.nsa.api.commons.components.ref.tipoiva.repository.TipoIvaRepository;

import java.util.List;

@Service
public class TipoIvaService {

    @Autowired
    private TipoIvaRepository tipoIvaRepository;

    public TipoIva.MensajeRespuesta getTiposIvaAll() {
        try {
            List<TipoIva> tiposIva = tipoIvaRepository.findAll();
            if (tiposIva.isEmpty()) {
                return new TipoIva.MensajeRespuesta(204L, "No se encontraron tipos de IVA.", null);
            }
            return new TipoIva.MensajeRespuesta(200L, "Tipos de IVA obtenidos exitosamente.", tiposIva);
        } catch (Exception e) {
            e.printStackTrace();
            return new TipoIva.MensajeRespuesta(500L, "Error al obtener tipos de IVA: " + e.getMessage(), null);
        }
    }

    public TipoIva.MensajeRespuesta insertarTipoIva(TipoIva tipoIva) {
        try {
            TipoIva nuevoTipoIva = tipoIvaRepository.save(tipoIva);
            return new TipoIva.MensajeRespuesta(200L, "Tipo de IVA creado exitosamente.", List.of(nuevoTipoIva));
        } catch (Exception e) {
            e.printStackTrace();
            return new TipoIva.MensajeRespuesta(500L, "Error al insertar tipo de IVA: " + e.getMessage(), null);
        }
    }

    public TipoIva.MensajeRespuesta updateTipoIva(TipoIva tipoIva) {
        try {
            if (tipoIva.getIvaCodigo() == null || !tipoIvaRepository.existsById(tipoIva.getIvaCodigo())) {
                return new TipoIva.MensajeRespuesta(204L, "Tipo de IVA no encontrado.", null);
            }
            TipoIva updatedTipoIva = tipoIvaRepository.save(tipoIva);
            return new TipoIva.MensajeRespuesta(200L, "Tipo de IVA actualizado exitosamente.", List.of(updatedTipoIva));
        } catch (Exception e) {
            e.printStackTrace();
            return new TipoIva.MensajeRespuesta(500L, "Error al actualizar tipo de IVA: " + e.getMessage(), null);
        }
    }

    public TipoIva.MensajeRespuesta deleteTipoIva(Long ivaCodigo) {
        try {
            if (tipoIvaRepository.existsById(ivaCodigo)) {
                tipoIvaRepository.deleteById(ivaCodigo);
                return new TipoIva.MensajeRespuesta(200L, "Tipo de IVA eliminado exitosamente.", null);
            } else {
                return new TipoIva.MensajeRespuesta(204L, "Tipo de IVA con código " + ivaCodigo + " no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el Tipo de Iva porque está referenciado por otros registros.";
            return new TipoIva.MensajeRespuesta(204L, "Error al eliminar el Tipo de Iva: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new TipoIva.MensajeRespuesta(500L, "Error al eliminar tipo de IVA con código " + ivaCodigo + ": " + e.getMessage(), null);
        }
    }

    public boolean deleteById(Long tdoCodigo) {
        try {
            tipoIvaRepository.deleteById(tdoCodigo);
            return true;
        } catch (EmptyResultDataAccessException e) {
            // El tipo de iva no existe
            return false;
        } catch (DataIntegrityViolationException e) {
            // El tipo de iva no se puede eliminar debido a restricciones de integridad
            return false;
        } catch (Exception e) {
            // Cualquier otra excepción
            return false;
        }
    }

}

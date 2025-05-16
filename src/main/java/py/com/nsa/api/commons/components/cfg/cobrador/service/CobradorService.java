package py.com.nsa.api.commons.components.cfg.cobrador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.cobrador.model.Cobrador;
import py.com.nsa.api.commons.components.cfg.cobrador.model.pk.CobradorPK;
import py.com.nsa.api.commons.components.cfg.cobrador.repository.CobradorRepository;

import java.util.List;

@Service
public class CobradorService {

    @Autowired
    private CobradorRepository cobradorRepository;

    public Cobrador.MensajeRespuesta getCobradoresAll() {
        try {
            List<Cobrador> cobradores = cobradorRepository.findAll();
            if (cobradores.isEmpty()) {
                return new Cobrador.MensajeRespuesta(200L, "No se encontraron cobradores.", null);
            }
            return new Cobrador.MensajeRespuesta(200L, "Cobradores obtenidos exitosamente.", cobradores);
        } catch (Exception e) {
            return new Cobrador.MensajeRespuesta(500L, "Error al obtener los cobradores: " + e.getMessage(), null);
        }
    }

    public Cobrador.MensajeRespuesta insertarCobrador(Cobrador cobrador) {
        try {
            Long nextCobrCodigo = cobradorRepository.getNextCobrCodigoByCarCodigo(
                    cobrador.getCarCodigo());
            cobrador.setCobrCodigo(nextCobrCodigo);
            Cobrador nuevoCobrador = cobradorRepository.save(cobrador);
            return new Cobrador.MensajeRespuesta(200L, "Cobrador creado exitosamente.", List.of(nuevoCobrador));
        } catch (Exception e) {
            return new Cobrador.MensajeRespuesta(500L, "Error al insertar el cobrador: " + e.getMessage(), null);
        }
    }

    public Cobrador.MensajeRespuesta updateCobrador(Cobrador cobrador) {
        try {
            if (cobradorRepository.existsById(new CobradorPK(cobrador.getCobrCodigo(), cobrador.getCarCodigo()))) {
                Cobrador updatedCobrador = cobradorRepository.save(cobrador);
                return new Cobrador.MensajeRespuesta(200L, "Cobrador actualizado exitosamente.", List.of(updatedCobrador));
            } else {
                return new Cobrador.MensajeRespuesta(204L, "Cobrador no encontrado.", null);
            }
        } catch (Exception e) {
            return new Cobrador.MensajeRespuesta(500L, "Error al actualizar el cobrador: " + e.getMessage(), null);
        }
    }

    public Cobrador.MensajeRespuesta deleteCobrador(Long cobrCodigo, Long carCodigo) {
        try {
            CobradorPK cobradorPK = new CobradorPK(cobrCodigo, carCodigo);
            if (cobradorRepository.existsById(cobradorPK)) {
                cobradorRepository.deleteById(cobradorPK);
                return new Cobrador.MensajeRespuesta(200L, "Cobrador eliminado exitosamente.", null);
            } else {
                return new Cobrador.MensajeRespuesta(204L, "Cobrador no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error
            String mensaje = "No se puede eliminar el Cobrador porque está referenciado por otros registros"; // Mensaje
            // personalizado
            return new Cobrador.MensajeRespuesta(204L,
                    "Error al eliminar el cobrador: " + mensaje, null);
        } catch (Exception e) {
            return new Cobrador.MensajeRespuesta(500L, "Error al eliminar el cobrador: " + e.getMessage(), null);
        }
    }
}

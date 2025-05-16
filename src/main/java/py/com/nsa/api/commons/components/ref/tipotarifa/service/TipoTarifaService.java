package py.com.nsa.api.commons.components.ref.tipotarifa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.tipotarifa.model.TipoTarifa;
import py.com.nsa.api.commons.components.ref.tipotarifa.repository.TipoTarifaRepository;

import java.util.List;

@Service
public class TipoTarifaService {

    @Autowired
    private TipoTarifaRepository tipotarifaRepository;

    public TipoTarifa.MensajeRespuesta getTipoTarifaAll() {
        try {
            List<TipoTarifa> tipotarifas = tipotarifaRepository.findAll();
            if (tipotarifas != null && !tipotarifas.isEmpty()) {
                return new TipoTarifa.MensajeRespuesta("ok", "Tipos de tarifa obtenidos exitosamente.", tipotarifas);
            } else {
                return new TipoTarifa.MensajeRespuesta("info", "No se encontraron tipos de tarifa.", null);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los tipos de tarifa: " + e.getMessage());
            e.printStackTrace();
            return new TipoTarifa.MensajeRespuesta("error", "Error al obtener los tipos de tarifa: " + e.getMessage(),
                    null);
        }
    }

    public TipoTarifa.MensajeRespuesta insertTipoTarifa(TipoTarifa tipotarifa) {
        if (tipotarifaRepository.existsByTitNombreAndTitDescripcionIgnoreCase(tipotarifa.getTitNombre(),
                tipotarifa.getTitDescripcion())) {
            return new TipoTarifa.MensajeRespuesta("error", "Ya existe una tarifa con el mismo tipo y nombre.", null);
        }
        try {
            TipoTarifa insertedTipoTarifa = tipotarifaRepository.save(tipotarifa);
            return new TipoTarifa.MensajeRespuesta("ok", "Tipo de tarifa insertado exitosamente.",
                    List.of(insertedTipoTarifa));
        } catch (Exception e) {
            System.err.println("Error al insertar el tipo de tarifa: " + e.getMessage());
            e.printStackTrace();
            return new TipoTarifa.MensajeRespuesta("error", "Error al insertar el tipo de tarifa: " + e.getMessage(),
                    null);
        }
    }

    public TipoTarifa.MensajeRespuesta updateTipoTarifa(TipoTarifa tipotarifa) {
        /*
         * if
         * (tipotarifaRepository.existsByTitNombreAndTitDescripcionIgnoreCase(tipotarifa
         * .getTitNombre(),
         * tipotarifa.getTitDescripcion())) {
         * return new TipoTarifa.MensajeRespuesta("error",
         * "Ya existe una tarifa con el mismo tipo y nombre.", null);
         * }
         */
        try {
            TipoTarifa updatedTipoTarifa = tipotarifaRepository.save(tipotarifa);
            return new TipoTarifa.MensajeRespuesta("ok", "Tipo de tarifa actualizado exitosamente.",
                    List.of(updatedTipoTarifa));
        } catch (Exception e) {
            System.err.println("Error al actualizar el tipo de tarifa: " + e.getMessage());
            e.printStackTrace();
            return new TipoTarifa.MensajeRespuesta("error", "Error al actualizar el tipo de tarifa: " + e.getMessage(),
                    null);
        }
    }

    public TipoTarifa.MensajeRespuesta deleteTipoTarifa(Long titCodigo) {
        try {
            if (tipotarifaRepository.existsById(titCodigo)) {
                tipotarifaRepository.deleteById(titCodigo);
                return new TipoTarifa.MensajeRespuesta("ok",
                        "Tipo de tarifa con código " + titCodigo + " eliminado exitosamente", null);
            } else {
                return new TipoTarifa.MensajeRespuesta("error",
                        "Tipo de tarifa con código " + titCodigo + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el tipo de tarifa porque está referenciado por otros registros.";
            return new TipoTarifa.MensajeRespuesta("error", "Error al eliminar el tipo de tarifa: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new TipoTarifa.MensajeRespuesta("error",
                    "Error al eliminar el tipo de tarifa con código " + titCodigo + ": " + e.getMessage(), null);
        }
    }
}

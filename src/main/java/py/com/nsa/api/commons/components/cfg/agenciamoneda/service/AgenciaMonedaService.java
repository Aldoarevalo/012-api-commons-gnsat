package py.com.nsa.api.commons.components.cfg.agenciamoneda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.agenciamoneda.model.AgenciaMoneda;
import py.com.nsa.api.commons.components.cfg.agenciamoneda.model.pk.AgenciaMonedaPK;
import py.com.nsa.api.commons.components.cfg.agenciamoneda.repository.AgenciaMonedaRepository;

import java.util.List;

@Service
public class AgenciaMonedaService {

    @Autowired
    private AgenciaMonedaRepository agenciaMonedaRepository;

    public AgenciaMoneda.MensajeRespuesta getAgenciaMonedasAll() {
        try {
            List<AgenciaMoneda> agenciaMonedas = agenciaMonedaRepository.findAll();
            if (agenciaMonedas.isEmpty()) {
                return new AgenciaMoneda.MensajeRespuesta(204L, "No se encontraron monedas de agencia.", null);
            }
            return new AgenciaMoneda.MensajeRespuesta(200L, "Monedas de agencia obtenidas exitosamente.", agenciaMonedas);
        } catch (Exception e) {
            e.printStackTrace();
            return new AgenciaMoneda.MensajeRespuesta(500L, "Error al obtener monedas de agencia: " + e.getMessage(), null);
        }
    }

    public AgenciaMoneda.MensajeRespuesta insertarAgenciaMoneda(AgenciaMoneda agenciaMoneda) {
        try {
            AgenciaMoneda nuevaAgenciaMoneda = agenciaMonedaRepository.save(agenciaMoneda);
            return new AgenciaMoneda.MensajeRespuesta(200L, "Moneda de agencia creada exitosamente.", List.of(nuevaAgenciaMoneda));
        } catch (Exception e) {
            e.printStackTrace();
            return new AgenciaMoneda.MensajeRespuesta(500L, "Error al insertar la moneda de agencia: " + e.getMessage(), null);
        }
    }

    /*public AgenciaMoneda.MensajeRespuesta updateAgenciaMoneda(AgenciaMoneda agenciaMoneda) {
        try {
            if (agenciaMoneda.getAgCod() == null || agenciaMoneda.getParMoneda() == null) {
                return new AgenciaMoneda.MensajeRespuesta(204L, "Código de agencia o moneda no pueden ser nulos.", null);
            }

            AgenciaMonedaPK agenciaMonedaPK = new AgenciaMonedaPK(agenciaMoneda.getAgCod(), agenciaMoneda.getParMoneda());

            if (!agenciaMonedaRepository.existsById(agenciaMonedaPK)) {
                return new AgenciaMoneda.MensajeRespuesta(204L, "Moneda de agencia no encontrada.", null);
            }

            AgenciaMoneda updatedAgenciaMoneda = agenciaMonedaRepository.save(agenciaMoneda);
            return new AgenciaMoneda.MensajeRespuesta(200L, "Moneda de agencia actualizada exitosamente.", List.of(updatedAgenciaMoneda));
        } catch (Exception e) {
            e.printStackTrace();
            return new AgenciaMoneda.MensajeRespuesta(500L, "Error al actualizar la moneda de agencia: " + e.getMessage(), null);
        }
    }*/

    public AgenciaMoneda.MensajeRespuesta deleteAgenciaMoneda(Long agCod, String parMoneda) {
        try {
            // Crear la clave primaria compuesta
            AgenciaMonedaPK agenciaMonedaPK = new AgenciaMonedaPK(agCod, parMoneda);

            // Verificar si el registro con la clave primaria compuesta existe
            if (agenciaMonedaRepository.existsById(agenciaMonedaPK)) {
                // Eliminar el registro si existe
                agenciaMonedaRepository.deleteById(agenciaMonedaPK);
                return new AgenciaMoneda.MensajeRespuesta(200L, "Moneda de agencia eliminada exitosamente.", null);
            } else {
                // Si no existe, devolver respuesta con código 204
                return new AgenciaMoneda.MensajeRespuesta(204L, "Moneda de agencia no encontrada.", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que la Agencia Moneda está referenciada por otros registros
            String mensaje = "No se puede eliminar la Moneda de Agencia porque está referenciada por otros registros.";
            return new AgenciaMoneda.MensajeRespuesta(204L, "Error al eliminar la Moneda de Agencia: " + mensaje, null);
        } catch (Exception e) {
            // Otros errores generales
            return new AgenciaMoneda.MensajeRespuesta(500L, "Error al eliminar la moneda de agencia: " + e.getMessage(), null);
        }
    }

}
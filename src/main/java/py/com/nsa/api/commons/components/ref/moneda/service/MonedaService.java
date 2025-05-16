package py.com.nsa.api.commons.components.ref.moneda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.moneda.model.Moneda;
import py.com.nsa.api.commons.components.ref.moneda.repository.MonedaRepository;

import java.util.List;

@Service
public class MonedaService {

    @Autowired
    private MonedaRepository monedaRepository;

    public Moneda.MensajeRespuesta getMonedasall() {
        try {
            List<Moneda> monedas = monedaRepository.findAll();
            if (monedas != null && !monedas.isEmpty()) {
                return new Moneda.MensajeRespuesta("ok", "Monedas obtenidas exitosamente.", monedas);
            } else {
                return new Moneda.MensajeRespuesta("info", "No se encontraron monedas.", null);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener las monedas: " + e.getMessage());
            e.printStackTrace();
            return new Moneda.MensajeRespuesta("error", "Error al obtener las monedas: " + e.getMessage(), null);
        }
    }

    public Moneda.MensajeRespuesta insertMoneda(Moneda moneda) {
        if (monedaRepository.existsByMonNombreIgnoreCase(moneda.getMonNombre())
                && monedaRepository.existsByMonAbreviacionIgnoreCase(moneda.getMonAbreviacion())) {
            return new Moneda.MensajeRespuesta("error", "Ya existe una moneda con el mismo nombre y abreviación.",
                    null);
        }
        try {
            Moneda insertedMoneda = monedaRepository.save(moneda);
            return new Moneda.MensajeRespuesta("ok", "Moneda insertada exitosamente.", List.of(insertedMoneda));
        } catch (Exception e) {
            System.err.println("Error al insertar la moneda: " + e.getMessage());
            e.printStackTrace();
            return new Moneda.MensajeRespuesta("error", "Error al insertar la moneda: " + e.getMessage(), null);
        }
    }

    public Moneda.MensajeRespuesta updateMoneda(Moneda moneda) {
        if (monedaRepository.existsByMonNombreIgnoreCase(moneda.getMonNombre())
                && monedaRepository.existsByMonAbreviacionIgnoreCase(moneda.getMonAbreviacion())) {
            return new Moneda.MensajeRespuesta("error", "Ya existe una moneda con el mismo nombre y abreviación.",
                    null);
        }

        try {
            Moneda updatedMoneda = monedaRepository.save(moneda);
            return new Moneda.MensajeRespuesta("ok", "Moneda actualizada exitosamente.", List.of(updatedMoneda));
        } catch (Exception e) {
            System.err.println("Error al actualizar la moneda: " + e.getMessage());
            e.printStackTrace();
            return new Moneda.MensajeRespuesta("error", "Error al actualizar la moneda: " + e.getMessage(), null);
        }
    }

    public Moneda.MensajeRespuesta deleteMoneda(Long monedaCodigo) {
        try {
            if (monedaRepository.existsById(monedaCodigo)) {
                monedaRepository.deleteById(monedaCodigo);
                return new Moneda.MensajeRespuesta("ok",
                        "Moneda con código " + monedaCodigo + " eliminada exitosamente", null);
            } else {
                return new Moneda.MensajeRespuesta("error", "Moneda con código " + monedaCodigo + " no encontrada",
                        null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar la Moneda porque está referenciada por otros registros.";
            return new Moneda.MensajeRespuesta("error", "Error al eliminar la Moneda: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Moneda.MensajeRespuesta("error",
                    "Error al eliminar la moneda con código " + monedaCodigo + ": " + e.getMessage(), null);
        }
    }
}

package py.com.nsa.api.commons.components.cfg.comision.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.comision.model.Comision;
import py.com.nsa.api.commons.components.cfg.comision.repository.ComisionRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ComisionService {

    @Autowired
    private ComisionRepository comisionRepository;

    public Comision.MensajeRespuesta getComisionesAll() {
        try {
            List<Comision> comisiones = comisionRepository.findAll();
            if (comisiones.isEmpty()) {
                return new Comision.MensajeRespuesta(204L, "No se encontraron comisiones.", null);
            }
            return new Comision.MensajeRespuesta(200L, "Comisiones obtenidas exitosamente.", comisiones);
        } catch (Exception e) {
            return new Comision.MensajeRespuesta(500L, "Error al obtener las comisiones: " + e.getMessage(), null);
        }
    }

    public Comision.MensajeRespuesta getComisionById(Integer comCod) {
        try {
            Optional<Comision> comisionOptional = comisionRepository.findById(comCod);
            if (comisionOptional.isPresent()) {
                return new Comision.MensajeRespuesta(
                        200L,
                        "Comisión encontrada exitosamente.",
                        List.of(comisionOptional.get())
                );
            } else {
                return new Comision.MensajeRespuesta(
                        204L,
                        "Comisión con código " + comCod + " no encontrada.",
                        null
                );
            }
        } catch (Exception e) {
            return new Comision.MensajeRespuesta(
                    500L,
                    "Error al obtener la comisión: " + e.getMessage(),
                    null
            );
        }
    }

    public Comision.MensajeRespuesta getComisionesByFiltros(
            String tipoTransac, String moneda, String estado, Integer codAg) {
        try {
            List<Comision> comisiones = comisionRepository.findByMultiplesFiltros(
                    tipoTransac, moneda, estado, codAg);

            if (comisiones.isEmpty()) {
                return new Comision.MensajeRespuesta(
                        204L,
                        "No se encontraron comisiones con los filtros especificados.",
                        null
                );
            }
            return new Comision.MensajeRespuesta(
                    200L,
                    "Comisiones obtenidas exitosamente.",
                    comisiones
            );
        } catch (Exception e) {
            return new Comision.MensajeRespuesta(
                    500L,
                    "Error al obtener comisiones: " + e.getMessage(),
                    null
            );
        }
    }

    public Comision.MensajeRespuesta insertarComision(Comision comision) {
        try {
            // Establecer las fechas de auditoría
            Date fechaActual = new Date();
            comision.setCodFechaGrab(fechaActual);
            comision.setCodFechaMod(fechaActual);

            Comision nuevaComision = comisionRepository.save(comision);
            return new Comision.MensajeRespuesta(
                    200L,
                    "Comisión creada exitosamente.",
                    List.of(nuevaComision)
            );
        } catch (Exception e) {

            System.out.println("Offshi loco");
            e.printStackTrace();  // <-- Esto imprime todo el stack completo

            return new Comision.MensajeRespuesta(
                    500L,
                    "Error al insertar la comisión: " + e.getMessage(),
                    null
            );
        }
    }

    public Comision.MensajeRespuesta updateComision(Comision comision) {
        try {
            if (!comisionRepository.existsById(comision.getComCod())) {
                return new Comision.MensajeRespuesta(
                        204L,
                        "Comisión no encontrada.",
                        null
                );
            }

            // Actualizar fecha de modificación
            comision.setCodFechaMod(new Date());

            Comision updatedComision = comisionRepository.save(comision);
            return new Comision.MensajeRespuesta(
                    200L,
                    "Comisión actualizada exitosamente.",
                    List.of(updatedComision)
            );
        } catch (Exception e) {
            return new Comision.MensajeRespuesta(
                    500L,
                    "Error al actualizar la comisión: " + e.getMessage(),
                    null
            );
        }
    }

    @Transactional
    public Comision.MensajeRespuesta deleteComision(Integer comCod) {
        try {
            if (comisionRepository.existsById(comCod)) {
                comisionRepository.deleteById(comCod);
                return new Comision.MensajeRespuesta(
                        200L,
                        "Comisión eliminada exitosamente",
                        null
                );
            } else {
                return new Comision.MensajeRespuesta(
                        204L,
                        "Comisión con código " + comCod + " no encontrada",
                        null
                );
            }
        } catch (DataIntegrityViolationException e) {
            String mensaje = "No se puede eliminar la comisión porque está referenciada por otros registros";
            return new Comision.MensajeRespuesta(204L, mensaje, null);
        } catch (Exception e) {
            return new Comision.MensajeRespuesta(
                    500L,
                    "Error al eliminar la comisión con código " + comCod + ": " + e.getMessage(),
                    null
            );
        }
    }
}


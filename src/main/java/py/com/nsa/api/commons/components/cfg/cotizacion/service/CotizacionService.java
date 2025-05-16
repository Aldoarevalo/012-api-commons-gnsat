package py.com.nsa.api.commons.components.cfg.cotizacion.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.cotizacion.model.Cotizacion;
import py.com.nsa.api.commons.components.cfg.cotizacion.repository.CotizacionRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    public Cotizacion.MensajeRespuesta getCotizacionesAll() {
        try {
            List<Cotizacion> cotizaciones = cotizacionRepository.findAll();
            if (cotizaciones.isEmpty()) {
                return new Cotizacion.MensajeRespuesta(204L, "No se encontraron cotizaciones.", null);
            }
            return new Cotizacion.MensajeRespuesta(200L, "Cotizaciones obtenidas exitosamente.", cotizaciones);
        } catch (Exception e) {
            return new Cotizacion.MensajeRespuesta(500L, "Error al obtener las cotizaciones: " + e.getMessage(), null);
        }
    }

    public Cotizacion.MensajeRespuesta getCotizacionById(Integer cotCod) {
        try {
            Optional<Cotizacion> cotizacionOptional = cotizacionRepository.findById(cotCod);
            if (cotizacionOptional.isPresent()) {
                return new Cotizacion.MensajeRespuesta(
                        200L,
                        "Cotización encontrada exitosamente.",
                        List.of(cotizacionOptional.get())
                );
            } else {
                return new Cotizacion.MensajeRespuesta(
                        204L,
                        "Cotización con código " + cotCod + " no encontrada.",
                        null
                );
            }
        } catch (Exception e) {
            return new Cotizacion.MensajeRespuesta(
                    500L,
                    "Error al obtener la cotización: " + e.getMessage(),
                    null
            );
        }
    }

    public Cotizacion.MensajeRespuesta getCotizacionesVigentes() {
        try {
            Date fechaActual = new Date();
            List<Cotizacion> cotizaciones = cotizacionRepository.findCotizacionesVigentes(fechaActual);
            if (cotizaciones.isEmpty()) {
                return new Cotizacion.MensajeRespuesta(
                        204L,
                        "No se encontraron cotizaciones vigentes.",
                        null
                );
            }
            return new Cotizacion.MensajeRespuesta(
                    200L,
                    "Cotizaciones vigentes obtenidas exitosamente.",
                    cotizaciones
            );
        } catch (Exception e) {
            return new Cotizacion.MensajeRespuesta(
                    500L,
                    "Error al obtener las cotizaciones vigentes: " + e.getMessage(),
                    null
            );
        }
    }

    public Cotizacion.MensajeRespuesta insertarCotizacion(Cotizacion cotizacion) {
        try {
            // Establecer las fechas de auditoría
            Date fechaActual = new Date();
            cotizacion.setCotFechaGrab(fechaActual);
            cotizacion.setCotFechaMod(fechaActual);

            System.out.println(" nuevaCotizacion es : ");
            Cotizacion nuevaCotizacion = cotizacionRepository.save(cotizacion);
            System.out.println(nuevaCotizacion);

            return new Cotizacion.MensajeRespuesta(
                    200L,
                    "Cotización creada exitosamente.",
                    List.of(nuevaCotizacion)
            );
        } catch (Exception e) {
            System.out.println("Offshi loco");
            e.printStackTrace();  // <-- Esto imprime todo el stack completo
            return new Cotizacion.MensajeRespuesta(
                    500L,
                    "Error al insertar la cotización: " + e.getMessage(),
                    null
            );
        }
    }

    public Cotizacion.MensajeRespuesta updateCotizacion(Cotizacion cotizacion) {
        try {
            if (!cotizacionRepository.existsById(cotizacion.getCotCod())) {
                return new Cotizacion.MensajeRespuesta(
                        204L,
                        "Cotización no encontrada.",
                        null
                );
            }

            // Actualizar fecha de modificación
            cotizacion.setCotFechaMod(new Date());

            Cotizacion updatedCotizacion = cotizacionRepository.save(cotizacion);
            return new Cotizacion.MensajeRespuesta(
                    200L,
                    "Cotización actualizada exitosamente.",
                    List.of(updatedCotizacion)
            );
        } catch (Exception e) {

            e.printStackTrace();  // <-- Esto imprime todo el stack completo

            return new Cotizacion.MensajeRespuesta(
                    500L,
                    "Error al actualizar la cotización: " + e.getMessage(),
                    null
            );
        }
    }

    @Transactional
    public Cotizacion.MensajeRespuesta deleteCotizacion(Integer cotCod) {
        try {
            if (cotizacionRepository.existsById(cotCod)) {
                cotizacionRepository.deleteById(cotCod);
                return new Cotizacion.MensajeRespuesta(
                        200L,
                        "Cotización eliminada exitosamente",
                        null
                );
            } else {
                return new Cotizacion.MensajeRespuesta(
                        204L,
                        "Cotización con código " + cotCod + " no encontrada",
                        null
                );
            }
        } catch (DataIntegrityViolationException e) {
            String mensaje = "No se puede eliminar la cotización porque está referenciada por otros registros";
            return new Cotizacion.MensajeRespuesta(204L, mensaje, null);
        } catch (Exception e) {
            return new Cotizacion.MensajeRespuesta(
                    500L,
                    "Error al eliminar la cotización con código " + cotCod + ": " + e.getMessage(),
                    null
            );
        }
    }

    public Cotizacion.MensajeRespuesta getByFiltros(String cotMoneda, String cotTipoTransac, String cotEstado) {
        try {
            List<Cotizacion> cotizaciones;

            if (cotMoneda != null && cotTipoTransac != null && cotEstado != null) {
                cotizaciones = cotizacionRepository
                        .findByCotMonedaAndCotTipoTransacAndCotEstado(cotMoneda, cotTipoTransac, cotEstado);
            } else if (cotMoneda != null) {
                cotizaciones = cotizacionRepository.findByCotMoneda(cotMoneda);
            } else if (cotTipoTransac != null) {
                cotizaciones = cotizacionRepository.findByCotTipoTransac(cotTipoTransac);
            } else if (cotEstado != null) {
                cotizaciones = cotizacionRepository.findByCotEstado(cotEstado);
            } else {
                cotizaciones = cotizacionRepository.findAll();
            }

            if (cotizaciones.isEmpty()) {
                return new Cotizacion.MensajeRespuesta(
                        204L,
                        "No se encontraron cotizaciones con los filtros especificados.",
                        null
                );
            }
            return new Cotizacion.MensajeRespuesta(
                    200L,
                    "Cotizaciones obtenidas exitosamente.",
                    cotizaciones
            );
        } catch (Exception e) {
            return new Cotizacion.MensajeRespuesta(
                    500L,
                    "Error al obtener cotizaciones: " + e.getMessage(),
                    null
            );
        }
    }
}
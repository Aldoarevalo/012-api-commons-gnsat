package py.com.nsa.api.commons.components.cfg.cotizacion.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.cotizacion.model.Cotizacion;
import py.com.nsa.api.commons.components.cfg.cotizacion.service.CotizacionService;

@RestController
@RequestMapping("/cotizacion")
public class CotizacionController {
    private static final Logger logger = LoggerFactory.getLogger(CotizacionController.class);

    @Autowired
    private CotizacionService cotizacionService;

    @GetMapping("/lista")
    public ResponseEntity<Cotizacion.MensajeRespuesta> getCotizacionesAll() {
        try {
            Cotizacion.MensajeRespuesta respuesta = cotizacionService.getCotizacionesAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener las cotizaciones: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cotizacion.MensajeRespuesta(500L, "Error al obtener cotizaciones: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Cotizacion.MensajeRespuesta> getCotizacionById(@PathVariable("codigo") Integer codigo) {
        try {
            Cotizacion.MensajeRespuesta respuesta = cotizacionService.getCotizacionById(codigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener la cotización con ID {}: {} ===>", codigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cotizacion.MensajeRespuesta(500L, "Error al obtener la cotización: " + e.getMessage(), null));
        }
    }

    @GetMapping("/vigentes")
    public ResponseEntity<Cotizacion.MensajeRespuesta> getCotizacionesVigentes() {
        try {
            Cotizacion.MensajeRespuesta respuesta = cotizacionService.getCotizacionesVigentes();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener las cotizaciones vigentes: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cotizacion.MensajeRespuesta(500L, "Error al obtener cotizaciones vigentes: " + e.getMessage(), null));
        }
    }

    @GetMapping("/filtrar")
    public ResponseEntity<Cotizacion.MensajeRespuesta> getCotizacionesFiltradas(
            @RequestParam(required = false) String moneda,
            @RequestParam(required = false) String tipoTransac,
            @RequestParam(required = false) String estado) {
        try {
            Cotizacion.MensajeRespuesta respuesta = cotizacionService.getByFiltros(moneda, tipoTransac, estado);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al filtrar cotizaciones: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cotizacion.MensajeRespuesta(500L, "Error al filtrar cotizaciones: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Cotizacion.MensajeRespuesta> insertarCotizacion(
            @Valid @RequestBody Cotizacion cotizacion) {
        try {
            System.out.println("Ingresó en la funcion de cotización.");
            System.out.println(cotizacion);
            Cotizacion.MensajeRespuesta respuesta = cotizacionService.insertarCotizacion(cotizacion);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("Algo salió mal loco");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error("<=== Error al insertar la cotización  - - : {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cotizacion.MensajeRespuesta(500L, "Error al insertar la cotización: " + e.getMessage(), null));

//            e.printStackTrace(); // Para ver el error completo
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new Cotizacion.MensajeRespuesta(500L, "Error al insertar la cotización: " + e.getMessage(), null));

        }
    }

    @PutMapping("/update")
    public ResponseEntity<Cotizacion.MensajeRespuesta> updateCotizacion(
            @Valid @RequestBody Cotizacion cotizacion) {
        try {
            Cotizacion.MensajeRespuesta respuesta = cotizacionService.updateCotizacion(cotizacion);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar la cotización con ID {}: {} ===>", cotizacion.getCotCod(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cotizacion.MensajeRespuesta(500L, "Error al actualizar la cotización: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<Cotizacion.MensajeRespuesta> delete(@PathVariable("codigo") Integer codigo) {
        try {
            Cotizacion.MensajeRespuesta respuesta = cotizacionService.deleteCotizacion(codigo);

            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 204L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar la cotización con código {}: {} ===>", codigo, e.getMessage(), e);

            Cotizacion.MensajeRespuesta errorRespuesta = new Cotizacion.MensajeRespuesta(
                    500L,
                    "Error al eliminar la cotización: " + e.getMessage(),
                    null
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorRespuesta);
        }
    }
}
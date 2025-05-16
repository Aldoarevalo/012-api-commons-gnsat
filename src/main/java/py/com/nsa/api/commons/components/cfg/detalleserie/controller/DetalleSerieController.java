package py.com.nsa.api.commons.components.cfg.detalleserie.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.detalleserie.model.DetalleSerie;
import py.com.nsa.api.commons.components.cfg.detalleserie.service.DetalleSerieService;

import java.util.Map;

@RestController
@RequestMapping("/detalle-serie")
public class DetalleSerieController {

    @Autowired
    private DetalleSerieService detalleSerieService;

    @GetMapping("/lista")
    public ResponseEntity<DetalleSerie.MensajeRespuesta> getDetalleSerieAll() {
        try {
            DetalleSerie.MensajeRespuesta respuesta = detalleSerieService.getDetalleSeriesAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DetalleSerie.MensajeRespuesta(500L, "Error al obtener los detalles de serie: " + e.getMessage(), null));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<DetalleSerie.MensajeRespuesta> getDetalleSerieBysCod(
            @RequestParam(required = false) String sCod,
            @RequestBody(required = false) Map<String, String> filtro) {
        try {
            // Si no viene por parámetro, intentar obtener del body
            String codigoSerie = sCod;
            if (codigoSerie == null && filtro != null) {
                codigoSerie = filtro.get("sCod");
            }

            // Validar que tengamos el código de alguna forma
            if (codigoSerie == null || codigoSerie.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new DetalleSerie.MensajeRespuesta(400L,
                                "El código de serie es requerido", null));
            }

            DetalleSerie.MensajeRespuesta respuesta =
                    detalleSerieService.getDetalleSerieBysCod(codigoSerie);

            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DetalleSerie.MensajeRespuesta(500L,
                            "Error al buscar detalles de serie: " + e.getMessage(), null));
        }
    }



    @PostMapping("/insert")
    public ResponseEntity<DetalleSerie.MensajeRespuesta> insertarDetalleSerie(@Valid @RequestBody DetalleSerie detalle) {
        try {
            DetalleSerie.MensajeRespuesta respuesta = detalleSerieService.insertarDetalleSerie(detalle);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DetalleSerie.MensajeRespuesta(500L, "Error al insertar el detalle: " + e.getMessage(), null));
        }
    }


    @PutMapping("/update")
    public ResponseEntity<DetalleSerie.MensajeRespuesta> updateDetalleSerie(
            @Valid @RequestBody DetalleSerie detalleSerie) {
        try {
            DetalleSerie.MensajeRespuesta respuesta = detalleSerieService.updateDetalleSerie(detalleSerie);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DetalleSerie.MensajeRespuesta(500L, "Error al actualizar DetalleSerie: " + e.getMessage(), null));
        }
    }


}

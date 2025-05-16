package py.com.nsa.api.commons.components.ref.tarifaruta.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.tarifaruta.model.TarifaRuta;
import py.com.nsa.api.commons.components.ref.tarifaruta.service.TarifaRutaService;

@RestController
@RequestMapping("/tarifa-ruta")
public class TarifaRutaController {

    @Autowired
    private TarifaRutaService service;

    private static final Logger logger = LoggerFactory.getLogger(TarifaRutaController.class);

    @GetMapping("/lista")
    public ResponseEntity<TarifaRuta.MensajeRespuesta> getAll() {
        try {
            TarifaRuta.MensajeRespuesta respuesta = service.getAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener tarifas de ruta: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TarifaRuta.MensajeRespuesta(500L, "Error al obtener tarifas de ruta: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<TarifaRuta.MensajeRespuesta> getFiltered(@RequestBody TarifaRuta filtro) {
        try {
            TarifaRuta.MensajeRespuesta respuesta = service.getFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al filtrar tarifas de ruta: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TarifaRuta.MensajeRespuesta(500L, "Error al filtrar tarifas de ruta: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<TarifaRuta.MensajeRespuesta> insert(@Valid @RequestBody TarifaRuta tarifaRuta) {
        try {
            TarifaRuta.MensajeRespuesta respuesta = service.insert(tarifaRuta);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al insertar tarifa de ruta: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TarifaRuta.MensajeRespuesta(500L, "Error al insertar tarifa de ruta: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TarifaRuta.MensajeRespuesta> update(@Valid @RequestBody TarifaRuta tarifaRuta) {
        try {
            TarifaRuta.MensajeRespuesta respuesta = service.update(tarifaRuta);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al actualizar tarifa de ruta: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TarifaRuta.MensajeRespuesta(500L, "Error al actualizar tarifa de ruta: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<TarifaRuta.MensajeRespuesta> delete(
            @RequestParam Long rucCod, @RequestParam Long rudSecuencia) {
        try {
            TarifaRuta.MensajeRespuesta respuesta = service.deleteById(rucCod, rudSecuencia);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al eliminar tarifa de ruta con rucCod {} y rudSecuencia {}: {}", rucCod, rudSecuencia, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TarifaRuta.MensajeRespuesta(500L, "Error al eliminar tarifa de ruta: " + e.getMessage(), null));
        }
    }
}
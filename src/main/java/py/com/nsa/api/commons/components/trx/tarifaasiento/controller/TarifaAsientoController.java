package py.com.nsa.api.commons.components.trx.tarifaasiento.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.trx.tarifaasiento.model.TarifaAsiento;
import py.com.nsa.api.commons.components.trx.tarifaasiento.service.TarifaAsientoService;

@RestController
@RequestMapping("/tarifa-asiento")
public class TarifaAsientoController {

    private static final Logger logger = LoggerFactory.getLogger(TarifaAsientoController.class);

    @Autowired
    private TarifaAsientoService tarifaAsientoService;

    @GetMapping("/lista")
    public ResponseEntity<TarifaAsiento.MensajeRespuesta> getTarifasAll() {
        try {
            TarifaAsiento.MensajeRespuesta respuesta = tarifaAsientoService.getTarifasAll();
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener las tarifas: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TarifaAsiento.MensajeRespuesta(500L, "Error al obtener las tarifas: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<TarifaAsiento.MensajeRespuesta> getTarifasFiltered(@RequestBody TarifaAsiento filtro) {
        try {
            TarifaAsiento.MensajeRespuesta respuesta = tarifaAsientoService.getTarifasFiltered(filtro);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener tarifas filtradas: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TarifaAsiento.MensajeRespuesta(500L, "Error al obtener tarifas filtradas: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<TarifaAsiento.MensajeRespuesta> insertTarifa(@Valid @RequestBody TarifaAsiento tarifa) {
        try {
            logger.debug("Recibiendo petición POST con tarifa: {}", tarifa);
            TarifaAsiento.MensajeRespuesta respuesta = tarifaAsientoService.insertarTarifa(tarifa);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else if (respuesta.getStatus() == 409L) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar la tarifa: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TarifaAsiento.MensajeRespuesta(500L, "Error al insertar la tarifa: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TarifaAsiento.MensajeRespuesta> updateTarifa(@Valid @RequestBody TarifaAsiento tarifa) {
        try {
            logger.info("Actualizando tarifa con combinación: parAsiento={}, vehiculoCod={}, proCod={}",
                    tarifa.getParAsiento(), tarifa.getVehiculoCod(), tarifa.getProCod());
            TarifaAsiento.MensajeRespuesta respuesta = tarifaAsientoService.updateTarifa(tarifa);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar la tarifa: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TarifaAsiento.MensajeRespuesta(500L, "Error al actualizar la tarifa: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<TarifaAsiento.MensajeRespuesta> deleteTarifa(
            @RequestParam("parAsiento") String parAsiento,
            @RequestParam("vehiculoCod") Integer vehiculoCod,
            @RequestParam("proCod") String proCod) {
        try {
            TarifaAsiento.MensajeRespuesta respuesta = tarifaAsientoService.deleteTarifa(parAsiento, vehiculoCod, proCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else if (respuesta.getStatus() == 409L) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar la tarifa: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TarifaAsiento.MensajeRespuesta(500L, "Error al eliminar la tarifa: " + e.getMessage(), null));
        }
    }
}
package py.com.nsa.api.commons.components.trx.transferenciaproddet.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.trx.transferenciaproddet.model.TransferenciaProductoDetalle;
import py.com.nsa.api.commons.components.trx.transferenciaproddet.service.TransferenciaProductoDetalleService;

import java.util.Map;

@RestController
@RequestMapping("/transferencia-producto-detalle")
public class TransferenciaProductoDetalleController {

    private static final Logger logger = LoggerFactory.getLogger(TransferenciaProductoDetalleController.class);

    @Autowired
    private TransferenciaProductoDetalleService detalleService;

    /**
     * Obtiene todos los detalles de transferencia de productos
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/lista")
    public ResponseEntity<TransferenciaProductoDetalle.MensajeRespuesta> getDetallesAll() {
        try {
            TransferenciaProductoDetalle.MensajeRespuesta respuesta = detalleService.getDetallesAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los detalles de transferencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al obtener los detalles de transferencia:" + e.getMessage(), null));
        }
    }

    /**
     * Obtiene un detalle de transferencia específico
     * @param trfpCod Código de la transferencia
     * @param trfpdLinea Número de línea del detalle
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/buscar")
    public ResponseEntity<TransferenciaProductoDetalle.MensajeRespuesta> getDetalleById(
            @RequestParam("trfpCod") Long trfpCod,
            @RequestParam("trfpdLinea") Integer trfpdLinea) {
        try {
            TransferenciaProductoDetalle.MensajeRespuesta respuesta = detalleService.getDetalleById(trfpCod, trfpdLinea);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al buscar el detalle de transferencia: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene detalles de transferencia por código de transferencia
     * @param trfpCod Código de la transferencia
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/transferencia/{trfpCod}")
    public ResponseEntity<TransferenciaProductoDetalle.MensajeRespuesta> getDetallesByTransferencia(@PathVariable Long trfpCod) {
        try {
            TransferenciaProductoDetalle.MensajeRespuesta respuesta = detalleService.getDetallesByTransferencia(trfpCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al buscar detalles por transferencia: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene detalles de transferencia por línea de referencia
     * @param trfpCod Código de la transferencia
     * @param trfpdRefLinea Número de línea de referencia
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/referencia")
    public ResponseEntity<TransferenciaProductoDetalle.MensajeRespuesta> getDetallesByLineaReferencia(
            @RequestParam("trfpCod") Long trfpCod,
            @RequestParam("trfpdRefLinea") Integer trfpdRefLinea) {
        try {
            TransferenciaProductoDetalle.MensajeRespuesta respuesta = detalleService.getDetallesByLineaReferencia(trfpCod, trfpdRefLinea);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al buscar detalles por línea de referencia: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene detalles de transferencia filtrados
     * @param filtro Objeto TransferenciaProductoDetalle con los criterios de filtrado
     * @return ResponseEntity con la respuesta de la operación
     */
    @PostMapping("/lista-filtro")
    public ResponseEntity<TransferenciaProductoDetalle.MensajeRespuesta> getDetallesFiltered(@RequestBody TransferenciaProductoDetalle filtro) {
        try {
            TransferenciaProductoDetalle.MensajeRespuesta respuesta = detalleService.getDetallesFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener detalles filtrados: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al obtener detalles filtrados: " + e.getMessage(), null));
        }
    }

    /**
     * Inserta un nuevo detalle de transferencia de producto
     * @param detalle Detalle de transferencia a insertar
     * @return ResponseEntity con la respuesta de la operación
     */
    @PostMapping("/insert")
    public ResponseEntity<TransferenciaProductoDetalle.MensajeRespuesta> insertDetalle(@Valid @RequestBody TransferenciaProductoDetalle detalle) {
        try {
            logger.debug("Recibiendo petición POST con detalle de transferencia: {}", detalle);

            TransferenciaProductoDetalle.MensajeRespuesta respuesta = detalleService.insertarDetalle(detalle);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (409L == respuesta.getStatus()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el detalle de transferencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al insertar el detalle de transferencia: " + e.getMessage(), null));
        }
    }

    /**
     * Actualiza un detalle de transferencia existente
     * @param detalle Detalle de transferencia con datos actualizados
     * @return ResponseEntity con la respuesta de la operación
     */
    @PutMapping("/update")
    public ResponseEntity<TransferenciaProductoDetalle.MensajeRespuesta> updateDetalle(@Valid @RequestBody TransferenciaProductoDetalle detalle) {
        try {
            logger.info("Actualizando detalle de transferencia con código: {} y línea: {}", detalle.getTrfpCod(), detalle.getTrfpdLinea());
            TransferenciaProductoDetalle.MensajeRespuesta respuesta = detalleService.updateDetalle(detalle);

            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else if (respuesta.getStatus() == 409L) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el detalle de transferencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al actualizar el detalle de transferencia: " + e.getMessage(), null));
        }
    }

    /**
     * Elimina un detalle de transferencia
     * @param trfpCod Código de la transferencia
     * @param trfpdLinea Número de línea del detalle
     * @return ResponseEntity con la respuesta de la operación
     */
    @DeleteMapping("/delete")
    public ResponseEntity<TransferenciaProductoDetalle.MensajeRespuesta> deleteDetalle(
            @RequestParam("trfpCod") Long trfpCod,
            @RequestParam("trfpdLinea") Integer trfpdLinea) {
        try {
            TransferenciaProductoDetalle.MensajeRespuesta respuesta = detalleService.deleteDetalle(trfpCod, trfpdLinea);
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
            logger.error("<=== Error al eliminar el detalle de transferencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al eliminar el detalle de transferencia: " + e.getMessage(), null));
        }
    }

    /**
     * Actualiza la cantidad recibida de un detalle de transferencia
     * @param requestBody Mapa con el código de transferencia, línea y cantidad recibida
     * @return ResponseEntity con la respuesta de la operación
     */
    @PatchMapping("/actualizar-cantidad-recibida")
    public ResponseEntity<TransferenciaProductoDetalle.MensajeRespuesta> actualizarCantidadRecibida(
            @RequestBody Map<String, Object> requestBody) {
        try {
            Long trfpCod = Long.valueOf(requestBody.get("trfpCod").toString());
            Integer trfpdLinea = Integer.valueOf(requestBody.get("trfpdLinea").toString());
            Integer cantidadRecibida = Integer.valueOf(requestBody.get("cantidadRecibida").toString());

            TransferenciaProductoDetalle.MensajeRespuesta respuesta = detalleService.actualizarCantidadRecibida(trfpCod, trfpdLinea, cantidadRecibida);

            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar la cantidad recibida: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al actualizar la cantidad recibida: " + e.getMessage(), null));
        }
    }
}
package py.com.nsa.api.commons.components.trx.transferenciaproducto.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.trx.transferenciaproducto.model.TransferenciaProducto;
import py.com.nsa.api.commons.components.trx.transferenciaproducto.service.TransferenciaProductoService;

@RestController
@RequestMapping("/transferencias-producto")
public class TransferenciaProductoController {

    private static final Logger logger = LoggerFactory.getLogger(TransferenciaProductoController.class);

    @Autowired
    private TransferenciaProductoService transferenciaService;

    /**
     * Obtiene todas las transferencias de productos
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/lista")
    public ResponseEntity<TransferenciaProducto.MensajeRespuesta> getTransferenciasAll() {
        try {
            TransferenciaProducto.MensajeRespuesta respuesta = transferenciaService.getTransferenciasAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener las transferencias de productos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProducto.MensajeRespuesta(500L, "Error al obtener las transferencias de productos:" + e.getMessage(), null));
        }
    }

    /**
     * Obtiene una transferencia de producto por su código
     * @param trfpCod Código de la transferencia
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/buscar")
    public ResponseEntity<TransferenciaProducto.MensajeRespuesta> getTransferenciaByTrfpCod(@RequestParam("trfpCod") Long trfpCod) {
        try {
            TransferenciaProducto.MensajeRespuesta respuesta = transferenciaService.getTransferenciaByTrfpCod(trfpCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProducto.MensajeRespuesta(500L, "Error al buscar la transferencia de producto: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene transferencias de productos filtradas
     * @param filtro Objeto TransferenciaProducto con los criterios de filtrado
     * @return ResponseEntity con la respuesta de la operación
     */
    @PostMapping("/lista-filtro")
    public ResponseEntity<TransferenciaProducto.MensajeRespuesta> getTransferenciasFiltered(@RequestBody TransferenciaProducto filtro) {
        try {
            TransferenciaProducto.MensajeRespuesta respuesta = transferenciaService.getTransferenciasFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener transferencias filtradas: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProducto.MensajeRespuesta(500L, "Error al obtener transferencias filtradas: " + e.getMessage(), null));
        }
    }

    /**
     * Inserta una nueva transferencia de producto
     * @param transferencia Transferencia de producto a insertar
     * @return ResponseEntity con la respuesta de la operación
     */
    @PostMapping("/insert")
    public ResponseEntity<TransferenciaProducto.MensajeRespuesta> insertTransferencia(@Valid @RequestBody TransferenciaProducto transferencia) {
        try {
            logger.debug("Recibiendo petición POST con transferencia de producto: {}", transferencia);

            TransferenciaProducto.MensajeRespuesta respuesta = transferenciaService.insertarTransferencia(transferencia);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (409L == respuesta.getStatus()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar la transferencia de producto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProducto.MensajeRespuesta(500L, "Error al insertar la transferencia de producto: " + e.getMessage(), null));
        }
    }

    /**
     * Actualiza una transferencia de producto existente
     * @param transferencia Transferencia de producto con datos actualizados
     * @return ResponseEntity con la respuesta de la operación
     */
    @PutMapping("/update")
    public ResponseEntity<TransferenciaProducto.MensajeRespuesta> updateTransferencia(@Valid @RequestBody TransferenciaProducto transferencia) {
        try {
            logger.info("Actualizando transferencia de producto con código: {}", transferencia.getTrfpCod());
            TransferenciaProducto.MensajeRespuesta respuesta = transferenciaService.updateTransferencia(transferencia);

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
            logger.error("<=== Error al actualizar la transferencia de producto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProducto.MensajeRespuesta(500L, "Error al actualizar la transferencia de producto: " + e.getMessage(), null));
        }
    }

    /**
     * Elimina una transferencia de producto por su código
     * @param trfpCod Código de la transferencia a eliminar
     * @return ResponseEntity con la respuesta de la operación
     */
    @DeleteMapping("/delete")
    public ResponseEntity<TransferenciaProducto.MensajeRespuesta> deleteTransferencia(@RequestParam("trfpCod") Long trfpCod) {
        try {
            TransferenciaProducto.MensajeRespuesta respuesta = transferenciaService.deleteTransferencia(trfpCod);
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
            logger.error("<=== Error al eliminar la transferencia de producto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TransferenciaProducto.MensajeRespuesta(500L, "Error al eliminar la transferencia de producto: " + e.getMessage(), null));
        }
    }
}
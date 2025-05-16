package py.com.nsa.api.commons.components.trx.movimientoproducto.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.trx.movimientoproducto.model.MovimientoProducto;
import py.com.nsa.api.commons.components.trx.movimientoproducto.service.MovimientoProductoService;

import java.util.Date;

@RestController
@RequestMapping("/movimientos-producto")
public class MovimientoProductoController {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoProductoController.class);

    @Autowired
    private MovimientoProductoService movimientoService;

    /**
     * Obtiene todos los movimientos de productos
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/lista")
    public ResponseEntity<MovimientoProducto.MensajeRespuesta> getMovimientosAll() {
        try {
            MovimientoProducto.MensajeRespuesta respuesta = movimientoService.getMovimientosAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los movimientos de productos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MovimientoProducto.MensajeRespuesta(500L, "Error al obtener los movimientos de productos:" + e.getMessage(), null));
        }
    }

    /**
     * Obtiene un movimiento de producto por su código
     * @param mpCod Código del movimiento
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/buscar")
    public ResponseEntity<MovimientoProducto.MensajeRespuesta> getMovimientoByMpCod(@RequestParam("mpCod") Long mpCod) {
        try {
            MovimientoProducto.MensajeRespuesta respuesta = movimientoService.getMovimientoByMpCod(mpCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MovimientoProducto.MensajeRespuesta(500L, "Error al buscar el movimiento de producto: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene movimientos de productos por código de producto
     * @param proCod Código del producto
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/producto/{proCod}")
    public ResponseEntity<MovimientoProducto.MensajeRespuesta> getMovimientosByProCod(@PathVariable String proCod) {
        try {
            MovimientoProducto.MensajeRespuesta respuesta = movimientoService.getMovimientosByProCod(proCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MovimientoProducto.MensajeRespuesta(500L, "Error al buscar movimientos por producto: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene movimientos de productos por código de almacén
     * @param alCod Código del almacén
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/almacen/{alCod}")
    public ResponseEntity<MovimientoProducto.MensajeRespuesta> getMovimientosByAlCod(@PathVariable Long alCod) {
        try {
            MovimientoProducto.MensajeRespuesta respuesta = movimientoService.getMovimientosByAlCod(alCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MovimientoProducto.MensajeRespuesta(500L, "Error al buscar movimientos por almacén: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene movimientos de productos por tipo de movimiento
     * @param parTipo Tipo de movimiento
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/tipo/{parTipo}")
    public ResponseEntity<MovimientoProducto.MensajeRespuesta> getMovimientosByParTipo(@PathVariable String parTipo) {
        try {
            MovimientoProducto.MensajeRespuesta respuesta = movimientoService.getMovimientosByParTipo(parTipo);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MovimientoProducto.MensajeRespuesta(500L, "Error al buscar movimientos por tipo: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene movimientos de productos filtrados por rango de fechas
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/fechas")
    public ResponseEntity<MovimientoProducto.MensajeRespuesta> getMovimientosByFechas(
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyyMMdd") Date fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyyMMdd") Date fechaFin) {
        try {
            MovimientoProducto.MensajeRespuesta respuesta = movimientoService.getMovimientosByFechas(fechaInicio, fechaFin);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MovimientoProducto.MensajeRespuesta(500L, "Error al buscar movimientos por fechas: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene movimientos de productos filtrados
     * @param filtro Objeto MovimientoProducto con los criterios de filtrado
     * @return ResponseEntity con la respuesta de la operación
     */
    @PostMapping("/lista-filtro")
    public ResponseEntity<MovimientoProducto.MensajeRespuesta> getMovimientosFiltered(@RequestBody MovimientoProducto filtro) {
        try {
            MovimientoProducto.MensajeRespuesta respuesta = movimientoService.getMovimientosFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener movimientos filtrados: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MovimientoProducto.MensajeRespuesta(500L, "Error al obtener movimientos filtrados: " + e.getMessage(), null));
        }
    }

    /**
     * Inserta un nuevo movimiento de producto
     * @param movimiento Movimiento de producto a insertar
     * @return ResponseEntity con la respuesta de la operación
     */
    @PostMapping("/insert")
    public ResponseEntity<MovimientoProducto.MensajeRespuesta> insertMovimiento(@Valid @RequestBody MovimientoProducto movimiento) {
        try {
            logger.debug("Recibiendo petición POST con movimiento de producto: {}", movimiento);

            // Cambiar "insertarMovimiento" por "insertMovimiento" para coincidir con el servicio
            MovimientoProducto.MensajeRespuesta respuesta = movimientoService.insertMovimiento(movimiento);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (409L == respuesta.getStatus()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el movimiento de producto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MovimientoProducto.MensajeRespuesta(500L, "Error al insertar el movimiento de producto: " + e.getMessage(), null));
        }
    }
    /**
     * Actualiza un movimiento de producto existente
     * @param movimiento Movimiento de producto con datos actualizados
     * @return ResponseEntity con la respuesta de la operación
     */
    @PutMapping("/update")
    public ResponseEntity<MovimientoProducto.MensajeRespuesta> updateMovimiento(@Valid @RequestBody MovimientoProducto movimiento) {
        try {
            logger.info("Actualizando movimiento de producto con código: {}", movimiento.getMpCod());
            MovimientoProducto.MensajeRespuesta respuesta = movimientoService.updateMovimiento(movimiento);

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
            logger.error("<=== Error al actualizar el movimiento de producto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MovimientoProducto.MensajeRespuesta(500L, "Error al actualizar el movimiento de producto: " + e.getMessage(), null));
        }
    }

    /**
     * Elimina un movimiento de producto por su código
     * @param mpCod Código del movimiento a eliminar
     * @return ResponseEntity con la respuesta de la operación
     */
    @DeleteMapping("/delete")
    public ResponseEntity<MovimientoProducto.MensajeRespuesta> deleteMovimiento(@RequestParam("mpCod") Long mpCod) {
        try {
            MovimientoProducto.MensajeRespuesta respuesta = movimientoService.deleteMovimiento(mpCod);
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
            logger.error("<=== Error al eliminar el movimiento de producto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MovimientoProducto.MensajeRespuesta(500L, "Error al eliminar el movimiento de producto: " + e.getMessage(), null));
        }
    }
}
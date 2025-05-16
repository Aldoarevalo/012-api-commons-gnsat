package py.com.nsa.api.commons.components.cfg.productoimpuesto.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.productoimpuesto.model.ProductoImpuesto;
import py.com.nsa.api.commons.components.cfg.productoimpuesto.service.ProductoImpuestoService;

@RestController
@RequestMapping("/producto-impuesto")
public class ProductoImpuestoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoImpuestoController.class);

    @Autowired
    private ProductoImpuestoService service;

    @GetMapping("/lista")
    public ResponseEntity<ProductoImpuesto.MensajeRespuesta> getProductoImpuestoList() {
        try {
            ProductoImpuesto.MensajeRespuesta respuesta = service.getProductoImpuestosAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener asignaciones de impuestos a productos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductoImpuesto.MensajeRespuesta(500L, "Error al obtener asignaciones de impuestos a productos: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<ProductoImpuesto.MensajeRespuesta> getProductoImpuestoFiltered(@RequestBody ProductoImpuesto filtro) {
        try {
            ProductoImpuesto.MensajeRespuesta respuesta = service.getProductoImpuestosFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener asignaciones de impuestos a productos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductoImpuesto.MensajeRespuesta(500L, "Error al obtener asignaciones de impuestos a productos: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<ProductoImpuesto.MensajeRespuesta> insertProductoImpuesto(@Valid @RequestBody ProductoImpuesto productoImpuesto) {
        try {
            logger.debug("<=== JSON: {} ===>", productoImpuesto);
            ProductoImpuesto.MensajeRespuesta respuesta = service.insert(productoImpuesto);
            logger.debug("<=== JSON RES: {} ===>", productoImpuesto);

            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (409L == respuesta.getStatus()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar asignación de impuesto a producto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductoImpuesto.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ProductoImpuesto.MensajeRespuesta> update(@Valid @RequestBody ProductoImpuesto productoImpuesto) {
        try {
            ProductoImpuesto.MensajeRespuesta respuesta = service.update(productoImpuesto);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar asignación de impuesto a producto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductoImpuesto.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ProductoImpuesto.MensajeRespuesta> delete(
            @RequestParam("proCod") String proCod,
            @RequestParam("paCod") Long paCod) {
        try {
            ProductoImpuesto.MensajeRespuesta respuesta = service.delete(proCod, paCod);
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
            logger.error("<=== Error al eliminar asignación de impuesto a producto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductoImpuesto.MensajeRespuesta(500L, "Error al eliminar asignación de impuesto a producto: " + e.getMessage(), null));
        }
    }
}
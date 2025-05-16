package py.com.nsa.api.commons.components.ref.producto.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.producto.model.Producto;
import py.com.nsa.api.commons.components.ref.producto.service.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    /**
     * Obtiene todos los productos
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/lista")
    public ResponseEntity<Producto.MensajeRespuesta> getProductosAll() {
        try {
            Producto.MensajeRespuesta respuesta = productoService.getProductosAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los productos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Producto.MensajeRespuesta(500L, "Error al obtener los productos:" + e.getMessage(), null));
        }
    }

    /**
     * Obtiene productos filtrados
     * @param filtro Objeto Producto con los criterios de filtrado
     * @return ResponseEntity con la respuesta de la operación
     */
    @PostMapping("/lista-filtro")
    public ResponseEntity<Producto.MensajeRespuesta> getProductoFiltered(@RequestBody Producto filtro) {
        try {
            Producto.MensajeRespuesta respuesta = productoService.getProductosFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener productos filtrados: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Producto.MensajeRespuesta(500L, "Error al obtener productos filtrados: " + e.getMessage(), null));
        }
    }

    /**
     * Inserta un nuevo producto
     * @param producto Producto a insertar
     * @return ResponseEntity con la respuesta de la operación
     */
    @PostMapping("/insert")
    public ResponseEntity<Producto.MensajeRespuesta> insertProducto(@Valid @RequestBody Producto producto) {
        try {
            logger.debug("Recibiendo petición POST con producto: {}", producto);

            Producto.MensajeRespuesta respuesta = productoService.insertarProducto(producto);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (409L == respuesta.getStatus()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el producto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Producto.MensajeRespuesta(500L, "Error al insertar el producto: " + e.getMessage(), null));
        }
    }

    /**
     * Actualiza un producto existente
     * @param producto Producto con datos actualizados
     * @return ResponseEntity con la respuesta de la operación
     */
    @PutMapping("/update")
    public ResponseEntity<Producto.MensajeRespuesta> updateProducto(@Valid @RequestBody Producto producto) {
        try {
            logger.info("Actualizando producto con código: {}", producto.getProCod());
            Producto.MensajeRespuesta respuesta = productoService.updateProducto(producto);

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
            logger.error("<=== Error al actualizar el producto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Producto.MensajeRespuesta(500L, "Error al actualizar el producto: " + e.getMessage(), null));
        }
    }

    /**
     * Elimina un producto por su código
     * @param proCod Código del producto a eliminar
     * @return ResponseEntity con la respuesta de la operación
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Producto.MensajeRespuesta> deleteProducto(@RequestParam("proCod") String proCod) {
        try {
            Producto.MensajeRespuesta respuesta = productoService.deleteProducto(proCod);
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
            logger.error("<=== Error al eliminar el producto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Producto.MensajeRespuesta(500L, "Error al eliminar el producto: " + e.getMessage(), null));
        }
    }
}
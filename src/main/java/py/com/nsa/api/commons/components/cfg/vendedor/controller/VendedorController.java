package py.com.nsa.api.commons.components.cfg.vendedor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.vendedor.model.Vendedor;
import py.com.nsa.api.commons.components.cfg.vendedor.service.VendedorService;

@RestController
@RequestMapping("/vendedor")
public class VendedorController {

    private static final Logger logger = LoggerFactory.getLogger(VendedorController.class);

    @Autowired
    private VendedorService vendedorService;

    @GetMapping("/lista")
    public ResponseEntity<Vendedor.MensajeRespuesta> getVendedoresAll() {
        try {
            Vendedor.MensajeRespuesta respuesta = vendedorService.getVendedoresAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener vendedores: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Vendedor.MensajeRespuesta(500L, "Error al obtener vendedores: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Vendedor.MensajeRespuesta> insertarVendedor(@RequestBody Vendedor vendedor) {
        try {
            Vendedor.MensajeRespuesta respuesta = vendedorService.insertarVendedor(vendedor);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar el vendedor: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Vendedor.MensajeRespuesta(500L, "Error al insertar el vendedor: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Vendedor.MensajeRespuesta> updateVendedor(@RequestBody Vendedor vendedor) {
        try {
            Vendedor.MensajeRespuesta respuesta = vendedorService.updateVendedor(vendedor);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar el vendedor con código {}: {} ===>", vendedor.getVendCodigo(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Vendedor.MensajeRespuesta(500L, "Error al actualizar el vendedor: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Vendedor.MensajeRespuesta> deleteVendedor(@RequestParam("vendCodigo") Long vendCodigo, @RequestParam("carCodigo") Long carCodigo) {
        try {
            Vendedor.MensajeRespuesta respuesta = vendedorService.deleteVendedor(vendCodigo, carCodigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al eliminar el vendedor con código {} y cargo {}: {} ===>", vendCodigo, carCodigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Vendedor.MensajeRespuesta(500L, "Error al eliminar el vendedor: " + e.getMessage(), null));
        }
    }
}

package py.com.nsa.api.commons.components.ref.rutadet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.rutadet.model.RutaDet;
import py.com.nsa.api.commons.components.ref.rutadet.service.RutaDetService;

@RestController
@RequestMapping("/ruta-detalle")
public class RutaDetController {

    @Autowired
    private RutaDetService rutaDetService;

    private static final Logger logger = LoggerFactory.getLogger(RutaDetController.class);


    // Listar todos los detalles
    @GetMapping("/lista")
    public ResponseEntity<RutaDetService.MensajeRespuesta> getList() {
        try {
            RutaDetService.MensajeRespuesta respuesta = rutaDetService.getList();
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al listar los detalles: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RutaDetService.MensajeRespuesta(500L, "Error al listar los detalles: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<RutaDetService.MensajeRespuesta> getDetallesFiltrados(@RequestBody RutaDet filtro) {
        try {
            RutaDetService.MensajeRespuesta respuesta = rutaDetService.getDetallesFiltrados(filtro);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al filtrar los detalles: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RutaDetService.MensajeRespuesta(500L, "Error al filtrar los detalles: " + e.getMessage(), null));
        }
    }


    // Insertar un nuevo detalle
    @PostMapping("/insert")
    public ResponseEntity<RutaDetService.MensajeRespuesta> save(@RequestBody RutaDet rutaDet) {
        try {
            RutaDetService.MensajeRespuesta respuesta = rutaDetService.save(rutaDet);
            return ResponseEntity.status(respuesta.getStatus().intValue() == 200 ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                    .body(respuesta);
        } catch (Exception e) {
            logger.error("Error al guardar el detalle: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RutaDetService.MensajeRespuesta(500L, "Error al guardar el detalle: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<RutaDetService.MensajeRespuesta> update(@RequestBody RutaDet rutaDet) {
        try {
            RutaDetService.MensajeRespuesta respuesta = rutaDetService.update(rutaDet);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar el detalle: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RutaDetService.MensajeRespuesta(500L, "Error al actualizar el detalle: " + e.getMessage(), null));
        }
    }

    @PutMapping("/updatedetalle")
    public ResponseEntity<RutaDetService.MensajeRespuesta> updatedetalle(@RequestBody RutaDet rutaDet) {
        try {
            RutaDetService.MensajeRespuesta respuesta = rutaDetService.updatedetalle(rutaDet);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar el detalle: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RutaDetService.MensajeRespuesta(500L, "Error al actualizar el detalle: " + e.getMessage(), null));
        }
    }

    // Endpoint existente para eliminar
    @DeleteMapping("/delete")
    public ResponseEntity<RutaDetService.MensajeRespuesta> deleteRutaDetalle(
            @RequestParam("rucCod") Long rucCod,
            @RequestParam("rudSecuencia") Long rudSecuencia) {
        try {
            RutaDetService.MensajeRespuesta respuesta = rutaDetService.deleteById(rucCod, rudSecuencia);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al eliminar el detalle con rucCod {} y rudSecuencia {}: {}",
                    rucCod, rudSecuencia, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RutaDetService.MensajeRespuesta(500L,
                            "Error al eliminar el detalle: " + e.getMessage(), null));
        }
    }
}
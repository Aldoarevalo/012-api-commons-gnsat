package py.com.nsa.api.commons.components.trx.viaje.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.trx.viaje.model.Viaje;
import py.com.nsa.api.commons.components.trx.viaje.service.ViajeService;

@RestController
@RequestMapping("/viaje")
public class ViajeController {

    private static final Logger logger = LoggerFactory.getLogger(ViajeController.class);

    @Autowired
    private ViajeService viajeService;

    /**
     * Lista todos los viajes
     */
    @GetMapping("/lista")
    public ResponseEntity<Viaje.MensajeRespuesta> getViajesAll() {
        try {
            Viaje.MensajeRespuesta respuesta = viajeService.getViajesAll();
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los viajes: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Viaje.MensajeRespuesta(500L, "Error al obtener los viajes: " + e.getMessage(), null));
        }
    }

    /**
     * Busca un viaje por su código
     */
    @GetMapping("/buscar")
    public ResponseEntity<Viaje.MensajeRespuesta> getViajeByVCod(@RequestParam("vCod") Integer vCod) {
        try {
            Viaje.MensajeRespuesta respuesta = viajeService.getViajeByVCod(vCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al buscar el viaje: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Viaje.MensajeRespuesta(500L, "Error al buscar el viaje: " + e.getMessage(), null));
        }
    }

    /**
     * Lista viajes filtrados por criterios
     */
    @PostMapping("/lista-filtro")
    public ResponseEntity<Viaje.MensajeRespuesta> getViajesFiltered(@RequestBody Viaje filtro) {
        try {
            Viaje.MensajeRespuesta respuesta = viajeService.getViajesFiltered(filtro);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener viajes filtrados: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Viaje.MensajeRespuesta(500L, "Error al obtener viajes filtrados: " + e.getMessage(), null));
        }
    }

    /**
     * Inserta un nuevo viaje
     */
    @PostMapping("/insert")
    public ResponseEntity<Viaje.MensajeRespuesta> insertViaje(@Valid @RequestBody Viaje viaje) {
        try {
            logger.debug("Recibiendo petición POST con viaje: {}", viaje);
            Viaje.MensajeRespuesta respuesta = viajeService.insertarViaje(viaje);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el viaje: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Viaje.MensajeRespuesta(500L, "Error al insertar el viaje: " + e.getMessage(), null));
        }
    }

    /**
     * Actualiza un viaje existente
     */
    @PutMapping("/update")
    public ResponseEntity<Viaje.MensajeRespuesta> updateViaje(@Valid @RequestBody Viaje viaje) {
        try {
            logger.info("Actualizando viaje con código: {}", viaje.getVCod());
            Viaje.MensajeRespuesta respuesta = viajeService.updateViaje(viaje);
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
            logger.error("<=== Error al actualizar el viaje: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Viaje.MensajeRespuesta(500L, "Error al actualizar el viaje: " + e.getMessage(), null));
        }
    }


    @PostMapping("/insert-completo")
    public ResponseEntity<Viaje.MensajeRespuesta> insertViajeCompleto(@Valid @RequestBody Viaje viaje) {
        try {
            logger.debug("Recibiendo petición POST con viaje: {}", viaje);
            Viaje.MensajeRespuesta respuesta = viajeService.insertViajeCompleto(viaje);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el viaje: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Viaje.MensajeRespuesta(500L, "Error al insertar el viaje: " + e.getMessage(), null));
        }
    }

    /**
     * Actualiza un viaje existente
     */
    @PutMapping("/update-completo")
    public ResponseEntity<Viaje.MensajeRespuesta> updateViajeCompleto(@Valid @RequestBody Viaje viaje) {
        try {
            logger.info("Actualizando viaje con código: {}", viaje.getVCod());
            Viaje.MensajeRespuesta respuesta = viajeService.updateViajeCompleto(viaje);
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
            logger.error("<=== Error al actualizar el viaje: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Viaje.MensajeRespuesta(500L, "Error al actualizar el viaje: " + e.getMessage(), null));
        }
    }

    /**
     * Elimina un viaje por su código
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Viaje.MensajeRespuesta> deleteViaje(@RequestParam("vCod") Integer vCod) {
        try {
            Viaje.MensajeRespuesta respuesta = viajeService.deleteViaje(vCod);
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
            logger.error("<=== Error al eliminar el viaje: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Viaje.MensajeRespuesta(500L, "Error al eliminar el viaje: " + e.getMessage(), null));
        }
    }
}
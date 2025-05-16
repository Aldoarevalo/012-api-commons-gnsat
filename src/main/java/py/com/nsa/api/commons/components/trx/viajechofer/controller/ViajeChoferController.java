package py.com.nsa.api.commons.components.trx.viajechofer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.trx.viajechofer.model.ViajeChofer;
import py.com.nsa.api.commons.components.trx.viajechofer.service.ViajeChoferService;

@RestController
@RequestMapping("/viaje-chofer")
public class ViajeChoferController {

    private static final Logger logger = LoggerFactory.getLogger(ViajeChoferController.class);
    @Autowired
    private ViajeChoferService viajeChoferService;

    // Listar todas las asignaciones
    @GetMapping("/lista")
    public ResponseEntity<ViajeChofer.MensajeRespuesta> listarTodasLasAsignaciones() {
        ViajeChofer.MensajeRespuesta respuesta = viajeChoferService.listarTodasLasAsignaciones();
        if (respuesta.getStatus() == 200L) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
        }
    }

    // Listar asignaciones por viaje
    @GetMapping("/lista-por-viaje")
    public ResponseEntity<ViajeChofer.MensajeRespuesta> listarPorViaje(@RequestParam("vCod") Integer vCod) {
        ViajeChofer.MensajeRespuesta respuesta = viajeChoferService.listarPorViaje(vCod);
        if (respuesta.getStatus() == 200L) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
        }
    }

    // Listar asignaciones por chofer
    @GetMapping("/lista-por-chofer")
    public ResponseEntity<ViajeChofer.MensajeRespuesta> listarPorChofer(@RequestParam("eCod") Long eCod) {
        ViajeChofer.MensajeRespuesta respuesta = viajeChoferService.listarPorChofer(eCod);
        if (respuesta.getStatus() == 200L) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<ViajeChofer.MensajeRespuesta> getViajeChoferesFiltered(@RequestBody ViajeChofer filtro) {
        try {
            ViajeChofer.MensajeRespuesta respuesta = viajeChoferService.getViajeChoferesFiltered(filtro);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener asignaciones filtradas: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ViajeChofer.MensajeRespuesta(500L, "Error al obtener asignaciones filtradas: " + e.getMessage(), null));
        }
    }

    // Insertar una nueva asignación
    @PostMapping("/insert")
    public ResponseEntity<ViajeChofer.MensajeRespuesta> insertarViajeChofer(@RequestBody ViajeChofer viajeChofer) {
        ViajeChofer.MensajeRespuesta respuesta = viajeChoferService.insertarViajeChofer(viajeChofer.getVCod(), viajeChofer.getECod());
        if (respuesta.getStatus() == 200L) {
            return ResponseEntity.ok(respuesta);
        } else if (respuesta.getStatus() == 409L) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    @PostMapping("/insert-choferes")
    public ResponseEntity<ViajeChofer.MensajeRespuesta> insertarViajeChoferes(@RequestBody ViajeChofer viajeChofer) {
        ViajeChofer.MensajeRespuesta respuesta = viajeChoferService.insertarViajeChofer(viajeChofer.getVCod(), viajeChofer.getECod());
        if (respuesta.getStatus() == 200L) {
            return ResponseEntity.ok(respuesta);
        } else if (respuesta.getStatus() == 409L) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    // Eliminar una asignación
    @DeleteMapping("/delete")
    public ResponseEntity<ViajeChofer.MensajeRespuesta> eliminarAsignacion(
            @RequestParam("vCod") Integer vCod,
            @RequestParam("eCod") Long eCod) {
        ViajeChofer.MensajeRespuesta respuesta = viajeChoferService.eliminarAsignacion(vCod, eCod);
        if (respuesta.getStatus() == 200L) {
            return ResponseEntity.ok(respuesta);
        } else if (respuesta.getStatus() == 404L) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }
}
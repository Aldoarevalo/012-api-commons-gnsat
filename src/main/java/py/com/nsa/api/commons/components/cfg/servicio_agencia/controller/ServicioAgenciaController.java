package py.com.nsa.api.commons.components.cfg.servicio_agencia.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.servicio_agencia.model.ServicioAgencia;
import py.com.nsa.api.commons.components.cfg.servicio_agencia.service.ServicioAgenciaService;


@RestController
@RequestMapping("serv-agencia")
public class ServicioAgenciaController {
    @Autowired
    private ServicioAgenciaService service;

    private static final Logger logger = LoggerFactory.getLogger(ServicioAgenciaController.class);

    @GetMapping("/lista")
    public ResponseEntity<ServicioAgencia.MensajeRespuesta> getAgenciaList() {
        try {
            ServicioAgencia.MensajeRespuesta respuesta = service.obtenerTodos();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener tipos de IVA: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ServicioAgencia.MensajeRespuesta(500L, "Error al obtener agencias: " + e.getMessage(), null));
        }
    }
    @PostMapping("/lista-filtro")
    public ResponseEntity<ServicioAgencia.MensajeRespuesta> getAgenciaFiltered(@RequestBody ServicioAgencia filtro) {
        try {
            ServicioAgencia.MensajeRespuesta respuesta = service.getAgenciasFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener servicio agencias: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ServicioAgencia.MensajeRespuesta(500L, "Error al obtener servicio agencias: " + e.getMessage(), null));
        }
    }
    @GetMapping("/byAgencia/{agCod}")
    public ResponseEntity<ServicioAgencia.MensajeRespuesta> getAgenciaByAgCod(@PathVariable Long agCod) {
        try {
            ServicioAgencia.MensajeRespuesta respuesta = service.obtenerPorAgCod(agCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener servicio agencia por agCod: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ServicioAgencia.MensajeRespuesta(500L, "Error al obtener servicio agencia: " + e.getMessage(), null));
        }
    }


    @PostMapping("/insert")
    public ResponseEntity<ServicioAgencia.MensajeRespuesta> insertAgencia(@Valid @RequestBody ServicioAgencia agencia) {
        try {
            ServicioAgencia.MensajeRespuesta respuesta = service.insert(agencia);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar servicio agencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ServicioAgencia.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }
    @PutMapping("/update")
    public ResponseEntity<ServicioAgencia.MensajeRespuesta> updateServicioAgencia(@Valid @RequestBody ServicioAgencia agencia) {
        try {
            ServicioAgencia.MensajeRespuesta respuesta = service.update(agencia);
            if (200L ==  respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar servicio agencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ServicioAgencia.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ServicioAgencia.MensajeRespuesta> deleteSerAgencia(
            @RequestParam("parServicio") String parServicio,
            @RequestParam("agCod") long agCod) {
        try {
            ServicioAgencia.MensajeRespuesta respuesta = service.delete(parServicio,agCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el servicio con código {}: {} ===>", parServicio, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ServicioAgencia.MensajeRespuesta(500L, "Error al eliminar el servicio: " + e.getMessage(), null));
        }
    }

}

package py.com.nsa.api.commons.components.ref.empleado.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.empleado.model.Empleado;
import py.com.nsa.api.commons.components.ref.empleado.service.EmpleadoService;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/lista")
    public ResponseEntity<Empleado.MensajeRespuesta> getEmpleadosAll() {
        try {
            Empleado.MensajeRespuesta respuesta = empleadoService.getEmpleadosAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener empleados: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Empleado.MensajeRespuesta(500L, "Error al obtener empleados: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<Empleado.MensajeRespuesta> getEmpleadosFiltered(@RequestBody Empleado filtro) {
        try {
            Empleado.MensajeRespuesta respuesta = empleadoService.getEmpleadosFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener empleados filtrados: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Empleado.MensajeRespuesta(500L, "Error al obtener empleados filtrados: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Empleado.MensajeRespuesta> insertarEmpleado(@Valid @RequestBody Empleado empleado) {
        try {
            Empleado.MensajeRespuesta respuesta = empleadoService.insertarEmpleado(empleado);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar el empleado: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Empleado.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Empleado.MensajeRespuesta> updateEmpleado(@Valid @RequestBody Empleado empleado) {
        try {
            Empleado.MensajeRespuesta respuesta = empleadoService.updateEmpleado(empleado);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar el empleado con código {}: {} ===>", empleado.getECod(),
                    e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Empleado.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Empleado.MensajeRespuesta> deleteEmpleado(@RequestParam("eCod") Long eCod) {
        try {
            Empleado.MensajeRespuesta respuesta = empleadoService.deleteEmpleado(eCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar empleado con código {}: {} ===>", eCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Empleado.MensajeRespuesta(500L, "Error al eliminar empleado: " + e.getMessage(), null));
        }
    }

}

package py.com.nsa.api.commons.components.ref.vehiculo.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.vehiculo.model.Vehiculo;
import py.com.nsa.api.commons.components.ref.vehiculo.service.VehiculoService;

@RestController
@RequestMapping("vehiculo")
public class VehiculoController {

    @Autowired
    private VehiculoService service;

    private static final Logger logger = LoggerFactory.getLogger(VehiculoController.class);

    @GetMapping("/lista")
    public ResponseEntity<Vehiculo.MensajeRespuesta> getList() {
        try {
            Vehiculo.MensajeRespuesta respuesta = service.getList();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener la lista de vehículos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Vehiculo.MensajeRespuesta(500L, "Error al obtener la lista de vehículos: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Vehiculo.MensajeRespuesta> insert(@Valid @RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo.MensajeRespuesta respuesta = service.save(vehiculo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar vehículo: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Vehiculo.MensajeRespuesta(500L, "Error al insertar vehículo: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insertcabeceradetalle")
    public ResponseEntity<Vehiculo.MensajeRespuesta> insertcabeceradetalle(@Valid @RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo.MensajeRespuesta respuesta = service.guardar(vehiculo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar vehículo: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Vehiculo.MensajeRespuesta(500L, "Error al insertar vehículo: " + e.getMessage(), null));
        }
    }

    @PutMapping("/updatecabeceradetalle")
    public ResponseEntity<Vehiculo.MensajeRespuesta> updatecabeceradetalle(@Valid @RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo.MensajeRespuesta respuesta = service.actualizar(vehiculo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar vehículo: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Vehiculo.MensajeRespuesta(500L, "Error al actualizar vehículo: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Vehiculo.MensajeRespuesta> update(@Valid @RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo.MensajeRespuesta respuesta = service.update(vehiculo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar vehículo: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Vehiculo.MensajeRespuesta(500L, "Error al actualizar vehículo: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Vehiculo.MensajeRespuesta> deleteVehiculo(@RequestParam("codigo") Long codigo) {
        try {
            Vehiculo.MensajeRespuesta respuesta = service.deleteById(codigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al eliminar el vehículo con ID {}: {} ===>", codigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Vehiculo.MensajeRespuesta(500L, "Error al eliminar el vehículo: " + e.getMessage(), null));
        }
    }
}
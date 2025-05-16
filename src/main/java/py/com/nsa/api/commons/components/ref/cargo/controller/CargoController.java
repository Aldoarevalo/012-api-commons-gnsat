package py.com.nsa.api.commons.components.ref.cargo.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.cargo.model.Cargo;
import py.com.nsa.api.commons.components.ref.cargo.service.CargoService;

@RestController
@RequestMapping("/cargo")
public class CargoController {
    private static final Logger logger = LoggerFactory.getLogger(CargoController.class);
    @Autowired
    private CargoService cargoService;

    @GetMapping("/lista")
    public ResponseEntity<Cargo.MensajeRespuesta> getCargosAll(
            @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            Cargo.MensajeRespuesta respuesta = cargoService.getCargosAll(keyword);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }

        } catch (Exception e) {
            logger.error("<=== Error al obtener los cargos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cargo.MensajeRespuesta(500L, "Error al obtener cargos: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Cargo.MensajeRespuesta> insertarCargo(
            @Valid @RequestBody Cargo cargo) {
        try {
            Cargo.MensajeRespuesta respuesta = cargoService.insertarCargo(cargo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el cargo: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cargo.MensajeRespuesta(500L, "Error al insertar el cargo: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Cargo.MensajeRespuesta> updateCargo(
            @Valid @RequestBody Cargo cargo) {
        try {
            Cargo.MensajeRespuesta respuesta = cargoService.updateCargo(cargo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el cargo con ID {}: {} ===>", cargo.getCarCodigo(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cargo.MensajeRespuesta(500L, "Error al actualizar el cargo: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Cargo.MensajeRespuesta> deleteCargo(
            @RequestParam("carCodigo") Long carCodigo) {
        try {
            Cargo.MensajeRespuesta respuesta = cargoService.deleteCargo(carCodigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el cargo con código {}: {} ===>", carCodigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cargo.MensajeRespuesta(500L, "Error al eliminar el cargo: " + e.getMessage(), null));
        }
    }
}

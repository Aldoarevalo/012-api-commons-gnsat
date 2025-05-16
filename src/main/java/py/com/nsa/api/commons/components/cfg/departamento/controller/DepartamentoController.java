package py.com.nsa.api.commons.components.cfg.departamento.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.departamento.model.Departamento;
import py.com.nsa.api.commons.components.cfg.departamento.service.DepartamentoService;

@RestController
@RequestMapping("/departamento")
public class DepartamentoController {
    private static final Logger logger = LoggerFactory.getLogger(DepartamentoController.class);

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping("/lista")
    public ResponseEntity<Departamento.MensajeRespuesta> getDepartamentosAll() {
        try {
            Departamento.MensajeRespuesta respuesta = departamentoService.getDepartamentosAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }

        } catch (Exception e) {
            logger.error("<=== Error al obtener los departamentos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Departamento.MensajeRespuesta(500L, "Error al obtener departamentos: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Departamento.MensajeRespuesta> insertarDepartamento(
            @Valid @RequestBody Departamento departamento) {
        try {
            Departamento.MensajeRespuesta respuesta = departamentoService.insertarDepartamento(departamento);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el departamento: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Departamento.MensajeRespuesta(500L, "Error al insertar el departamento: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Departamento.MensajeRespuesta> updateDepartamento(
            @Valid @RequestBody Departamento departamento) {
        try {
            Departamento.MensajeRespuesta respuesta = departamentoService.updateDepartamento(departamento);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el departamento con ID {}: {} ===>", departamento.getDpCod(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Departamento.MensajeRespuesta(500L, "Error al actualizar el departamento: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Departamento.MensajeRespuesta> deleteDepartamento(
            @RequestParam("dpCod") Long dpCod) {
        try {
            Departamento.MensajeRespuesta respuesta = departamentoService.deleteDepartamento(dpCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el departamento con código {}: {} ===>", dpCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Departamento.MensajeRespuesta(500L, "Error al eliminar el departamento: " + e.getMessage(), null));
        }
    }
}

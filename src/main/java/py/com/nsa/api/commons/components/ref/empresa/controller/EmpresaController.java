package py.com.nsa.api.commons.components.ref.empresa.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.empresa.model.Empresa;
import py.com.nsa.api.commons.components.ref.empresa.service.EmpresaService;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
    private static final Logger logger = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/lista")
    public ResponseEntity<Empresa.MensajeRespuesta> getEmpresasAll() {
        try {
            Empresa.MensajeRespuesta respuesta = empresaService.getEmpresasAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener las empresas: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Empresa.MensajeRespuesta(500L, "Error al obtener empresas: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<Empresa.MensajeRespuesta> getEmpresasFiltered(@RequestBody Empresa filtro) {
        try {
            Empresa.MensajeRespuesta respuesta = empresaService.getEmpresasFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener empresas filtradas: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Empresa.MensajeRespuesta(500L,
                            "Error al obtener empresas filtradas: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Empresa.MensajeRespuesta> insertarEmpresa(
            @Valid @RequestBody Empresa empresa) {
        try {
            Empresa.MensajeRespuesta respuesta = empresaService.insertarEmpresa(empresa);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar la empresa: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Empresa.MensajeRespuesta(500L, "Error al insertar la empresa: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Empresa.MensajeRespuesta> updateEmpresa(
            @Valid @RequestBody Empresa empresa) {
        try {
            Empresa.MensajeRespuesta respuesta = empresaService.updateEmpresa(empresa);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar la empresa con ID {}: {} ===>", empresa.getEmCod(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Empresa.MensajeRespuesta(500L, "Error al actualizar la empresa: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Empresa.MensajeRespuesta> deleteEmpresa(
            @RequestParam("emCod") Long emCod) {
        try {
            Empresa.MensajeRespuesta respuesta = empresaService.deleteEmpresa(emCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar la empresa con código {}: {} ===>", emCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Empresa.MensajeRespuesta(500L, "Error al eliminar la empresa: " + e.getMessage(), null));
        }
    }
}
package py.com.nsa.api.commons.components.cfg.cajaagencia.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.cajaagencia.model.CajaAgencia;
import py.com.nsa.api.commons.components.cfg.cajaagencia.model.CajaAgenciaDTO;
import py.com.nsa.api.commons.components.cfg.cajaagencia.model.request.UpdateOperacionRequest;
import py.com.nsa.api.commons.components.cfg.cajaagencia.service.CajaAgenciaService;

import java.util.List;

@RestController
@RequestMapping("cajaagencia")
public class CajaAgenciaController {

    @Autowired
    private CajaAgenciaService service;

    private static final Logger logger = LoggerFactory.getLogger(CajaAgenciaController.class);

    @GetMapping("/lista")
    public ResponseEntity<CajaAgencia.MensajeRespuesta> getCajaAgenciaList() {
        try {
            CajaAgencia.MensajeRespuesta respuesta = service.getAllCajas();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener cajas de agencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CajaAgencia.MensajeRespuesta(500L, "Error al obtener cajas de agencia: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<CajaAgencia.MensajeRespuesta> insertCajaAgencia(@Valid @RequestBody CajaAgencia caja) {
        try {
            CajaAgencia.MensajeRespuesta respuesta = service.insert(caja);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar caja de agencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CajaAgencia.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CajaAgencia.MensajeRespuesta> updateCajaAgencia(@Valid @RequestBody CajaAgencia caja) {
        try {
            CajaAgencia.MensajeRespuesta respuesta = service.update(caja);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar caja de agencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CajaAgencia.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<Boolean> deleteCajaAgencia(@PathVariable("codigo") Long codigo) {
        boolean result = service.deleteById(codigo);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/cajas-agencias")
    public ResponseEntity<List<CajaAgenciaDTO>> getCajasAgencias(
            @RequestParam(required = false) Long paisCod,
            @RequestParam(required = false) Long ciudadCod,
            @RequestParam(required = false) String tipoAgencia,
            @RequestParam(required = false) Long agenciaCod,
            @RequestParam(required = false) Long usuarioCod) {

        List<CajaAgenciaDTO> result = service.buscarCajasAgenciasPorFiltros(
                paisCod, ciudadCod, tipoAgencia, agenciaCod, usuarioCod);

        return ResponseEntity.ok(result);
    }


    @GetMapping("/agencia-cajas")
    public ResponseEntity<List<CajaAgenciaDTO>> getAgenciaCajas(
            @RequestParam(required = false) Long agenciaCod,
            @RequestParam(required = false) Long usuarioCod) {
        System.out.println("holaaaaaaaaaaa1"+agenciaCod+usuarioCod); // This should be a logger.info

        // You might want to validate the parameters since they're marked as not required
        if (agenciaCod == null || usuarioCod == null) {
            return ResponseEntity.badRequest().build();
        }

        List<CajaAgenciaDTO> result = service.buscarCajasAgenciasPorUsuarioyAgencia(
                 agenciaCod, usuarioCod);

        return ResponseEntity.ok(result);
    }


    @PutMapping("/operacion")
    public ResponseEntity<CajaAgencia.MensajeRespuesta> updateOperacion(@Valid @RequestBody UpdateOperacionRequest request) {
        try {
            CajaAgencia.MensajeRespuesta respuesta = service.updateOperacionCajas(request);
            
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (404L == respuesta.getStatus()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar operación de cajas: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CajaAgencia.MensajeRespuesta(500L, 
                        "Error al actualizar operación de cajas: " + e.getMessage(), null));
        }
    }
}


package py.com.nsa.api.commons.components.cfg.agencia.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.agencia.dto.AdvancedFilterRequest;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.agencia.service.AgenciaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("agencia")
public class AgenciaController {
    @Autowired
    private AgenciaService service;

    private static final Logger logger = LoggerFactory.getLogger(AgenciaController.class);

    @GetMapping("/lista")
    public ResponseEntity<Agencia.MensajeRespuesta> getAgenciaList() {
        try {
            Agencia.MensajeRespuesta respuesta = service.getAgenciasAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener tipos de IVA: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Agencia.MensajeRespuesta(500L, "Error al obtener agencias: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<Agencia.MensajeRespuesta> getAgenciaFiltered(@RequestBody Agencia filtro) {
        try {
            Agencia.MensajeRespuesta respuesta = service.getAgenciasFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener agencias: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Agencia.MensajeRespuesta(500L, "Error al obtener agencias: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Agencia.MensajeRespuesta> insertAgencia(@Valid @RequestBody Agencia agencia) {
        try {
            logger.error("<=== JSON: {} ===>", agencia);
            Agencia.MensajeRespuesta respuesta = service.insert(agencia);
            logger.error("<=== JSON RES: {} ===>", agencia);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar agencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Agencia.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Agencia.MensajeRespuesta> update(@Valid @RequestBody Agencia agencia) {
        try {
            Agencia.MensajeRespuesta respuesta = service.update(agencia);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar agencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Agencia.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @GetMapping("/pais")
    public ResponseEntity<List<Map<String, Object>>> getDistinctAgenciasPais() {
        logger.info("Solicitud recibida para obtener agencias distintas.");

        List<Map<String, Object>> agencias = service.getDistinctAgenciasPais();

        logger.info("Enviando respuesta con {} registros.", agencias.size());
        return ResponseEntity.ok(agencias);
    }

    @GetMapping("/ciudad")
    public ResponseEntity<List<Map<String, Object>>> getDistinctCiudades(@RequestParam Long paCod) {
        logger.info("Solicitud recibida para obtener ciudades del país con código: {}", paCod);

        List<Map<String, Object>> ciudades = service.getDistinctCiudades(paCod);

        logger.info("Enviando respuesta con {} registros.", ciudades.size());
        return ResponseEntity.ok(ciudades);
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<Map<String, Object>>> getDistinctTipoAgencias() {
        logger.info("Solicitud recibida para obtener tipos de agencia distintos.");

        List<Map<String, Object>> tipoAgencias = service.getDistinctTipoAgencias();

        logger.info("Enviando respuesta con {} registros.", tipoAgencias.size());
        return ResponseEntity.ok(tipoAgencias);
    }

    /**
     * Realiza un filtrado avanzado basado en grupos de condiciones.
     *
     * @param filterRequest Estructura de filtros avanzados
     * @return Lista de agencias filtradas
     */
    @PostMapping("/filtrado-avanzado")
    public ResponseEntity<Agencia.MensajeRespuesta> filtrarAvanzado(@RequestBody AdvancedFilterRequest filterRequest) {
        try {
            List<Agencia> agencias = service.applyAdvancedFilters(filterRequest);

            if (agencias.isEmpty()) {
                return ResponseEntity.ok(new Agencia.MensajeRespuesta(204L, "No se encontraron agencias con los filtros aplicados.", null));
            }

            return ResponseEntity.ok(new Agencia.MensajeRespuesta(200L, "Agencias encontradas correctamente.", agencias));
        } catch (Exception e) {
            logger.error("Error al aplicar filtros avanzados: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Agencia.MensajeRespuesta(500L, "Error al aplicar filtros avanzados: " + e.getMessage(), null));
        }
    }

}

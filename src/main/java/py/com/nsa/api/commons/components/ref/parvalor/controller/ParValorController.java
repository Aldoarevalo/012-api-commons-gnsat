package py.com.nsa.api.commons.components.ref.parvalor.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.parametro.model.Parametro;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import py.com.nsa.api.commons.components.ref.parvalor.service.ValorService;

import java.util.*;

@RestController
@RequestMapping("/parvalor")
public class ParValorController {

    private static final Logger logger = LoggerFactory.getLogger(ParValorController.class);

    @Autowired
    private ValorService valorService;

    @GetMapping("/lista")
    public ResponseEntity<ParValor.MensajeRespuesta> getValoresAll() {
        try {
            ParValor.MensajeRespuesta respuesta = valorService.getValoresAll();
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            }  else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener valores: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ParValor.MensajeRespuesta(500L, "Error al obtener valores: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<ParValor.MensajeRespuesta> getValoresFiltered(@RequestBody Object requestBody) {
        try {
            List<Map<String, Object>> filtros;

            // Verificar si el cuerpo de la solicitud es una lista o un objeto individual
            if (requestBody instanceof List) {
                filtros = (List<Map<String, Object>>) requestBody;
            } else {
                Map<String, Object> singleFiltro = (Map<String, Object>) requestBody;
                filtros = Collections.singletonList(singleFiltro);
            }

            // Procesar todos los filtros y combinar los resultados
            List<ParValor> combinedResults = new ArrayList<>();
            for (Map<String, Object> filtro : filtros) {
                // Crear un objeto ParValor a partir del Map
                ParValor parValor = new ParValor();

                // Asignar campos de ParValor solo si no son null
                if (filtro.containsKey("parValor") && filtro.get("parValor") != null) {
                    parValor.setParValor((String) filtro.get("parValor"));
                }
                if (filtro.containsKey("parDescripcion") && filtro.get("parDescripcion") != null) {
                    parValor.setParDescripcion((String) filtro.get("parDescripcion"));
                }
                if (filtro.containsKey("parComentario") && filtro.get("parComentario") != null) {
                    parValor.setParComentario((String) filtro.get("parComentario"));
                }

                // Manejar el objeto "parametro" de forma segura
                Map<String, Object> parametroMap = (Map<String, Object>) filtro.get("parametro");
                if (parametroMap != null) {
                    Parametro parametro = new Parametro();
                    // Solo asignar pmCod si existe y no es null
                    if (parametroMap.containsKey("pmCod") && parametroMap.get("pmCod") != null) {
                        parametro.setPmCod(Long.valueOf(parametroMap.get("pmCod") instanceof Integer ? (Integer) parametroMap.get("pmCod") : null));
                    }
                    // Solo asignar pmNombre si existe y no es null
                    if (parametroMap.containsKey("pmNombre") && parametroMap.get("pmNombre") != null) {
                        parametro.setPmNombre((String) parametroMap.get("pmNombre"));
                    }
                    // Asignar el objeto parametro solo si tiene datos válidos
                    if (parametro.getPmCod() != null || parametro.getPmNombre() != null) {
                        parValor.setParametro(parametro);
                    }
                }

                // Obtener los valores filtrados
                ParValor.MensajeRespuesta respuesta = valorService.getValoresFiltered(parValor);
                if (respuesta.getStatus() == 200L && respuesta.getValores() != null) {
                    combinedResults.addAll(respuesta.getValores());
                }
            }

            if (!combinedResults.isEmpty()) {
                return ResponseEntity.ok(new ParValor.MensajeRespuesta(200L, "Valores encontrados", combinedResults));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ParValor.MensajeRespuesta(204L, "No se encontraron valores", null));
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener valores filtrados: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ParValor.MensajeRespuesta(500L, "Error al obtener valores filtrados: " + e.getMessage(), null));
        }
    }
    @PostMapping("/insert")
    public ResponseEntity<ParValor.MensajeRespuesta> insertarValor(@Valid @RequestBody ParValor valor) {
        try {
            ParValor.MensajeRespuesta respuesta = valorService.insertarValor(valor);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            }  else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al insertar el valor: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ParValor.MensajeRespuesta(500L, "Error al insertar el valor: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ParValor.MensajeRespuesta> updateValor(@Valid @RequestBody ParValor valor) {
        try {
            ParValor.MensajeRespuesta respuesta = valorService.updateValor(valor);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            }  else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al actualizar el valor: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ParValor.MensajeRespuesta(500L, "Error al actualizar el valor: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ParValor.MensajeRespuesta> deleteValor(@RequestParam("parValor") String parValor) {
        try {
            ParValor.MensajeRespuesta respuesta = valorService.deleteValor(parValor);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 204L) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el valor con PAR_VALOR {}: {}", parValor, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ParValor.MensajeRespuesta(500L, "Error al eliminar el valor: " + e.getMessage(), null));
        }
    }
    // Nuevo método para obtener los valores por descripción
    @GetMapping("/buscarPorDescripcion")
    public ResponseEntity<Map<String, Object>> getByDescripcion(@RequestParam("pmNombre") String pmNombre) {
        try {
            Map<String, Object> response = valorService.getByDescripcion(pmNombre);
            int status = (int) response.get("status");

            if (status == 200) {
                return ResponseEntity.ok(response);
            } else if (status == 204) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los datos por descripción {}: {} ===>", pmNombre, e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("mensaje", "Error al obtener los datos del valor: " + e.getMessage());
            errorResponse.put("detalles", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
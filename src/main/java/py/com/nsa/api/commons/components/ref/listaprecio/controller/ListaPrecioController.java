package py.com.nsa.api.commons.components.ref.listaprecio.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.listaprecio.model.ListaPrecio;

import py.com.nsa.api.commons.components.ref.listaprecio.dto.ListaPrecioAdvancedFilterRequest;
import py.com.nsa.api.commons.components.ref.listaprecio.service.ListaPrecioService;
import java.util.List;

@RestController
@RequestMapping("/listaprecio")
public class ListaPrecioController {

    private static final Logger logger = LoggerFactory.getLogger(ListaPrecioController.class);

    @Autowired
    private ListaPrecioService listaPrecioService;

    @GetMapping("/lista")
    public ResponseEntity<ListaPrecio.MensajeRespuesta> getListaPreciosAll() {
        try {
            ListaPrecio.MensajeRespuesta respuesta = listaPrecioService.getListaPreciosAll();
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al obtener listas de precios: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ListaPrecio.MensajeRespuesta(500L, "Error al obtener listas de precios: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<ListaPrecio.MensajeRespuesta> insertarListaPrecio(@Valid @RequestBody ListaPrecio listaPrecio) {
        try {
            ListaPrecio.MensajeRespuesta respuesta = listaPrecioService.insertarListaPrecio(listaPrecio);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 409L) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al insertar la lista de precios: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ListaPrecio.MensajeRespuesta(500L, "Error al insertar la lista de precios: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ListaPrecio.MensajeRespuesta> updateListaPrecio(@Valid @RequestBody ListaPrecio listaPrecio) {
        try {
            ListaPrecio.MensajeRespuesta respuesta = listaPrecioService.updateListaPrecio(listaPrecio);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else if (respuesta.getStatus() == 409L) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar la lista de precios: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ListaPrecio.MensajeRespuesta(500L, "Error al actualizar la lista de precios: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ListaPrecio.MensajeRespuesta> deleteListaPrecio(@RequestParam("lpCod") Long lpCod) {
        try {
            ListaPrecio.MensajeRespuesta respuesta = listaPrecioService.deleteListaPrecio(lpCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al eliminar la lista de precios: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ListaPrecio.MensajeRespuesta(500L, "Error al eliminar la lista de precios: " + e.getMessage(), null));
        }
    }

    /**
     * Realiza un filtrado avanzado basado en grupos de condiciones.
     *
     * @param filterRequest Estructura de filtros avanzados
     * @return Lista de listas de precios filtradas
     */
    @PostMapping("/filtrado-avanzado")
    public ResponseEntity<ListaPrecio.MensajeRespuesta> filtrarAvanzado(@RequestBody ListaPrecioAdvancedFilterRequest filterRequest) {
        try {
            List<ListaPrecio> listaPrecios = listaPrecioService.applyAdvancedFilters(filterRequest);

            if (listaPrecios.isEmpty()) {
                return ResponseEntity.ok(new ListaPrecio.MensajeRespuesta(204L, "No se encontraron listas de precios con los filtros aplicados.", null));
            }

            return ResponseEntity.ok(new ListaPrecio.MensajeRespuesta(200L, "Listas de precios encontradas correctamente.", listaPrecios));
        } catch (Exception e) {
            logger.error("Error al aplicar filtros avanzados: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ListaPrecio.MensajeRespuesta(500L, "Error al aplicar filtros avanzados: " + e.getMessage(), null));
        }
    }
}
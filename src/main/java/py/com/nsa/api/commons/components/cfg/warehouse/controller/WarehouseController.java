package py.com.nsa.api.commons.components.cfg.warehouse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.warehouse.model.Warehouse;
import py.com.nsa.api.commons.components.cfg.warehouse.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/filter")
    public ResponseEntity<Warehouse.MensajeRespuesta> getWarehouseByFilters(
            @RequestParam(required = false) String wmsWhseid,
            @RequestParam(required = false) String wmsAmbiente) {
        try {
            Warehouse.MensajeRespuesta respuesta = warehouseService.getWarehouseByFilters(wmsWhseid, wmsAmbiente);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener almacenes: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                    new Warehouse.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
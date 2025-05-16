package py.com.nsa.api.commons.components.cfg.menu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.menu.model.Menu;
import py.com.nsa.api.commons.components.cfg.menu.service.MenuService;

@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuService service;

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @GetMapping("/lista")
    public ResponseEntity<Menu.MensajeRespuesta> getList() {
        try {
            Menu.MensajeRespuesta respuesta = service.getMenuAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener permisos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Menu.MensajeRespuesta(500L, "Error al obtener permisos: " + e.getMessage(), null));
        }
    }

}

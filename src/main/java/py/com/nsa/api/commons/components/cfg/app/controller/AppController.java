package py.com.nsa.api.commons.components.cfg.app.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.app.model.App;
import py.com.nsa.api.commons.components.cfg.app.service.AppService;

@RestController
@RequestMapping("app")
public class AppController {
    @Autowired
    private AppService service;

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @GetMapping("/lista")
    public ResponseEntity<App.MensajeRespuesta> getAppList() {
        try {
            App.MensajeRespuesta respuesta = service.getAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener lista de apps: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new App.MensajeRespuesta(500L, "Error al obtener lista de apps: " + e.getMessage(), null));
        }
    }

}

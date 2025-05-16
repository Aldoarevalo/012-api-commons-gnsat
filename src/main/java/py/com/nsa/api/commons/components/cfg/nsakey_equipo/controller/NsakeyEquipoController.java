package py.com.nsa.api.commons.components.cfg.nsakey_equipo.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.nsakey_equipo.model.NsakeyEquipo;
import py.com.nsa.api.commons.components.cfg.nsakey_equipo.service.NsakeyEquipoService;

@RestController
@RequestMapping("/nsakey-equipo")
public class NsakeyEquipoController {

    private static final Logger logger = LoggerFactory.getLogger(NsakeyEquipoController.class);

    @Autowired
    private NsakeyEquipoService nsakeyEquipoService;

    @PostMapping("/insert")
    public ResponseEntity<NsakeyEquipo.MensajeRespuesta> insertarEquipo(
            @Valid @RequestBody NsakeyEquipo equipo) {
        try {
            NsakeyEquipo.MensajeRespuesta respuesta = nsakeyEquipoService.insertarEquipo(equipo);
            return new ResponseEntity<>(respuesta,
                    HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al insertar equipo: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                    new NsakeyEquipo.MensajeRespuesta(500L,
                            "Error interno del servidor: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<NsakeyEquipo.MensajeRespuesta> buscarEquipo(
            @RequestParam String cpuModelo,
            @RequestParam String macAddress) {
        try {
            NsakeyEquipo.MensajeRespuesta respuesta =
                    nsakeyEquipoService.buscarEquipo(cpuModelo, macAddress);
            return new ResponseEntity<>(respuesta,
                    HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al buscar equipo: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                    new NsakeyEquipo.MensajeRespuesta(500L,
                            "Error interno del servidor: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
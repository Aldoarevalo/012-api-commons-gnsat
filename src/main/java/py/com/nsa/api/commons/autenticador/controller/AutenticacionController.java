package py.com.nsa.api.commons.autenticador.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.autenticador.model.CodigoAcceso;
import py.com.nsa.api.commons.autenticador.service.CodigoAccesoService;

@RestController
@RequestMapping("/auth")
public class AutenticacionController {

    private static final Logger logger = LoggerFactory.getLogger(AutenticacionController.class);

    @Autowired
    private CodigoAccesoService codigoAccesoService;

    @PostMapping("/solicitar-codigo")
    public ResponseEntity<CodigoAcceso.RespuestaAutenticacion> solicitarCodigo(
            @RequestBody CodigoAcceso.SolicitudCodigo solicitud) {
        logger.info("Solicitud de código para usuario: {}", solicitud.getUsuario());

        CodigoAcceso.RespuestaAutenticacion respuesta =
                codigoAccesoService.generarCodigo(solicitud.getUsuario());

        if (respuesta.getStatus() == 200L) {
            logger.info("Código generado exitosamente para usuario: {}", solicitud.getUsuario());
            return ResponseEntity.ok(respuesta);
        }

        logger.error("Error al generar código para usuario: {}", solicitud.getUsuario());
        return ResponseEntity.status(respuesta.getStatus().intValue()).body(respuesta);
    }

    @PostMapping("/verificar-codigo")
    public ResponseEntity<CodigoAcceso.RespuestaAutenticacion> verificarCodigo(
            @RequestBody CodigoAcceso.VerificacionCodigo verificacion) {
        logger.info("Verificando código para usuario: {}", verificacion.getUsuario());

        CodigoAcceso.RespuestaAutenticacion respuesta =
                codigoAccesoService.verificarCodigo(
                        verificacion.getUsuario(),
                        verificacion.getCodigo()
                );

        if (respuesta.getStatus() == 200L) {
            logger.info("Código verificado exitosamente para usuario: {}", verificacion.getUsuario());
            return ResponseEntity.ok(respuesta);
        }

        logger.error("Error al verificar código para usuario: {}", verificacion.getUsuario());
        return ResponseEntity.status(respuesta.getStatus().intValue()).body(respuesta);
    }
}
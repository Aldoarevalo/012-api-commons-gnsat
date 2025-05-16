package py.com.nsa.api.commons.components.ref.almacen.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.almacen.model.Almacen;
import py.com.nsa.api.commons.components.ref.almacen.service.AlmacenService;

@RestController
@RequestMapping("/almacen")
public class AlmacenController {

    private static final Logger logger = LoggerFactory.getLogger(AlmacenController.class);

    @Autowired
    private AlmacenService almacenService;

    /**
     * Obtiene todos los almacenes
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/lista")
    public ResponseEntity<Almacen.MensajeRespuesta> getAlmacenesAll() {
        try {
            Almacen.MensajeRespuesta respuesta = almacenService.getAlmacenesAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los almacenes: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Almacen.MensajeRespuesta(500L, "Error al obtener los almacenes:" + e.getMessage(), null));
        }
    }

    /**
     * Obtiene un almacén por su código
     * @param alCod Código del almacén
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/buscar")
    public ResponseEntity<Almacen.MensajeRespuesta> getAlmacenByAlCod(@RequestParam("alCod") Long alCod) {
        try {
            Almacen.MensajeRespuesta respuesta = almacenService.getAlmacenByAlCod(alCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Almacen.MensajeRespuesta(500L, "Error al buscar el almacén: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene almacenes filtrados
     * @param filtro Objeto Almacen con los criterios de filtrado
     * @return ResponseEntity con la respuesta de la operación
     */
    @PostMapping("/lista-filtro")
    public ResponseEntity<Almacen.MensajeRespuesta> getAlmacenFiltered(@RequestBody Almacen filtro) {
        try {
            Almacen.MensajeRespuesta respuesta = almacenService.getAlmacenesFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener almacenes filtrados: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Almacen.MensajeRespuesta(500L, "Error al obtener almacenes filtrados: " + e.getMessage(), null));
        }
    }

    /**
     * Inserta un nuevo almacén
     * @param almacen Almacén a insertar
     * @return ResponseEntity con la respuesta de la operación
     */
    @PostMapping("/insert")
    public ResponseEntity<Almacen.MensajeRespuesta> insertAlmacen(@Valid @RequestBody Almacen almacen) {
        try {
            logger.debug("Recibiendo petición POST con almacén: {}", almacen);

            Almacen.MensajeRespuesta respuesta = almacenService.insertarAlmacen(almacen);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (409L == respuesta.getStatus()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el almacén: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Almacen.MensajeRespuesta(500L, "Error al insertar el almacén: " + e.getMessage(), null));
        }
    }

    /**
     * Actualiza un almacén existente
     * @param almacen Almacén con datos actualizados
     * @return ResponseEntity con la respuesta de la operación
     */
    @PutMapping("/update")
    public ResponseEntity<Almacen.MensajeRespuesta> updateAlmacen(@Valid @RequestBody Almacen almacen) {
        try {
            logger.info("Actualizando almacén con código: {}", almacen.getAlCod());
            Almacen.MensajeRespuesta respuesta = almacenService.updateAlmacen(almacen);

            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else if (respuesta.getStatus() == 409L) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el almacén: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Almacen.MensajeRespuesta(500L, "Error al actualizar el almacén: " + e.getMessage(), null));
        }
    }

    /**
     * Elimina un almacén por su código
     * @param alCod Código del almacén a eliminar
     * @return ResponseEntity con la respuesta de la operación
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Almacen.MensajeRespuesta> deleteAlmacen(@RequestParam("alCod") Long alCod) {
        try {
            Almacen.MensajeRespuesta respuesta = almacenService.deleteAlmacen(alCod);
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
            logger.error("<=== Error al eliminar el almacén: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Almacen.MensajeRespuesta(500L, "Error al eliminar el almacén: " + e.getMessage(), null));
        }
    }
}
package py.com.nsa.api.commons.components.ref.materialensamblado.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.materialensamblado.model.MaterialEnsamblado;
import py.com.nsa.api.commons.components.ref.materialensamblado.service.MaterialEnsambladoService;

@RestController
@RequestMapping("/material-ensamblado")
public class MaterialEnsambladoController {

    private static final Logger logger = LoggerFactory.getLogger(MaterialEnsambladoController.class);

    @Autowired
    private MaterialEnsambladoService materialService;

    /**
     * Obtiene todos los materiales ensamblados
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/lista")
    public ResponseEntity<MaterialEnsamblado.MensajeRespuesta> getMaterialesAll() {
        try {
            MaterialEnsamblado.MensajeRespuesta respuesta = materialService.getMaterialesAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los materiales ensamblados: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MaterialEnsamblado.MensajeRespuesta(500L, "Error al obtener los materiales ensamblados:" + e.getMessage(), null));
        }
    }

    /**
     * Obtiene materiales ensamblados por código de producto
     * @param proCod Código del producto
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/producto/{proCod}")
    public ResponseEntity<MaterialEnsamblado.MensajeRespuesta> getMaterialesByProducto(@PathVariable String proCod) {
        try {
            MaterialEnsamblado.MensajeRespuesta respuesta = materialService.getMaterialesByProducto(proCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MaterialEnsamblado.MensajeRespuesta(500L, "Error al buscar materiales ensamblados: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene un material ensamblado específico
     * @param meLinea Número de línea del material
     * @param proCod Código del producto
     * @return ResponseEntity con la respuesta de la operación
     */
    @GetMapping("/buscar")
    public ResponseEntity<MaterialEnsamblado.MensajeRespuesta> getMaterialById(
            @RequestParam("meLinea") Integer meLinea,
            @RequestParam("proCod") String proCod) {
        try {
            MaterialEnsamblado.MensajeRespuesta respuesta = materialService.getMaterialById(meLinea, proCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MaterialEnsamblado.MensajeRespuesta(500L, "Error al buscar el material ensamblado: " + e.getMessage(), null));
        }
    }

    /**
     * Obtiene materiales ensamblados filtrados
     * @param filtro Objeto MaterialEnsamblado con los criterios de filtrado
     * @return ResponseEntity con la respuesta de la operación
     */
    @PostMapping("/lista-filtro")
    public ResponseEntity<MaterialEnsamblado.MensajeRespuesta> getMaterialesFiltered(@RequestBody MaterialEnsamblado filtro) {
        try {
            MaterialEnsamblado.MensajeRespuesta respuesta = materialService.getMaterialesFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener materiales ensamblados filtrados: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MaterialEnsamblado.MensajeRespuesta(500L, "Error al obtener materiales ensamblados filtrados: " + e.getMessage(), null));
        }
    }

    /**
     * Inserta un nuevo material ensamblado
     * @param material Material ensamblado a insertar
     * @return ResponseEntity con la respuesta de la operación
     */
    @PostMapping("/insert")
    public ResponseEntity<MaterialEnsamblado.MensajeRespuesta> insertMaterial(@Valid @RequestBody MaterialEnsamblado material) {
        try {
            logger.debug("Recibiendo petición POST con material ensamblado: {}", material);

            MaterialEnsamblado.MensajeRespuesta respuesta = materialService.insertarMaterial(material);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (409L == respuesta.getStatus()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el material ensamblado: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MaterialEnsamblado.MensajeRespuesta(500L, "Error al insertar el material ensamblado: " + e.getMessage(), null));
        }
    }

    /**
     * Actualiza un material ensamblado existente
     * @param material Material ensamblado con datos actualizados
     * @return ResponseEntity con la respuesta de la operación
     */
    @PutMapping("/update")
    public ResponseEntity<MaterialEnsamblado.MensajeRespuesta> updateMaterial(@Valid @RequestBody MaterialEnsamblado material) {
        try {
            logger.info("Actualizando material ensamblado con línea: {} y código de producto: {}", material.getMeLinea(), material.getProCod());
            MaterialEnsamblado.MensajeRespuesta respuesta = materialService.updateMaterial(material);

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
            logger.error("<=== Error al actualizar el material ensamblado: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MaterialEnsamblado.MensajeRespuesta(500L, "Error al actualizar el material ensamblado: " + e.getMessage(), null));
        }
    }

    /**
     * Elimina un material ensamblado
     * @param meLinea Número de línea del material
     * @param proCod Código del producto
     * @return ResponseEntity con la respuesta de la operación
     */
    @DeleteMapping("/delete")
    public ResponseEntity<MaterialEnsamblado.MensajeRespuesta> deleteMaterial(
            @RequestParam("meLinea") Integer meLinea,
            @RequestParam("proCod") String proCod) {
        try {
            MaterialEnsamblado.MensajeRespuesta respuesta = materialService.deleteMaterial(meLinea, proCod);
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
            logger.error("<=== Error al eliminar el material ensamblado: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MaterialEnsamblado.MensajeRespuesta(500L, "Error al eliminar el material ensamblado: " + e.getMessage(), null));
        }
    }
}
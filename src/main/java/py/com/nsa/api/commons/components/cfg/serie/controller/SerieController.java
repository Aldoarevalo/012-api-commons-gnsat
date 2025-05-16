package py.com.nsa.api.commons.components.cfg.serie.controller;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;
import py.com.nsa.api.commons.components.cfg.pais.service.PaisService;
import py.com.nsa.api.commons.components.cfg.serie.model.Serie;
import py.com.nsa.api.commons.components.cfg.serie.service.SerieService;

@RestController
@RequestMapping("/series")
public class SerieController {
    private static final Logger logger = LoggerFactory.getLogger(SerieController.class);

    @Autowired
    private SerieService serieService;

    @Autowired
    private PaisService paisService;

    @GetMapping("/lista")
    public ResponseEntity<Serie.MensajeRespuesta> getSerieAll(){
        try {
            Serie.MensajeRespuesta respuesta = serieService.getSeriesAll();
            if(200L == respuesta.getStatus()){
                return ResponseEntity.ok(respuesta);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener las series: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Serie.MensajeRespuesta(500L, "Error al obtener las series:" + e.getMessage(), null));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<Serie.MensajeRespuesta> getSerieBySCod(@RequestParam("sCod") String sCod) {
        try {
            Serie.MensajeRespuesta respuesta = serieService.getSerieBySCod(sCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Serie.MensajeRespuesta(500L, "Error al buscar la serie: " + e.getMessage(), null));
        }
    }

    @GetMapping("/paises")
    public ResponseEntity<Pais.MensajeRespuesta> getPaisesAll() {
        try {
            Pais.MensajeRespuesta respuesta = paisService.getPaisesAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los países para series: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Pais.MensajeRespuesta(500L, "Error al obtener países para series: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Serie.MensajeRespuesta> insertSerie(@Valid @RequestBody Serie serie) {
        try {
            logger.debug("Recibiendo petición POST con serie: {}", serie);
            logger.debug("Valores recibidos - sCod: '{}', sPref: '{}', pais: '{}'",
                    serie.getSCod(), serie.getSPref(), serie.getPais());

            Serie.MensajeRespuesta respuesta = serieService.insertarSeries(serie);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar la serie: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Serie.MensajeRespuesta(500L, "Error al insertar la serie: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Serie.MensajeRespuesta> updateSerie(@Valid @RequestBody Serie serie) {
        try {
            logger.info("Actualizando serie con sCod: {}", serie.getSCod());
            Serie.MensajeRespuesta respuesta = serieService.updateSerie(serie);

            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar la serie: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Serie.MensajeRespuesta(500L, "Error al actualizar la serie: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Serie.MensajeRespuesta> deleteSerie(@RequestParam("sCod") String sCod) {
        try {
            Serie.MensajeRespuesta respuesta = serieService.deleteSerie(sCod);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Serie.MensajeRespuesta(500L, "Error al eliminar la serie: " + e.getMessage(), null));
        }
    }
}
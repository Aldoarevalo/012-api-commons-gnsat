package py.com.nsa.api.commons.components.ref.rutacab.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.rutacab.model.RutaCab;
import py.com.nsa.api.commons.components.ref.rutacab.service.RutaCabService;
import py.com.nsa.api.commons.components.ref.rutadet.model.RutaDet;
import py.com.nsa.api.commons.components.ref.rutadet.repository.RutaDetRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ruta")
public class RutaCabController {

    @Autowired
    private RutaCabService service;

    @Autowired
    private RutaDetRepository rutaDetRepository; // Inyectamos el repositorio de detalles

    private static final Logger logger = LoggerFactory.getLogger(RutaCabController.class);



    @GetMapping("/lista")
    public ResponseEntity<Map<String, Object>> getList() {
        try {
            RutaCab.MensajeRespuesta respuesta = service.getList();
            Map<String, Object> responseMap = new HashMap<>();

            if (200L == respuesta.getStatus() && respuesta.getRutas() != null) {
                List<Map<String, Object>> rutasConDetalles = new ArrayList<>();

                for (RutaCab ruta : respuesta.getRutas()) {
                    Map<String, Object> rutaInfo = new HashMap<>();
                    rutaInfo.put("ruta", ruta);

                    // Obtener detalles para la ruta usando rucCod
                    List<RutaDet> detalles = rutaDetRepository.findByRucCod(ruta.getRucCod());
                    List<Map<String, Object>> detallesFormateados = new ArrayList<>();

                    for (RutaDet detalle : detalles) {
                        Map<String, Object> detalleInfo = new HashMap<>();
                        detalleInfo.put("rucCod", detalle.getRucCod());
                        detalleInfo.put("rudSecuencia", detalle.getRudSecuencia());
                        detalleInfo.put("paraCod", detalle.getParaCod());
                        detalleInfo.put("trTiempo", detalle.getTrTiempo());

                        // Obtener la descripción de la parada desde la relación con Parada
                        if (detalle.getParada() != null) {
                            detalleInfo.put("paraDescripcion", detalle.getParada().getParaDescripcion());
                        } else {
                            detalleInfo.put("paraDescripcion", ""); // Si no hay parada, dejamos vacío
                        }

                        detallesFormateados.add(detalleInfo);
                    }

                    rutaInfo.put("detalles", detallesFormateados);
                    rutasConDetalles.add(rutaInfo);
                }

                responseMap.put("status", respuesta.getStatus());
                responseMap.put("mensaje", respuesta.getMensaje());
                responseMap.put("rutas", rutasConDetalles);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("status", respuesta.getStatus());
                responseMap.put("mensaje", respuesta.getMensaje());
                responseMap.put("data", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener la lista de rutas: {}", e.getMessage(), e);
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("status", 500L);
            errorMap.put("mensaje", "Error al obtener la lista de rutas: " + e.getMessage());
            errorMap.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<RutaCab.MensajeRespuesta> getRutaByRucCod(@RequestParam("rucCod") Long rucCod) {
        try {
            RutaCab.MensajeRespuesta respuesta = service.getRutaByRucCod(rucCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al buscar la ruta: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RutaCab.MensajeRespuesta(500L, "Error al buscar la ruta: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<Map<String, Object>> getRutasFiltradas(@RequestBody RutaCab filtro) {
        try {
            RutaCab.MensajeRespuesta respuesta = service.getRutasFiltradas(filtro);
            Map<String, Object> responseMap = new HashMap<>();

            if (200L == respuesta.getStatus() && respuesta.getRutas() != null) {
                List<Map<String, Object>> rutasConDetalles = new ArrayList<>();

                for (RutaCab ruta : respuesta.getRutas()) {
                    Map<String, Object> rutaInfo = new HashMap<>();
                    rutaInfo.put("ruta", ruta);

                    // Obtener detalles para la ruta usando rucCod
                    List<RutaDet> detalles = rutaDetRepository.findByRucCod(ruta.getRucCod());
                    List<Map<String, Object>> detallesFormateados = new ArrayList<>();

                    for (RutaDet detalle : detalles) {
                        Map<String, Object> detalleInfo = new HashMap<>();
                        detalleInfo.put("rucCod", detalle.getRucCod());
                        detalleInfo.put("rudSecuencia", detalle.getRudSecuencia());
                        detalleInfo.put("paraCod", detalle.getParaCod());
                        detalleInfo.put("trTiempo", detalle.getTrTiempo());

                        // Obtener la descripción de la parada desde la relación con Parada
                        if (detalle.getParada() != null) {
                            detalleInfo.put("paraDescripcion", detalle.getParada().getParaDescripcion());
                        } else {
                            detalleInfo.put("paraDescripcion", ""); // Si no hay parada, dejamos vacío
                        }

                        detallesFormateados.add(detalleInfo);
                    }

                    rutaInfo.put("detalles", detallesFormateados);
                    rutasConDetalles.add(rutaInfo);
                }

                responseMap.put("status", respuesta.getStatus());
                responseMap.put("mensaje", respuesta.getMensaje());
                responseMap.put("rutas", rutasConDetalles);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("status", respuesta.getStatus());
                responseMap.put("mensaje", respuesta.getMensaje());
                responseMap.put("data", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener rutas filtradas: {}", e.getMessage(), e);
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("status", 500L);
            errorMap.put("mensaje", "Error al obtener rutas filtradas: " + e.getMessage());
            errorMap.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }
    @PostMapping("/insert-cabecera")
    public ResponseEntity<RutaCab.MensajeRespuesta> saveCabecera(@RequestBody RutaCab rutaCab) {
        try {
            RutaCab.MensajeRespuesta respuesta = service.saveCabecera(rutaCab);
            return ResponseEntity.status(respuesta.getStatus().intValue() == 200 ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                    .body(respuesta);
        } catch (Exception e) {
            logger.error("Error al guardar la cabecera: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RutaCab.MensajeRespuesta(500L, "Error al guardar la cabecera: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update-cabecera")
    public ResponseEntity<RutaCab.MensajeRespuesta> updateCabecera(@RequestBody RutaCab rutaCab) {
        try {
            RutaCab.MensajeRespuesta respuesta = service.updateCabecera(rutaCab);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            }  else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar la cabecera: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RutaCab.MensajeRespuesta(500L, "Error al actualizar la cabecera: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<RutaCab.MensajeRespuesta> insert(@Valid @RequestBody RutaCab rutaCab) {
        logger.info("Ruta recibida: {}", rutaCab);
        try {
            RutaCab.MensajeRespuesta respuesta = service.save(rutaCab);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al insertar ruta: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RutaCab.MensajeRespuesta(500L, "Error al insertar ruta: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<RutaCab.MensajeRespuesta> update(@Valid @RequestBody RutaCab rutaCab) {
        try {
            RutaCab.MensajeRespuesta respuesta = service.update(rutaCab);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al actualizar ruta: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RutaCab.MensajeRespuesta(500L, "Error al actualizar ruta: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<RutaCab.MensajeRespuesta> deleteRuta(@RequestParam("rucCod") Long rucCod) {
        try {
            RutaCab.MensajeRespuesta respuesta = service.deleteById(rucCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al eliminar la ruta con ID {}: {}", rucCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RutaCab.MensajeRespuesta(500L, "Error al eliminar la ruta: " + e.getMessage(), null));
        }
    }
}
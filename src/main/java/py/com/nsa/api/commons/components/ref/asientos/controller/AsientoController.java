package py.com.nsa.api.commons.components.ref.asientos.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.asientos.model.Asiento;
import py.com.nsa.api.commons.components.ref.asientos.service.AsientoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/asiento")
public class AsientoController {

    @Autowired
    private AsientoService service;

    private static final Logger logger = LoggerFactory.getLogger(AsientoController.class);

    @GetMapping("/lista")
    public ResponseEntity<Asiento.MensajeRespuesta> getAll() {
        try {
            Asiento.MensajeRespuesta respuesta = service.getAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener la lista de asientos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Asiento.MensajeRespuesta(500L, "Error al obtener la lista de asientos: " + e.getMessage(), null));
        }
    }

    @GetMapping("/vehiculo/{veCod}")
    public ResponseEntity<Asiento.MensajeRespuesta> getByVehiculo(@PathVariable("veCod") Long veCod) {
        try {
            Asiento.MensajeRespuesta respuesta = service.getByVehiculo(veCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener asientos por vehículo con código {}: {} ===>", veCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Asiento.MensajeRespuesta(500L, "Error al obtener asientos por vehículo: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<Map<String, Object>> getAsientosFiltrados(@RequestBody Asiento filtro) {
        try {
            Asiento.MensajeRespuesta respuesta = service.getAsientosFiltered(filtro);
            Map<String, Object> responseMap = new HashMap<>();

            if (200L == respuesta.getStatus() && respuesta.getAsientos() != null) {
                List<Map<String, Object>> asientosDetallados = new ArrayList<>();

                for (Asiento asiento : respuesta.getAsientos()) {
                    Map<String, Object> asientoInfo = new HashMap<>();
                    asientoInfo.put("vasCod", asiento.getVasCod());
                    asientoInfo.put("vasNroAsiento", asiento.getVasNroAsiento());

                    // Agregar vehículo con todas sus relaciones
                    if (asiento.getVehiculo() != null) {
                        Map<String, Object> vehiculoInfo = new HashMap<>();
                        vehiculoInfo.put("veCod", asiento.getVehiculo().getVeCod());
                        vehiculoInfo.put("parTipo", asiento.getVehiculo().getParTipo());

                        // Agregar parValor del vehículo
                        if (asiento.getVehiculo().getParValor() != null) {
                            Map<String, Object> parValorInfo = new HashMap<>();
                            parValorInfo.put("parValor", asiento.getVehiculo().getParValor().getParValor());
                            parValorInfo.put("parDescripcion", asiento.getVehiculo().getParValor().getParDescripcion());
                            parValorInfo.put("parComentario", asiento.getVehiculo().getParValor().getParComentario());

                            if (asiento.getVehiculo().getParValor().getParametro() != null) {
                                Map<String, Object> parametroInfo = new HashMap<>();
                                parametroInfo.put("pmCod", asiento.getVehiculo().getParValor().getParametro().getPmCod());
                                parametroInfo.put("pmNombre", asiento.getVehiculo().getParValor().getParametro().getPmNombre());
                                parValorInfo.put("parametro", parametroInfo);
                            }

                            vehiculoInfo.put("parValor", parValorInfo);
                        }

                        vehiculoInfo.put("veChapa", asiento.getVehiculo().getVeChapa());
                        vehiculoInfo.put("veEmpresa", asiento.getVehiculo().getVeEmpresa());
                        vehiculoInfo.put("vePiso", asiento.getVehiculo().getVePiso());
                        vehiculoInfo.put("veUltModif", asiento.getVehiculo().getVeUltModif());
                        vehiculoInfo.put("veEstado", asiento.getVehiculo().getVeEstado());
                        vehiculoInfo.put("veNumero", asiento.getVehiculo().getVeNumero());

                        // Agregar empresa con sus relaciones
                        if (asiento.getVehiculo().getEmpresa() != null) {
                            Map<String, Object> empresaInfo = new HashMap<>();
                            empresaInfo.put("emCod", asiento.getVehiculo().getEmpresa().getEmCod());
                            empresaInfo.put("emDescripcion", asiento.getVehiculo().getEmpresa().getEmDescripcion());
                            empresaInfo.put("emRuc", asiento.getVehiculo().getEmpresa().getEmRuc());
                            empresaInfo.put("emDir", asiento.getVehiculo().getEmpresa().getEmDir());
                            empresaInfo.put("emTel", asiento.getVehiculo().getEmpresa().getEmTel());
                            empresaInfo.put("paCod", asiento.getVehiculo().getEmpresa().getPaCod());

                            if (asiento.getVehiculo().getEmpresa().getPais() != null) {
                                Map<String, Object> paisInfo = new HashMap<>();
                                paisInfo.put("paCod", asiento.getVehiculo().getEmpresa().getPais().getPaCod());
                                paisInfo.put("paDescripcion", asiento.getVehiculo().getEmpresa().getPais().getPaDescripcion());
                                paisInfo.put("paCodTel", asiento.getVehiculo().getEmpresa().getPais().getPaCodTel());
                                empresaInfo.put("pais", paisInfo);
                            }

                            vehiculoInfo.put("empresa", empresaInfo);
                        }

                        asientoInfo.put("vehiculo", vehiculoInfo);
                    }

                    // Agregar tipo de asiento
                    if (asiento.getVasTasiento() != null) {
                        Map<String, Object> tasientoInfo = new HashMap<>();
                        tasientoInfo.put("parValor", asiento.getVasTasiento().getParValor());
                        tasientoInfo.put("parDescripcion", asiento.getVasTasiento().getParDescripcion());
                        tasientoInfo.put("parComentario", asiento.getVasTasiento().getParComentario());

                        if (asiento.getVasTasiento().getParametro() != null) {
                            Map<String, Object> parametroInfo = new HashMap<>();
                            parametroInfo.put("pmCod", asiento.getVasTasiento().getParametro().getPmCod());
                            parametroInfo.put("pmNombre", asiento.getVasTasiento().getParametro().getPmNombre());
                            tasientoInfo.put("parametro", parametroInfo);
                        }

                        asientoInfo.put("vasTasiento", tasientoInfo);
                    }

                    // Agregar tipo de ubicación
                    if (asiento.getVasTubicacion() != null) {
                        Map<String, Object> tubicacionInfo = new HashMap<>();
                        tubicacionInfo.put("parValor", asiento.getVasTubicacion().getParValor());
                        tubicacionInfo.put("parDescripcion", asiento.getVasTubicacion().getParDescripcion());
                        tubicacionInfo.put("parComentario", asiento.getVasTubicacion().getParComentario());

                        if (asiento.getVasTubicacion().getParametro() != null) {
                            Map<String, Object> parametroInfo = new HashMap<>();
                            parametroInfo.put("pmCod", asiento.getVasTubicacion().getParametro().getPmCod());
                            parametroInfo.put("pmNombre", asiento.getVasTubicacion().getParametro().getPmNombre());
                            tubicacionInfo.put("parametro", parametroInfo);
                        }

                        asientoInfo.put("vasTubicacion", tubicacionInfo);
                    }

                    asientoInfo.put("vasPiso", asiento.getVasPiso());
                    asientoInfo.put("vasFila", asiento.getVasFila());
                    asientoInfo.put("vasColumna", asiento.getVasColumna());

                    asientosDetallados.add(asientoInfo);
                }

                responseMap.put("status", respuesta.getStatus());
                responseMap.put("mensaje", respuesta.getMensaje());
                responseMap.put("asientos", asientosDetallados);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("status", respuesta.getStatus());
                responseMap.put("mensaje", respuesta.getMensaje());
                responseMap.put("asientos", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener asientos filtrados: {} ===>", e.getMessage(), e);
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("status", 500L);
            errorMap.put("mensaje", "Error al obtener asientos filtrados: " + e.getMessage());
            errorMap.put("asientos", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Asiento.MensajeRespuesta> save(@RequestBody Asiento asiento) {
        try {
            Asiento.MensajeRespuesta respuesta = service.save(asiento);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar asiento: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Asiento.MensajeRespuesta(500L, "Error al insertar asiento: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Asiento.MensajeRespuesta> update(@RequestBody Asiento asiento) {
        try {
            Asiento.MensajeRespuesta respuesta = service.update(asiento);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar asiento: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Asiento.MensajeRespuesta(500L, "Error al actualizar asiento: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Asiento.MensajeRespuesta> delete(@RequestParam("id") Long id) {
        try {
            Asiento.MensajeRespuesta respuesta = service.delete(id);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al eliminar el asiento con ID {}: {} ===>", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Asiento.MensajeRespuesta(500L, "Error al eliminar el asiento: " + e.getMessage(), null));
        }
    }
}
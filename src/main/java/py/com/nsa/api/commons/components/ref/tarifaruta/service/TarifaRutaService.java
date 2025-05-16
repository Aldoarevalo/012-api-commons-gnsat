package py.com.nsa.api.commons.components.ref.tarifaruta.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.tarifaruta.model.TarifaRuta;
import py.com.nsa.api.commons.components.ref.tarifaruta.model.pk.TarifaRutaPK;
import py.com.nsa.api.commons.components.ref.tarifaruta.repository.TarifaRutaRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TarifaRutaService {

    @Autowired
    private TarifaRutaRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(TarifaRutaService.class);

    public TarifaRuta.MensajeRespuesta getAll() {
        try {
            List<TarifaRuta> tarifas = repository.findAll();
            if (tarifas.isEmpty()) {
                return new TarifaRuta.MensajeRespuesta(204L, "No se encontraron tarifas de ruta.", null);
            }
            return new TarifaRuta.MensajeRespuesta(200L, "Tarifas de ruta obtenidas exitosamente.", tarifas);
        } catch (Exception e) {
            logger.error("Error al obtener tarifas de ruta: {}", e.getMessage(), e);
            return new TarifaRuta.MensajeRespuesta(500L, "Error al obtener tarifas de ruta: " + e.getMessage(), null);
        }
    }

    public TarifaRuta.MensajeRespuesta getFiltered(TarifaRuta filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("rucCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("rudSecuencia", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("paraCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("rtTarifaGs", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("rtTarifaArs", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("rtTarifaRs", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("rtTarifaUsd", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("usuCod", ExampleMatcher.GenericPropertyMatchers.exact());

            Example<TarifaRuta> example = Example.of(filtro, matcher);
            List<TarifaRuta> tarifas = repository.findAll(example);

            if (!tarifas.isEmpty()) {
                return new TarifaRuta.MensajeRespuesta(200L, "Tarifas de ruta encontradas.", tarifas);
            } else {
                return new TarifaRuta.MensajeRespuesta(204L, "No se encontraron tarifas de ruta.", null);
            }
        } catch (Exception e) {
            logger.error("Error al filtrar tarifas de ruta: {}", e.getMessage(), e);
            return new TarifaRuta.MensajeRespuesta(500L, "Error al filtrar tarifas de ruta: " + e.getMessage(), null);
        }
    }

    @Transactional
    public TarifaRuta.MensajeRespuesta insert(TarifaRuta tarifaRuta) {
        try {
            List<TarifaRuta> tarifasGuardadas = new ArrayList<>();
            List<TarifaRuta> tarifas = tarifaRuta.getTarifas();

            if (tarifas != null && !tarifas.isEmpty()) {
                // Primera pasada: Validar todas las tarifas antes de insertar
                for (TarifaRuta tarifa : tarifas) {
                    // Validar campos obligatorios
                    if (tarifa.getRucCod() == null || tarifa.getRudSecuencia() == null ||
                            tarifa.getParaCod() == null || tarifa.getRtTarifaUsd() == null ||
                            tarifa.getUsuCod() == null) {
                        return new TarifaRuta.MensajeRespuesta(400L,
                                "Faltan campos obligatorios en una tarifa: rucCod, rudSecuencia, paraCod, rtTarifaUsd o usuCod",
                                null);
                    }

                    // Verificar si la tarifa ya existe
                    TarifaRutaPK pk = new TarifaRutaPK(tarifa.getRucCod(), tarifa.getRudSecuencia());
                    Optional<TarifaRuta> existingTarifa = repository.findById(pk);

                    if (existingTarifa.isPresent()) {
                        return new TarifaRuta.MensajeRespuesta(409L,
                                "La tarifa con rucCod: " + tarifa.getRucCod() + " y rudSecuencia: " + tarifa.getRudSecuencia() + " ya existe.",
                                null);
                    }
                }

                // Segunda pasada: Insertar todas las tarifas si todas son válidas
                for (TarifaRuta tarifa : tarifas) {
                    // Establecer fecha si no viene
                    if (tarifa.getRtUltmod() == null) {
                        tarifa.setRtUltmod(new Date());
                    }

                    // Guardar la tarifa
                    TarifaRuta savedTarifa = repository.save(tarifa);
                    tarifasGuardadas.add(savedTarifa);
                }

                return new TarifaRuta.MensajeRespuesta(200L, "Tarifas de ruta insertadas exitosamente.", tarifasGuardadas);
            } else {
                return new TarifaRuta.MensajeRespuesta(400L, "No se proporcionaron tarifas para insertar.", null);
            }
        } catch (Exception e) {
            logger.error("Error al insertar tarifas de ruta: {}", e.getMessage(), e);
            return new TarifaRuta.MensajeRespuesta(500L, "Error al insertar tarifas de ruta: " + e.getMessage(), null);
        }
    }
    @Transactional
    public TarifaRuta.MensajeRespuesta update(TarifaRuta tarifaRuta) {
        try {
            List<TarifaRuta> tarifasActualizadas = new ArrayList<>();
            List<TarifaRuta> tarifas = tarifaRuta.getTarifas();

            if (tarifas != null && !tarifas.isEmpty()) {
                // Primera pasada: Validar todas las tarifas antes de actualizar
                for (TarifaRuta tarifa : tarifas) {
                    // Validar campos obligatorios
                    if (tarifa.getRucCod() == null || tarifa.getRudSecuencia() == null) {
                        return new TarifaRuta.MensajeRespuesta(400L,
                                "Faltan campos obligatorios en una tarifa: rucCod o rudSecuencia",
                                null);
                    }

                    // Verificar si existe
                    TarifaRutaPK pk = new TarifaRutaPK(tarifa.getRucCod(), tarifa.getRudSecuencia());
                    Optional<TarifaRuta> existingTarifa = repository.findById(pk);

                    if (!existingTarifa.isPresent()) {
                        return new TarifaRuta.MensajeRespuesta(404L,
                                "Tarifa con rucCod: " + tarifa.getRucCod() + " y rudSecuencia: " + tarifa.getRudSecuencia() + " no encontrada.",
                                null);
                    }
                }

                // Segunda pasada: Actualizar todas las tarifas si todas existen
                for (TarifaRuta tarifa : tarifas) {
                    tarifa.setRtUltmod(new Date());
                    TarifaRuta updatedTarifa = repository.save(tarifa);
                    tarifasActualizadas.add(updatedTarifa);
                }

                return new TarifaRuta.MensajeRespuesta(200L, "Tarifas de ruta actualizadas exitosamente.", tarifasActualizadas);
            } else {
                return new TarifaRuta.MensajeRespuesta(400L, "No se proporcionaron tarifas para actualizar.", null);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar tarifas de ruta: {}", e.getMessage(), e);
            return new TarifaRuta.MensajeRespuesta(500L, "Error al actualizar tarifas de ruta: " + e.getMessage(), null);
        }
    }

    @Transactional
    public TarifaRuta.MensajeRespuesta deleteById(Long rucCod, Long rudSecuencia) {
        try {
            TarifaRutaPK pk = new TarifaRutaPK(rucCod, rudSecuencia);
            if (repository.existsById(pk)) {
                repository.deleteById(pk);
                return new TarifaRuta.MensajeRespuesta(200L, "Tarifa de ruta eliminada exitosamente.", null);
            } else {
                return new TarifaRuta.MensajeRespuesta(404L, "Tarifa de ruta no encontrada.", null);
            }
        } catch (JpaSystemException e) {
            logger.error("Error de integridad al eliminar tarifa de ruta: {}", e.getMessage(), e);
            return new TarifaRuta.MensajeRespuesta(204L, "No se puede eliminar la tarifa de ruta porque está referenciada por otros registros.", null);
        } catch (Exception e) {
            logger.error("Error al eliminar tarifa de ruta: {}", e.getMessage(), e);
            return new TarifaRuta.MensajeRespuesta(500L, "Error al eliminar tarifa de ruta: " + e.getMessage(), null);
        }
    }
}
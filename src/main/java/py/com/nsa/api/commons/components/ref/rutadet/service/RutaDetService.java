package py.com.nsa.api.commons.components.ref.rutadet.service;

import jakarta.transaction.Transactional;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.rutadet.model.RutaDet;
import py.com.nsa.api.commons.components.ref.rutadet.model.pk.RutaDetPK;
import py.com.nsa.api.commons.components.ref.rutadet.repository.RutaDetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RutaDetService {

    @Autowired
    private RutaDetRepository rutaDetRepository;

    private static final Logger logger = LoggerFactory.getLogger(RutaDetService.class);

    // Clase de respuesta para RutaDet
    @Data
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<RutaDet> detalles;

        public MensajeRespuesta(Long status, String mensaje, List<RutaDet> detalles) {
            this.status = status;
            this.mensaje = mensaje;
            this.detalles = detalles;
        }
    }

    // Listar todos los detalles
    //@Transactional
    public MensajeRespuesta getList() {
        try {
            List<RutaDet> detalles = rutaDetRepository.findAll();
            if (detalles != null && !detalles.isEmpty()) {
                return new MensajeRespuesta(200L, "Detalles obtenidos exitosamente.", detalles);
            } else {
                return new MensajeRespuesta(204L, "No se encontraron detalles.", null);
            }
        } catch (Exception e) {
            logger.error("Error al obtener detalles: {}", e.getMessage(), e);
            return new MensajeRespuesta(500L, "Error al obtener los detalles: " + e.getMessage(), null);
        }
    }

    //@Transactional(readOnly = true)
    public MensajeRespuesta getDetallesFiltrados(RutaDet filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("rucCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("rudSecuencia", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("paraCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("trTiempo", ExampleMatcher.GenericPropertyMatchers.exact());

            Example<RutaDet> example = Example.of(filtro, matcher);
            List<RutaDet> detalles = rutaDetRepository.findAll(example);

            if (detalles != null && !detalles.isEmpty()) {
                return new MensajeRespuesta(200L, "Detalles encontrados.", detalles);
            } else {
                return new MensajeRespuesta(204L, "No se encontraron detalles con los filtros proporcionados.", null);
            }
        } catch (Exception e) {
            logger.error("Error al filtrar detalles: {}", e.getMessage(), e);
            return new MensajeRespuesta(500L, "Error al filtrar los detalles: " + e.getMessage(), null);
        }
    }



    // Guardar un nuevo detalle
    @Transactional
    public MensajeRespuesta save(RutaDet rutaDet) {
        try {
            // Validar que los campos obligatorios estén presentes
            if (rutaDet.getRucCod() == null || rutaDet.getRudSecuencia() == null ||
                    rutaDet.getParaCod() == null || rutaDet.getTrTiempo() == null) {
                throw new IllegalArgumentException("Faltan campos obligatorios: rucCod, rudSecuencia, paraCod o trTiempo");
            }

            RutaDet savedRutaDet = rutaDetRepository.save(rutaDet);
            logger.info("Detalle guardado con rucCod: {} y rudSecuencia: {}",
                    savedRutaDet.getRucCod(), savedRutaDet.getRudSecuencia());
            return new MensajeRespuesta(200L, "Detalle insertado exitosamente.", List.of(savedRutaDet));
        } catch (Exception e) {
            logger.error("Error al insertar detalle: {}", e.getMessage(), e);
            return new MensajeRespuesta(500L, "Error al insertar el detalle: " + e.getMessage(), null);
        }
    }

    // Actualizar un detalle existente
    @Transactional
    public MensajeRespuesta update(RutaDet rutaDet) {
        try {
            // Crear una instancia de la clave primaria compuesta
            RutaDetPK pk = new RutaDetPK(rutaDet.getRucCod(), rutaDet.getRudSecuencia());

            // Verificar si el detalle existe usando la clave compuesta
            Optional<RutaDet> existingRutaDet = rutaDetRepository.findById(pk);

            if (existingRutaDet.isPresent()) {
                RutaDet updatedRutaDet = rutaDetRepository.save(rutaDet);
                return new MensajeRespuesta(200L, "Detalle actualizado exitosamente.", List.of(updatedRutaDet));
            } else {
                return new MensajeRespuesta(204L, "Detalle no encontrado.", null);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar detalle: {}", e.getMessage(), e);
            return new MensajeRespuesta(500L, "Error al actualizar el detalle: " + e.getMessage(), null);
        }
    }

    @Transactional
    public MensajeRespuesta updatedetalle(RutaDet rutaDet) {
        try {
            // Imprimir los valores recibidos
            logger.info("Valores recibidos en updatedetalle: rucCod={}, rudSecuencia={}, paraCod={}, trTiempo={}, rucCodOld={}, rudSecuenciaOld={}",
                    rutaDet.getRucCod(),
                    rutaDet.getRudSecuencia(),
                    rutaDet.getParaCod(),
                    rutaDet.getTrTiempo(),
                    rutaDet.getRucCodOld(),
                    rutaDet.getRudSecuenciaOld());
            // Validación de valores nulos
            if (rutaDet.getRucCod() == null || rutaDet.getRudSecuencia() == null ||
                    rutaDet.getRucCodOld() == null || rutaDet.getRudSecuenciaOld() == null) {
                return new MensajeRespuesta(400L, "Los campos rucCod, rudSecuencia, rucCodOld y rudSecuenciaOld no pueden ser nulos.", null);
            }

            // Ejecutar la consulta nativa de actualización
            int registrosActualizados = rutaDetRepository.updateRutaDetNative(
                    rutaDet.getRudSecuencia(), // Nueva secuencia
                    rutaDet.getParaCod(),
                    rutaDet.getTrTiempo(),
                    rutaDet.getRucCodOld(), // Valor original de rucCod
                    rutaDet.getRudSecuenciaOld() // Valor original de rudSecuencia
            );

            // Verificar si se actualizó algún registro
            if (registrosActualizados > 0) {
                // Crear la nueva clave primaria para recuperar el registro actualizado
                RutaDetPK newPk = new RutaDetPK(rutaDet.getRucCod(), rutaDet.getRudSecuencia());
                Optional<RutaDet> updatedRutaDet = rutaDetRepository.findById(newPk);

                if (updatedRutaDet.isPresent()) {
                    return new MensajeRespuesta(200L, "Detalle actualizado exitosamente.", List.of(updatedRutaDet.get()));
                } else {
                    return new MensajeRespuesta(500L, "Detalle actualizado, pero no se pudieron recuperar los datos.", null);
                }
            } else {
                return new MensajeRespuesta(404L, "No se encontró el detalle para actualizar.", null);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar detalle: {}", e.getMessage(), e);
            return new MensajeRespuesta(500L, "Error al actualizar el detalle: " + e.getMessage(), null);
        }
    }


    //@Transactional
    public MensajeRespuesta deleteById(Long rucCod, Long rudSecuencia) {
        try {
            RutaDetPK pk = new RutaDetPK(rucCod, rudSecuencia);
            if (rutaDetRepository.existsById(pk)) {
                rutaDetRepository.deleteById(pk);
                return new MensajeRespuesta(200L, "Detalle eliminado exitosamente.", null);
            } else {
                return new MensajeRespuesta(404L, "Detalle no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            logger.error("Error de integridad al eliminar detalle: {}", e.getMessage(), e);
            return new MensajeRespuesta(204L, "No se puede eliminar el detalle porque está referenciado por otros registros.", null);
        } catch (Exception e) {
            logger.error("Error al eliminar detalle: {}", e.getMessage(), e);
            return new MensajeRespuesta(500L, "Error al eliminar el detalle: " + e.getMessage(), null);
        }
    }

    // Eliminar todos los detalles de una ruta (usado por RutaCabService)
    @Transactional
    public void deleteByRucCod(Long rucCod) {
        try {
            rutaDetRepository.deleteByRucCod(rucCod);
        } catch (Exception e) {
            logger.error("Error al eliminar detalles por rucCod: {}", e.getMessage(), e);
            throw e;
        }
    }
}
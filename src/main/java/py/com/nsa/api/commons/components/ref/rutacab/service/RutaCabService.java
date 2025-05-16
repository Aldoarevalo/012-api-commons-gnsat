package py.com.nsa.api.commons.components.ref.rutacab.service;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.rutacab.model.RutaCab;
import py.com.nsa.api.commons.components.ref.rutadet.model.RutaDet;
import py.com.nsa.api.commons.components.ref.rutacab.repository.RutaCabRepository;
import py.com.nsa.api.commons.components.ref.rutadet.model.pk.RutaDetPK;
import py.com.nsa.api.commons.components.ref.rutadet.repository.RutaDetRepository;
import py.com.nsa.api.commons.components.ref.rutadet.service.RutaDetService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RutaCabService {

    @Autowired
    private RutaCabRepository rutaCabRepository;

    @Autowired
    private RutaDetRepository rutaDetRepository;

    @Autowired
    private RutaDetService rutaDetService;

    private static final Logger logger = LoggerFactory.getLogger(RutaCabService.class);


    public RutaCab.MensajeRespuesta getList() {
        try {
            List<RutaCab> rutas = rutaCabRepository.findAll();
            if (rutas != null && !rutas.isEmpty()) {
                return new RutaCab.MensajeRespuesta(200L, "Rutas obtenidas exitosamente.", rutas);
            } else {
                return new RutaCab.MensajeRespuesta(204L, "No se encontraron rutas.", null);
            }
        } catch (Exception e) {
            logger.error("Error al obtener rutas: {}", e.getMessage(), e);
            return new RutaCab.MensajeRespuesta(500L, "Error al obtener rutas: " + e.getMessage(), null);
        }
    }

    public RutaCab.MensajeRespuesta getRutasFiltradas(RutaCab filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("rucCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("rucEstado", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("rucDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("rucServicio", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("rucEmpresa", ExampleMatcher.GenericPropertyMatchers.exact());

            Example<RutaCab> example = Example.of(filtro, matcher);
            List<RutaCab> rutas = rutaCabRepository.findAll(example);

            if (!rutas.isEmpty()) {
                return new RutaCab.MensajeRespuesta(200L, "Rutas encontradas", rutas);
            } else {
                return new RutaCab.MensajeRespuesta(204L, "No se encontraron rutas", null);
            }
        } catch (Exception e) {
            logger.error("Error al filtrar rutas: {}", e.getMessage(), e);
            return new RutaCab.MensajeRespuesta(500L, "Error al filtrar rutas: " + e.getMessage(), null);
        }
    }


    @Transactional
    public RutaCab.MensajeRespuesta saveCabecera(RutaCab rutaCab) {
        try {
            // Establecer fecha de última modificación si no está presente (respetamos el valor del JSON si viene)
            if (rutaCab.getRucUltmod() == null) {
                rutaCab.setRucUltmod(new Date());
            }

            // Guardar solo la cabecera
            RutaCab savedRutaCab = rutaCabRepository.save(rutaCab);
            logger.info("Cabecera guardada con rucCod: {}", savedRutaCab.getRucCod());

            // No procesamos detalles, devolvemos la cabecera sin modificar la lista detalles
            savedRutaCab.setDetalles(null); // Opcional: limpiamos detalles para consistencia en la respuesta
            return new RutaCab.MensajeRespuesta(200L, "Cabecera insertada exitosamente.", List.of(savedRutaCab));
        } catch (Exception e) {
            logger.error("Error al insertar cabecera: {}", e.getMessage(), e);
            throw new RuntimeException("Error al insertar la cabecera: " + e.getMessage(), e);
        }
    }

    @Transactional
    public RutaCab.MensajeRespuesta updateCabecera(RutaCab rutaCab) {
        try {
            // Validar que rucCod no sea nulo
            if (rutaCab.getRucCod() == null) {
                logger.warn("El código de la ruta (rucCod) no puede ser nulo.");
                return new RutaCab.MensajeRespuesta(400L, "El código de la ruta (rucCod) no puede ser nulo.", null);
            }

            // Verificar si la ruta existe usando existsById
            if (!rutaCabRepository.existsById(rutaCab.getRucCod())) {
                logger.warn("Ruta no encontrada con rucCod: {}", rutaCab.getRucCod());
                return new RutaCab.MensajeRespuesta(404L, "Ruta no encontrada con rucCod: " + rutaCab.getRucCod(), null);
            }

            // Actualizar la fecha de última modificación
            if (rutaCab.getRucUltmod() == null) {
                rutaCab.setRucUltmod(new Date());
            }

            // Guardar la cabecera actualizada
            RutaCab updatedRutaCab = rutaCabRepository.save(rutaCab);
            logger.info("Cabecera actualizada con rucCod: {}", updatedRutaCab.getRucCod());

            // No tocamos los detalles, devolvemos solo la cabecera actualizada
            return new RutaCab.MensajeRespuesta(200L, "Cabecera actualizada exitosamente.", List.of(updatedRutaCab));
        } catch (Exception e) {
            logger.error("Error al actualizar cabecera: {}", e.getMessage(), e);
            return new RutaCab.MensajeRespuesta(500L, "Error al actualizar la cabecera: " + e.getMessage(), null);
        }
    }

    @Transactional
    public RutaCab.MensajeRespuesta save(RutaCab rutaCab) {
        try {
            List<RutaDet> detalles = rutaCab.getDetalles();

            // Primera pasada: Validar todos los detalles antes de guardar nada
            if (detalles != null && !detalles.isEmpty()) {
                for (RutaDet detalle : detalles) {
                    if (detalle.getRudSecuencia() == null || detalle.getParaCod() == null || detalle.getTrTiempo() == null) {
                        return new RutaCab.MensajeRespuesta(204L,
                                "Faltan campos obligatorios en un detalle: rudSecuencia, paraCod o trTiempo",
                                null);
                    }
                    // Opcional: Verificar duplicados si aplica en tu lógica
                    RutaDetPK pk = new RutaDetPK(rutaCab.getRucCod(), detalle.getRudSecuencia());
                    if (rutaDetRepository.existsById(pk)) {
                        return new RutaCab.MensajeRespuesta(204L,
                                "El detalle con rudSecuencia: " + detalle.getRudSecuencia() + " ya existe para esta ruta.",
                                null);
                    }
                }
            }

            // Establecer fecha de última modificación
            if (rutaCab.getRucUltmod() == null) {
                rutaCab.setRucUltmod(new Date());
            }

            // Guardar la cabecera
            RutaCab savedRutaCab = rutaCabRepository.save(rutaCab);
            logger.info("Cabecera guardada con rucCod: {}", savedRutaCab.getRucCod());

            // Segunda pasada: Guardar los detalles
            List<RutaDet> detallesGuardados = new ArrayList<>();
            if (detalles != null && !detalles.isEmpty()) {
                for (RutaDet detalle : detalles) {
                    detalle.setRucCod(savedRutaCab.getRucCod());
                    RutaDetService.MensajeRespuesta detalleRespuesta = rutaDetService.save(detalle);
                    if (detalleRespuesta.getStatus() != 200L) {
                        throw new RuntimeException("Error al guardar detalle: " + detalleRespuesta.getMensaje());
                    }
                    detallesGuardados.addAll(detalleRespuesta.getDetalles());
                }
            }

            savedRutaCab.setDetalles(detallesGuardados);
            return new RutaCab.MensajeRespuesta(200L, "Ruta insertada exitosamente.", List.of(savedRutaCab));
        } catch (Exception e) {
            logger.error("Error al insertar ruta: {}", e.getMessage(), e);
            return new RutaCab.MensajeRespuesta(500L, "Error al insertar la ruta: " + e.getMessage(), null);
        }
    }

    @Transactional
    public RutaCab.MensajeRespuesta update(RutaCab rutaCab) {
        try {
            // Validar que rucCod no sea nulo
            if (rutaCab.getRucCod() == null) {
                logger.warn("El código de la ruta (rucCod) no puede ser nulo.");
                return new RutaCab.MensajeRespuesta(204L, "El código de la ruta (rucCod) no puede ser nulo.", null);
            }

            // Verificar si la ruta existe
            if (!rutaCabRepository.existsById(rutaCab.getRucCod())) {
                logger.warn("Ruta no encontrada con rucCod: {}", rutaCab.getRucCod());
                return new RutaCab.MensajeRespuesta(204L, "Ruta no encontrada con rucCod: " + rutaCab.getRucCod(), null);
            }

            List<RutaDet> detalles = rutaCab.getDetalles();

            // Primera pasada: Validar todos los detalles
            if (detalles != null && !detalles.isEmpty()) {
                for (RutaDet detalle : detalles) {
                    if (detalle.getRudSecuencia() == null || detalle.getParaCod() == null || detalle.getTrTiempo() == null) {
                        return new RutaCab.MensajeRespuesta(204L,
                                "Faltan campos obligatorios en un detalle: rudSecuencia, paraCod o trTiempo",
                                null);
                    }
                }
            }

            // Actualizar la fecha
            if (rutaCab.getRucUltmod() == null) {
                rutaCab.setRucUltmod(new Date());
            }

            // Guardar la cabecera actualizada
            RutaCab updatedRutaCab = rutaCabRepository.save(rutaCab);
            logger.info("Cabecera actualizada con rucCod: {}", updatedRutaCab.getRucCod());

            // Segunda pasada: Procesar los detalles
            List<RutaDet> detallesGuardados = new ArrayList<>();
            if (detalles != null && !detalles.isEmpty()) {
                // Opcional: Eliminar detalles previos si la intención es reemplazarlos
                // rutaDetService.deleteByRucCod(updatedRutaCab.getRucCod());

                for (RutaDet detalle : detalles) {
                    detalle.setRucCod(updatedRutaCab.getRucCod());
                    RutaDetService.MensajeRespuesta detalleRespuesta = rutaDetService.update(detalle);
                    if (detalleRespuesta.getStatus() != 200L) {
                        throw new RuntimeException("Error al guardar detalle: " + detalleRespuesta.getMensaje());
                    }
                    detallesGuardados.addAll(detalleRespuesta.getDetalles());
                }
            }

            updatedRutaCab.setDetalles(detallesGuardados);
            return new RutaCab.MensajeRespuesta(200L, "Ruta actualizada exitosamente.", List.of(updatedRutaCab));
        } catch (Exception e) {
            logger.error("Error al actualizar ruta: {}", e.getMessage(), e);
            return new RutaCab.MensajeRespuesta(500L, "Error al actualizar la ruta: " + e.getMessage(), null);
        }
    }

    public RutaCab.MensajeRespuesta getRutaByRucCod(Long rucCod) {
        try {
            Optional<RutaCab> ruta = rutaCabRepository.findById(rucCod);
            if (ruta.isEmpty()) {
                return new RutaCab.MensajeRespuesta(204L, "No se encontró la ruta con el código: " + rucCod, null);
            }
            return new RutaCab.MensajeRespuesta(200L, "Ruta encontrada.", List.of(ruta.get()));
        } catch (Exception e) {
            logger.error("Error al obtener la ruta: ", e);
            return new RutaCab.MensajeRespuesta(500L, "Error al obtener la ruta: " + e.getMessage(), null);
        }
    }

    @Transactional
    public RutaCab.MensajeRespuesta deleteById(Long rucCod) {
        try {
            if (rutaCabRepository.existsById(rucCod)) {
                // Obtener todos los detalles asociados a esta cabecera
                List<RutaDet> detalles = rutaDetRepository.findByRucCod(rucCod);

                // Eliminar cada detalle usando su clave primaria compuesta
                for (RutaDet detalle : detalles) {
                    rutaDetService.deleteById(detalle.getRucCod(), detalle.getRudSecuencia());
                }

                // Eliminar la cabecera después de eliminar todos los detalles
                rutaCabRepository.deleteById(rucCod);
                return new RutaCab.MensajeRespuesta(200L, "Ruta eliminada exitosamente.", null);
            } else {
                return new RutaCab.MensajeRespuesta(204L, "Ruta no encontrada.", null);
            }
        } catch (JpaSystemException e) {
            logger.error("Error de integridad al eliminar ruta: {}", e.getMessage(), e);
            return new RutaCab.MensajeRespuesta(204L, "No se puede eliminar la ruta porque está referenciada por otros registros.", null);
        } catch (Exception e) {
            logger.error("Error al eliminar ruta: {}", e.getMessage(), e);
            return new RutaCab.MensajeRespuesta(500L, "Error al eliminar la ruta: " + e.getMessage(), null);
        }
    }
}
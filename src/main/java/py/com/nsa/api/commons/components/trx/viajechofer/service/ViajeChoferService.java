package py.com.nsa.api.commons.components.trx.viajechofer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.trx.viajechofer.model.ViajeChofer;
import py.com.nsa.api.commons.components.trx.viajechofer.repository.ViajeChoferRepository;
import py.com.nsa.api.commons.components.trx.viaje.model.Viaje;
import py.com.nsa.api.commons.components.trx.viaje.repository.ViajeRepository;
import py.com.nsa.api.commons.components.ref.empleado.model.Empleado;
import py.com.nsa.api.commons.components.ref.empleado.repository.EmpleadoRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ViajeChoferService {
    private static final Logger logger = LoggerFactory.getLogger(ViajeChoferService.class);
    @Autowired
    private ViajeChoferRepository viajeChoferRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Listar todas las asignaciones
    public ViajeChofer.MensajeRespuesta listarTodasLasAsignaciones() {
        List<ViajeChofer> asignaciones = viajeChoferRepository.findAll();
        if (asignaciones.isEmpty()) {
            return new ViajeChofer.MensajeRespuesta(204L, "No se encontraron asignaciones.", null);
        }
        return new ViajeChofer.MensajeRespuesta(200L, "Asignaciones obtenidas exitosamente.", asignaciones);
    }

    // Listar asignaciones por viaje
    public ViajeChofer.MensajeRespuesta listarPorViaje(Integer vCod) {
        List<ViajeChofer> asignaciones = viajeChoferRepository.findByVCod(vCod);
        if (asignaciones.isEmpty()) {
            return new ViajeChofer.MensajeRespuesta(204L, "No se encontraron asignaciones para el viaje " + vCod + ".", null);
        }
        return new ViajeChofer.MensajeRespuesta(200L, "Asignaciones obtenidas exitosamente.", asignaciones);
    }

    // Listar asignaciones por chofer
    public ViajeChofer.MensajeRespuesta listarPorChofer(Long eCod) {
        List<ViajeChofer> asignaciones = viajeChoferRepository.findByECod(eCod);
        if (asignaciones.isEmpty()) {
            return new ViajeChofer.MensajeRespuesta(204L, "No se encontraron asignaciones para el chofer " + eCod + ".", null);
        }
        return new ViajeChofer.MensajeRespuesta(200L, "Asignaciones obtenidas exitosamente.", asignaciones);
    }

    public ViajeChofer.MensajeRespuesta getViajeChoferesFiltered(ViajeChofer filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("vCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("eCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("empleado.cCodCargo", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());


            Example<ViajeChofer> example = Example.of(filtro, matcher);
            List<ViajeChofer> viajeChoferes = viajeChoferRepository.findAll(example);

            if (viajeChoferes.isEmpty()) {
                return new ViajeChofer.MensajeRespuesta(204L, "No se encontraron asignaciones.", null);
            }
            return new ViajeChofer.MensajeRespuesta(200L, "Asignaciones encontradas.", viajeChoferes);
        } catch (Exception e) {
            logger.error("Error al filtrar asignaciones: ", e);
            return new ViajeChofer.MensajeRespuesta(500L, "Error al filtrar asignaciones: " + e.getMessage(), null);
        }
    }

    // Insertar una nueva asignación con validaciones
    public ViajeChofer.MensajeRespuesta insertarViajeChofer(Integer vCod, Long eCod) {
        // Verificar si el viaje existe
        Optional<Viaje> viajeOpt = viajeRepository.findById(vCod);
        if (!viajeOpt.isPresent()) {
            return new ViajeChofer.MensajeRespuesta(400L, "El viaje con código " + vCod + " no existe.", null);
        }

        // Verificar si el empleado existe
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(eCod);
        if (!empleadoOpt.isPresent()) {
            return new ViajeChofer.MensajeRespuesta(400L, "El empleado con código " + eCod + " no existe.", null);
        }

        // Verificar si el empleado tiene el cargo de "COND" o "GUAR"
        Empleado empleado = empleadoOpt.get();
        if (!empleado.getCCodCargo().equals("COND") && !empleado.getCCodCargo().equals("GUAR")) {
            return new ViajeChofer.MensajeRespuesta(400L, "El empleado debe tener el cargo de 'chofer' (COND) o 'guarda' (GUAR).", null);
        }

        // Verificar si la asignación ya existe
        if (viajeChoferRepository.existsByVCodAndECod(vCod, eCod)) {
            return new ViajeChofer.MensajeRespuesta(409L, "La asignación ya existe para este viaje y empleado.", null);
        }

        // Crear la entidad
        ViajeChofer nuevaAsignacion = ViajeChofer.builder()
                .vCod(vCod)
                .eCod(eCod)
                .viaje(viajeOpt.get())
                .empleado(empleado)
                .build();

        // Guardar en la base de datos
        ViajeChofer saved = viajeChoferRepository.save(nuevaAsignacion);
        return new ViajeChofer.MensajeRespuesta(200L, "Asignación creada exitosamente.", Collections.singletonList(saved));
    }

    @Transactional
    public ViajeChofer.MensajeRespuesta insertarViajeChoferes(Integer vCod, List<Long> eCods) {
        try {
            // Verificar si el viaje existe
            Optional<Viaje> viajeOpt = viajeRepository.findById(vCod);
            if (!viajeOpt.isPresent()) {
                logger.warn("El viaje con código {} no existe.", vCod);
                return new ViajeChofer.MensajeRespuesta(400L, "El viaje con código " + vCod + " no existe.", null);
            }

            // Validar que la lista de eCods no esté vacía
            if (eCods == null || eCods.isEmpty()) {
                logger.warn("La lista de códigos de empleados está vacía para el viaje {}.", vCod);
                return new ViajeChofer.MensajeRespuesta(400L, "La lista de códigos de empleados no puede estar vacía.", null);
            }

            List<ViajeChofer> nuevasAsignaciones = new ArrayList<>();
            for (Long eCod : eCods) {
                // Verificar si el empleado existe
                Optional<Empleado> empleadoOpt = empleadoRepository.findById(eCod);
                if (!empleadoOpt.isPresent()) {
                    logger.warn("El empleado con código {} no existe para el viaje {}.", eCod, vCod);
                    return new ViajeChofer.MensajeRespuesta(400L, "El empleado con código " + eCod + " no existe.", null);
                }

                // Verificar si el empleado tiene el cargo de "COND" o "GUAR"
                Empleado empleado = empleadoOpt.get();
                if (!empleado.getCCodCargo().equals("COND") && !empleado.getCCodCargo().equals("GUAR")) {
                    logger.warn("El empleado {} no tiene el cargo de 'chofer' (COND) o 'guarda' (GUAR) para el viaje {}.", eCod, vCod);
                    return new ViajeChofer.MensajeRespuesta(400L,
                            "El empleado con código " + eCod + " debe tener el cargo de 'chofer' (COND) o 'guarda' (GUAR).", null);
                }

                // Verificar si la asignación ya existe
                if (viajeChoferRepository.existsByVCodAndECod(vCod, eCod)) {
                    logger.warn("La asignación para el viaje {} y empleado {} ya existe.", vCod, eCod);
                    return new ViajeChofer.MensajeRespuesta(409L,
                            "La asignación ya existe para el viaje " + vCod + " y empleado " + eCod + ".", null);
                }

                // Crear la entidad ViajeChofer
                ViajeChofer asignacion = ViajeChofer.builder()
                        .vCod(vCod)
                        .eCod(eCod)
                        .viaje(viajeOpt.get())
                        .empleado(empleado)
                        .build();

                nuevasAsignaciones.add(asignacion);
            }

            // Guardar todas las asignaciones en una sola operación
            List<ViajeChofer> savedAsignaciones = viajeChoferRepository.saveAll(nuevasAsignaciones);
            logger.info("Se asignaron {} choferes al viaje {}.", savedAsignaciones.size(), vCod);

            return new ViajeChofer.MensajeRespuesta(200L,
                    "Asignaciones creadas exitosamente para el viaje " + vCod + ".", savedAsignaciones);
        } catch (Exception e) {
            logger.error("Error al insertar asignaciones de choferes para el viaje {}: {}", vCod, e.getMessage(), e);
            return new ViajeChofer.MensajeRespuesta(500L,
                    "Error al insertar asignaciones de choferes: " + e.getMessage(), null);
        }
    }

    // Eliminar una asignación
    public ViajeChofer.MensajeRespuesta eliminarAsignacion(Integer vCod, Long eCod) {
        ViajeChofer.IdClass id = new ViajeChofer.IdClass(vCod, eCod);
        if (!viajeChoferRepository.existsById(id)) {
            return new ViajeChofer.MensajeRespuesta(404L, "La asignación con vCod=" + vCod + " y eCod=" + eCod + " no existe.", null);
        }
        viajeChoferRepository.deleteById(id);
        return new ViajeChofer.MensajeRespuesta(200L, "Asignación eliminada exitosamente.", null);
    }
}
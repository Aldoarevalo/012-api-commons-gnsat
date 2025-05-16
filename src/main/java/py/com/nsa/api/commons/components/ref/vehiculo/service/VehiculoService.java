package py.com.nsa.api.commons.components.ref.vehiculo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.ref.asientos.controller.AsientoController;
import py.com.nsa.api.commons.components.ref.asientos.model.Asiento;
import py.com.nsa.api.commons.components.ref.asientos.service.AsientoService;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import py.com.nsa.api.commons.components.ref.vehiculo.model.Vehiculo;
import py.com.nsa.api.commons.components.ref.vehiculo.repository.VehiculoRepository;
import py.com.nsa.api.commons.components.ref.parvalor.repository.ValorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository repository;
    @Autowired
    private ValorRepository valorRepository;

    @Autowired
    private AsientoService asientoService;

    private static final Logger logger = LoggerFactory.getLogger(VehiculoService.class);

    public Vehiculo.MensajeRespuesta getList() {
        try {
            List<Vehiculo> vehiculos = repository.findAll();
            if (vehiculos != null && !vehiculos.isEmpty()) {
                return new Vehiculo.MensajeRespuesta(200L, "Vehículos obtenidos exitosamente.", vehiculos);
            } else {
                return new Vehiculo.MensajeRespuesta(204L, "No se encontraron vehículos.", null);
            }
        } catch (Exception e) {
            return new Vehiculo.MensajeRespuesta(500L, "Error al obtener vehículos: " + e.getMessage(), null);
        }
    }


    @Transactional
    public Vehiculo.MensajeRespuesta guardar(Vehiculo vehiculo) {
        try {
            List<Asiento> asientos = vehiculo.getAsientos() != null ? vehiculo.getAsientos() : new ArrayList<>();

            // Establecer fecha de última modificación
            if (vehiculo.getVeUltModif() == null) {
                vehiculo.setVeUltModif(new Date());
            }

            // Validar ParValor
            ParValor parValor = valorRepository.findById(vehiculo.getParTipo())
                    .orElseThrow(() -> new IllegalArgumentException("ParValor no encontrado para parTipo: " + vehiculo.getParTipo()));

            // Guardar el vehículo primero
            Vehiculo savedVehiculo = repository.save(vehiculo);
            logger.info("Vehículo guardado con veCod: {}", savedVehiculo.getVeCod());

            // Eliminar asientos existentes
            if (savedVehiculo.getVeCod() != null) {
                Asiento.MensajeRespuesta deleteResponse = asientoService.deleteByVehiculo(savedVehiculo.getVeCod());
                if (deleteResponse.getStatus() != 200L && deleteResponse.getStatus() != 204L) {
                    throw new RuntimeException("Error al eliminar asientos existentes: " + deleteResponse.getMensaje());
                }
                logger.info("Asientos existentes procesados para veCod: {}", savedVehiculo.getVeCod());
            }

            // Guardar nuevos asientos
            List<Asiento> asientosGuardados = new ArrayList<>();
            if (!asientos.isEmpty()) {
                // Asignar el vehículo guardado a cada asiento y validar
                for (Asiento asiento : asientos) {
                    asiento.setVehiculo(savedVehiculo);
                    if (asiento.getVasTasiento() == null || asiento.getVasTasiento().getParValor() == null ||
                            asiento.getVasTubicacion() == null || asiento.getVasTubicacion().getParValor() == null ||
                            asiento.getVasPiso() == null) {
                        throw new IllegalArgumentException(
                                "Faltan campos obligatorios en un asiento: vasTasiento.parValor, vasTubicacion.parValor o vasPiso");
                    }
                }

                Asiento.MensajeRespuesta asientoRespuesta = asientoService.saveAll(asientos);
                if (asientoRespuesta.getStatus() != 200L) {
                    throw new RuntimeException("Error al guardar asientos: " + asientoRespuesta.getMensaje());
                }
                asientosGuardados = asientoRespuesta.getAsientos();
                logger.info("Asientos guardados para veCod: {}", savedVehiculo.getVeCod());
            }

            // Asignar los asientos guardados al vehículo (sin causar recursión)
            savedVehiculo.setAsientos(asientosGuardados);
            return new Vehiculo.MensajeRespuesta(200L, "Vehículo y asientos insertados exitosamente.", List.of(savedVehiculo));
        } catch (Exception e) {
            logger.error("Error al insertar vehículo: {}", e.getMessage(), e);
            throw e; // Re-lanzar para asegurar rollback y permitir que el controlador maneje la respuesta
        }
    }

    @Transactional
    public Vehiculo.MensajeRespuesta actualizar(Vehiculo vehiculo) {
        try {
            // Verificar si el vehículo existe
            Optional<Vehiculo> existingVehiculoOpt = repository.findById(vehiculo.getVeCod());
            if (!existingVehiculoOpt.isPresent()) {
                return new Vehiculo.MensajeRespuesta(404L, "Vehículo no encontrado con veCod: " + vehiculo.getVeCod(), null);
            }

            // Establecer el veCod del vehículo enviado para que se actualice el registro existente
            vehiculo.setVeCod(vehiculo.getVeCod());

            // Establecer fecha de última modificación
            vehiculo.setVeUltModif(new Date());

            // Validar ParValor
            ParValor parValor = valorRepository.findById(vehiculo.getParTipo())
                    .orElseThrow(() -> new IllegalArgumentException("ParValor no encontrado para parTipo: " + vehiculo.getParTipo()));

            // Guardar el vehículo actualizado (esto actualizará el registro existente porque veCod ya está seteado)
            Vehiculo updatedVehiculo = repository.save(vehiculo);
            logger.info("Vehículo actualizado con veCod: {}", updatedVehiculo.getVeCod());

            // Eliminar asientos existentes
            Asiento.MensajeRespuesta deleteResponse = asientoService.deleteByVehiculo(updatedVehiculo.getVeCod());
            if (deleteResponse.getStatus() != 200L && deleteResponse.getStatus() != 204L) {
                throw new RuntimeException("Error al eliminar asientos existentes: " + deleteResponse.getMensaje());
            }
            logger.info("Asientos existentes procesados para veCod: {}", updatedVehiculo.getVeCod());

            // Guardar nuevos asientos
            List<Asiento> asientos = vehiculo.getAsientos() != null ? vehiculo.getAsientos() : new ArrayList<>();
            List<Asiento> asientosGuardados = new ArrayList<>();
            if (!asientos.isEmpty()) {
                for (Asiento asiento : asientos) {
                    asiento.setVehiculo(updatedVehiculo);
                    if (asiento.getVasTasiento() == null || asiento.getVasTasiento().getParValor() == null ||
                            asiento.getVasTubicacion() == null || asiento.getVasTubicacion().getParValor() == null ||
                            asiento.getVasPiso() == null) {
                        throw new IllegalArgumentException(
                                "Faltan campos obligatorios en un asiento: vasTasiento.parValor, vasTubicacion.parValor o vasPiso");
                    }
                }

                Asiento.MensajeRespuesta asientoRespuesta = asientoService.saveAll(asientos);
                if (asientoRespuesta.getStatus() != 200L) {
                    throw new RuntimeException("Error al guardar asientos: " + asientoRespuesta.getMensaje());
                }
                asientosGuardados = asientoRespuesta.getAsientos();
                logger.info("Asientos actualizados para veCod: {}", updatedVehiculo.getVeCod());
            }

            updatedVehiculo.setAsientos(asientosGuardados);
            return new Vehiculo.MensajeRespuesta(200L, "Vehículo y asientos actualizados exitosamente.", List.of(updatedVehiculo));
        } catch (Exception e) {
            logger.error("Error al actualizar vehículo: {}", e.getMessage(), e);
            throw e;
        }
    }


    public Vehiculo.MensajeRespuesta save(Vehiculo vehiculo) {
        try {
            if (vehiculo.getVeUltModif() == null) {
                vehiculo.setVeUltModif(new Date());
            }

            ParValor parValor = valorRepository.findById(vehiculo.getParTipo())
                    .orElseThrow(() -> new IllegalArgumentException("ParValor no encontrado"));


            Vehiculo savedVehiculo = repository.save(vehiculo);
            return new Vehiculo.MensajeRespuesta(200L, "Vehículo insertado exitosamente.", List.of(savedVehiculo));
        } catch (Exception e) {
            return new Vehiculo.MensajeRespuesta(500L, "Error al insertar el vehículo: " + e.getMessage(), null);
        }
    }

    public Vehiculo.MensajeRespuesta update(Vehiculo vehiculo) {
        try {
            Vehiculo existingVehiculo = repository.findById(vehiculo.getVeCod())
                    .orElseThrow(() -> new IllegalArgumentException("Vehículo con el código no encontrado: " + vehiculo.getVeCod()));

            // Actualizar solo campos permitidos
            existingVehiculo.setParTipo(vehiculo.getParTipo());
            existingVehiculo.setVeChapa(vehiculo.getVeChapa());
            existingVehiculo.setVeEmpresa(vehiculo.getVeEmpresa());
            existingVehiculo.setVePiso(vehiculo.getVePiso());
            existingVehiculo.setVeEstado(vehiculo.getVeEstado());
            existingVehiculo.setVeNumero(vehiculo.getVeNumero());

            // Actualizar fecha de última modificación
            existingVehiculo.setVeUltModif(new Date());

            Vehiculo updatedVehiculo = repository.save(existingVehiculo);
            return new Vehiculo.MensajeRespuesta(200L, "Vehículo actualizado exitosamente.", List.of(updatedVehiculo));
        } catch (Exception e) {
            return new Vehiculo.MensajeRespuesta(500L, "Error al actualizar el vehículo: " + e.getMessage(), null);
        }
    }

    public Vehiculo.MensajeRespuesta deleteById(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return new Vehiculo.MensajeRespuesta(200L, "Vehículo eliminado exitosamente.", null);
            } else {
                return new Vehiculo.MensajeRespuesta(204L, "Vehículo no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            return new Vehiculo.MensajeRespuesta(204L, "No se puede eliminar el vehículo porque está referenciado por otros registros.", null);
        } catch (Exception e) {
            return new Vehiculo.MensajeRespuesta(500L, "Error al eliminar el vehículo: " + e.getMessage(), null);
        }
    }

}
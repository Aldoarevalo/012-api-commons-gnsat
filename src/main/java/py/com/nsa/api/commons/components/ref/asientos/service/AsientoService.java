package py.com.nsa.api.commons.components.ref.asientos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.ref.asientos.model.Asiento;
import py.com.nsa.api.commons.components.ref.asientos.repository.AsientoRepository;
import py.com.nsa.api.commons.components.ref.vehiculo.model.Vehiculo;
import py.com.nsa.api.commons.components.ref.vehiculo.repository.VehiculoRepository;
import py.com.nsa.api.commons.components.ref.vehiculo.service.VehiculoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AsientoService {

    @Autowired
    private AsientoRepository repository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    private static final Logger logger = LoggerFactory.getLogger(AsientoService.class);

    public Asiento.MensajeRespuesta getAll() {
        try {
            List<Asiento> asientos = repository.findAll();
            if (!asientos.isEmpty()) {
                return new Asiento.MensajeRespuesta(200L, "Asientos obtenidos exitosamente.", asientos);
            } else {
                return new Asiento.MensajeRespuesta(204L, "No se encontraron asientos.", null);
            }
        } catch (Exception e) {
            return new Asiento.MensajeRespuesta(500L, "Error al obtener asientos: " + e.getMessage(), null);
        }
    }

    public Asiento.MensajeRespuesta getByVehiculo(Long veCod) {
        try {
            List<Asiento> asientos = repository.findByVehiculo(veCod);
            if (!asientos.isEmpty()) {
                return new Asiento.MensajeRespuesta(200L, "Asientos encontrados para el vehículo.", asientos);
            } else {
                return new Asiento.MensajeRespuesta(204L, "No se encontraron asientos para este vehículo.", null);
            }
        } catch (Exception e) {
            return new Asiento.MensajeRespuesta(500L, "Error al buscar asientos: " + e.getMessage(), null);
        }
    }

    public Asiento.MensajeRespuesta save(Asiento asiento) {
        try {
            Asiento savedAsiento = repository.save(asiento);
            return new Asiento.MensajeRespuesta(200L, "Asiento creado exitosamente.", List.of(savedAsiento));
        } catch (Exception e) {
            return new Asiento.MensajeRespuesta(500L, "Error al guardar el asiento: " + e.getMessage(), null);
        }
    }

    @Transactional
    public Asiento.MensajeRespuesta saveAll(List<Asiento> asientos) {
        try {
            List<Asiento> savedAsientos = repository.saveAll(asientos);
            return new Asiento.MensajeRespuesta(200L, "Asientos creados exitosamente.", savedAsientos);
        } catch (Exception e) {
            return new Asiento.MensajeRespuesta(500L, "Error al guardar los asientos: " + e.getMessage(), null);
        }
    }

    public Asiento.MensajeRespuesta getAsientosFiltered(Asiento filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("vasCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("vasNroAsiento", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("vasPiso", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("vasFila", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("vasColumna", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("vehiculo.veCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("vasTasiento.parValor", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("vasTubicacion.parValor", ExampleMatcher.GenericPropertyMatchers.exact());

            Example<Asiento> example = Example.of(filtro, matcher);
            List<Asiento> asientos = repository.findAll(example);

            if (!asientos.isEmpty()) {
                return new Asiento.MensajeRespuesta(200L, "Asientos encontrados", asientos);
            } else {
                return new Asiento.MensajeRespuesta(204L, "No se encontraron asientos", null);
            }
        } catch (Exception e) {
            return new Asiento.MensajeRespuesta(500L, "Error al filtrar asientos: " + e.getMessage(), null);
        }
    }


    public Asiento.MensajeRespuesta update(Asiento asiento) {
        try {
            if (repository.existsById(asiento.getVasCod())) {
                Asiento updatedAsiento = repository.save(asiento);
                return new Asiento.MensajeRespuesta(200L, "Asiento actualizado exitosamente.", List.of(updatedAsiento));
            } else {
                return new Asiento.MensajeRespuesta(204L, "Asiento no encontrado.", null);
            }
        } catch (Exception e) {
            return new Asiento.MensajeRespuesta(500L, "Error al actualizar el asiento: " + e.getMessage(), null);
        }
    }

    public Asiento.MensajeRespuesta delete(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return new Asiento.MensajeRespuesta(200L, "Asiento eliminado exitosamente.", null);
            } else {
                return new Asiento.MensajeRespuesta(204L, "Asiento no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            return new Asiento.MensajeRespuesta(204L, "No se puede eliminar el Asiento porque está referenciado por otros registros.", null);
        } catch (Exception e) {
            return new Asiento.MensajeRespuesta(500L, "Error al eliminar el asiento: " + e.getMessage(), null);
        }
    }

    @Transactional
    public Asiento.MensajeRespuesta deleteByVehiculo(Long veCod) {
        try {
            List<Asiento> asientos = repository.findByVehiculo(veCod);
            if (asientos != null && !asientos.isEmpty()) {
                repository.deleteAll(asientos);
                return new Asiento.MensajeRespuesta(200L, "Asientos eliminados exitosamente para el vehículo con veCod: " + veCod, null);
            } else {
                return new Asiento.MensajeRespuesta(204L, "No se encontraron asientos para el vehículo con veCod: " + veCod, null);
            }
        } catch (JpaSystemException e) {
            return new Asiento.MensajeRespuesta(204L, "No se pueden eliminar los asientos porque están referenciados por otros registros.", null);
        } catch (Exception e) {
            return new Asiento.MensajeRespuesta(500L, "Error al eliminar los asientos: " + e.getMessage(), null);
        }
    }

}

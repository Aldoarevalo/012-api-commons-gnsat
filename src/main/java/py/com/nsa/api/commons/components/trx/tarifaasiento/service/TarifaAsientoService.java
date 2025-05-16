package py.com.nsa.api.commons.components.trx.tarifaasiento.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.trx.tarifaasiento.model.TarifaAsiento;
import py.com.nsa.api.commons.components.trx.tarifaasiento.model.TarifaAsientoId;
import py.com.nsa.api.commons.components.trx.tarifaasiento.repository.TarifaAsientoRepository;
import py.com.nsa.api.commons.components.trx.viaje.repository.ViajeRepository;
import py.com.nsa.api.commons.components.ref.producto.repository.ProductoRepository;
import py.com.nsa.api.commons.components.ref.parvalor.repository.ValorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TarifaAsientoService {

    private static final Logger logger = LoggerFactory.getLogger(TarifaAsientoService.class);

    @Autowired
    private TarifaAsientoRepository tarifaAsientoRepository;

    @Autowired
    private ValorRepository valorRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public TarifaAsiento.MensajeRespuesta getTarifasAll() {
        try {
            List<TarifaAsiento> tarifas = tarifaAsientoRepository.findAll();
            if (tarifas.isEmpty()) {
                return new TarifaAsiento.MensajeRespuesta(204L, "No se encontraron tarifas.", null);
            }
            return new TarifaAsiento.MensajeRespuesta(200L, "Tarifas obtenidas exitosamente.", tarifas);
        } catch (Exception e) {
            logger.error("Error al obtener tarifas: ", e);
            return new TarifaAsiento.MensajeRespuesta(500L, "Error al obtener tarifas: " + e.getMessage(), null);
        }
    }

    public TarifaAsiento.MensajeRespuesta getTarifasFiltered(TarifaAsiento filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("parAsiento", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("vehiculoCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("proCod", ExampleMatcher.GenericPropertyMatchers.exact());

            Example<TarifaAsiento> example = Example.of(filtro, matcher);
            List<TarifaAsiento> tarifas = tarifaAsientoRepository.findAll(example);

            if (tarifas.isEmpty()) {
                return new TarifaAsiento.MensajeRespuesta(204L, "No se encontraron tarifas.", null);
            }
            return new TarifaAsiento.MensajeRespuesta(200L, "Tarifas encontradas.", tarifas);
        } catch (Exception e) {
            logger.error("Error al filtrar tarifas: ", e);
            return new TarifaAsiento.MensajeRespuesta(500L, "Error al filtrar tarifas: " + e.getMessage(), null);
        }
    }

    public TarifaAsiento.MensajeRespuesta insertarTarifa(TarifaAsiento tarifa) {
        try {
            logger.debug("Intentando insertar tarifa: {}", tarifa);

            // Validar campos obligatorios
            if (tarifa.getParAsiento() == null) {
                return new TarifaAsiento.MensajeRespuesta(400L, "El tipo de asiento (parAsiento) es obligatorio.", null);
            }
            if (tarifa.getVehiculoCod() == null) {
                return new TarifaAsiento.MensajeRespuesta(400L, "El código de viaje (vehiculoCod) es obligatorio.", null);
            }
            if (tarifa.getProCod() == null) {
                return new TarifaAsiento.MensajeRespuesta(400L, "El código de producto (proCod) es obligatorio.", null);
            }

            // Validar existencia de ParValor (Asiento)
            if (!valorRepository.existsById(tarifa.getParAsiento())) {
                return new TarifaAsiento.MensajeRespuesta(404L, "No existe un valor de parámetro para el asiento: " + tarifa.getParAsiento(), null);
            }
            // Validar existencia de Viaje
            if (!viajeRepository.existsById(tarifa.getVehiculoCod())) {
                return new TarifaAsiento.MensajeRespuesta(404L, "No existe un viaje con el código: " + tarifa.getVehiculoCod(), null);
            }
            // Validar existencia de Producto
            if (!productoRepository.existsById(tarifa.getProCod())) {
                return new TarifaAsiento.MensajeRespuesta(404L, "No existe un producto con el código: " + tarifa.getProCod(), null);
            }

            // Verificar duplicados (solo parAsiento y vehiculoCod, según la restricción de la base de datos)
            if (tarifaAsientoRepository.existsByParAsientoAndVehiculoCod(tarifa.getParAsiento(), tarifa.getVehiculoCod())) {
                return new TarifaAsiento.MensajeRespuesta(409L, "Ya existe una tarifa con la misma combinación de asiento y viaje.", null);
            }

            TarifaAsiento nuevaTarifa = tarifaAsientoRepository.save(tarifa);
            logger.debug("Tarifa guardada exitosamente: {}", nuevaTarifa);

            return new TarifaAsiento.MensajeRespuesta(200L, "Tarifa creada exitosamente.", List.of(nuevaTarifa));
        } catch (Exception e) {
            logger.error("Error al insertar la tarifa: ", e);
            return new TarifaAsiento.MensajeRespuesta(500L, "Error al insertar la tarifa: " + e.getMessage(), null);
        }
    }

    public TarifaAsiento.MensajeRespuesta updateTarifa(TarifaAsiento tarifa) {
        try {
            // Validar existencia de la tarifa (solo con parAsiento y vehiculoCod)
            Optional<TarifaAsiento> existingTarifaOpt = tarifaAsientoRepository.findByParAsientoAndVehiculoCod(tarifa.getParAsiento(), tarifa.getVehiculoCod());
            if (existingTarifaOpt.isEmpty()) {
                return new TarifaAsiento.MensajeRespuesta(404L, "No se encontró la tarifa con la combinación especificada de asiento y viaje.", null);
            }

            // Validar campos obligatorios
            if (tarifa.getParAsiento() == null) {
                return new TarifaAsiento.MensajeRespuesta(400L, "El tipo de asiento (parAsiento) es obligatorio.", null);
            }
            if (tarifa.getVehiculoCod() == null) {
                return new TarifaAsiento.MensajeRespuesta(400L, "El código de viaje (vehiculoCod) es obligatorio.", null);
            }
            if (tarifa.getProCod() == null) {
                return new TarifaAsiento.MensajeRespuesta(400L, "El código de producto (proCod) es obligatorio.", null);
            }

            // Validar existencia de ParValor (Asiento)
            if (!valorRepository.existsById(tarifa.getParAsiento())) {
                return new TarifaAsiento.MensajeRespuesta(404L, "No existe un valor de parámetro para el asiento: " + tarifa.getParAsiento(), null);
            }
            // Validar existencia de Viaje
            if (!viajeRepository.existsById(tarifa.getVehiculoCod())) {
                return new TarifaAsiento.MensajeRespuesta(404L, "No existe un viaje con el código: " + tarifa.getVehiculoCod(), null);
            }
            // Validar existencia de Producto
            if (!productoRepository.existsById(tarifa.getProCod())) {
                return new TarifaAsiento.MensajeRespuesta(404L, "No existe un producto con el código: " + tarifa.getProCod(), null);
            }

            // Obtener el registro existente
            TarifaAsiento existingTarifa = existingTarifaOpt.get();
            // Actualizar el campo proCod
            existingTarifa.setProCod(tarifa.getProCod());

            // Guardar el registro actualizado
            TarifaAsiento tarifaActualizada = tarifaAsientoRepository.save(existingTarifa);
            return new TarifaAsiento.MensajeRespuesta(200L, "Tarifa actualizada exitosamente.", List.of(tarifaActualizada));
        } catch (Exception e) {
            logger.error("Error al actualizar la tarifa: ", e);
            return new TarifaAsiento.MensajeRespuesta(500L, "Error al actualizar la tarifa: " + e.getMessage(), null);
        }
    }

    public TarifaAsiento.MensajeRespuesta deleteTarifa(String parAsiento, Integer vehiculoCod, String proCod) {
        try {
            // Verificar existencia del registro
            Optional<TarifaAsiento> tarifaOpt = tarifaAsientoRepository.findByParAsientoAndVehiculoCod(parAsiento, vehiculoCod);
            if (tarifaOpt.isEmpty()) {
                return new TarifaAsiento.MensajeRespuesta(404L, "No se encontró la tarifa con la combinación especificada.", null);
            }

            // Verificar que el proCod coincida
            TarifaAsiento tarifa = tarifaOpt.get();
            if (!tarifa.getProCod().equals(proCod)) {
                return new TarifaAsiento.MensajeRespuesta(404L, "No se encontró la tarifa con la combinación especificada.", null);
            }

            try {
                TarifaAsientoId id = new TarifaAsientoId(parAsiento, vehiculoCod);
                tarifaAsientoRepository.deleteById(id);
                return new TarifaAsiento.MensajeRespuesta(200L, "Tarifa eliminada exitosamente.", null);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                return new TarifaAsiento.MensajeRespuesta(409L, "No se puede eliminar la tarifa ya que está siendo utilizada en otros registros.", null);
            }
        } catch (Exception e) {
            logger.error("Error al eliminar la tarifa: ", e);
            return new TarifaAsiento.MensajeRespuesta(500L, "Error al eliminar la tarifa: " + e.getMessage(), null);
        }
    }
}
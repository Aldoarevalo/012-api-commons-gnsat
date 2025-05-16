package py.com.nsa.api.commons.components.trx.movimientoproducto.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.trx.movimientoproducto.model.MovimientoProducto;
import py.com.nsa.api.commons.components.trx.movimientoproducto.repository.MovimientoProductoRepository;
import py.com.nsa.api.commons.components.ref.producto.repository.ProductoRepository;
import py.com.nsa.api.commons.components.ref.parvalor.repository.ValorRepository;
import py.com.nsa.api.commons.components.ref.almacen.repository.AlmacenRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoProductoService {

    private static final Logger logger = LoggerFactory.getLogger(MovimientoProductoService.class);

    @Autowired
    private MovimientoProductoRepository movimientoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ValorRepository valorRepository;

    @Autowired
    private AlmacenRepository almacenRepository;

    /**
     * Obtiene todos los movimientos de productos
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MovimientoProducto.MensajeRespuesta getMovimientosAll() {
        try {
            List<MovimientoProducto> movimientos = movimientoRepository.findAll();
            if (movimientos.isEmpty()) {
                return new MovimientoProducto.MensajeRespuesta(204L, "No se encontraron movimientos de productos.", null);
            }
            return new MovimientoProducto.MensajeRespuesta(200L, "Movimientos de productos obtenidos exitosamente.", movimientos);
        } catch (Exception e) {
            logger.error("Error al obtener movimientos de productos: ", e);
            return new MovimientoProducto.MensajeRespuesta(500L, "Error al obtener movimientos de productos: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene un movimiento de producto por su código
     * @param mpCod Código del movimiento
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MovimientoProducto.MensajeRespuesta getMovimientoByMpCod(Long mpCod) {
        try {
            Optional<MovimientoProducto> movimiento = movimientoRepository.findByMpCod(mpCod);

            if (movimiento.isEmpty()) {
                return new MovimientoProducto.MensajeRespuesta(204L, "No se encontró el movimiento de producto con el código: " + mpCod, null);
            }

            return new MovimientoProducto.MensajeRespuesta(200L, "Movimiento de producto encontrado.", List.of(movimiento.get()));
        } catch (Exception e) {
            logger.error("Error al obtener el movimiento de producto: ", e);
            return new MovimientoProducto.MensajeRespuesta(500L, "Error al obtener el movimiento de producto: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene movimientos de productos por código de producto
     * @param proCod Código del producto
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MovimientoProducto.MensajeRespuesta getMovimientosByProCod(String proCod) {
        try {
            List<MovimientoProducto> movimientos = movimientoRepository.findByProCod(proCod);
            if (movimientos.isEmpty()) {
                return new MovimientoProducto.MensajeRespuesta(204L, "No se encontraron movimientos para el producto con código: " + proCod, null);
            }
            return new MovimientoProducto.MensajeRespuesta(200L, "Movimientos encontrados para el producto.", movimientos);
        } catch (Exception e) {
            logger.error("Error al obtener movimientos por producto: ", e);
            return new MovimientoProducto.MensajeRespuesta(500L, "Error al obtener movimientos por producto: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene movimientos de productos por código de almacén
     * @param alCod Código del almacén
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MovimientoProducto.MensajeRespuesta getMovimientosByAlCod(Long alCod) {
        try {
            List<MovimientoProducto> movimientos = movimientoRepository.findByAlCod(alCod);
            if (movimientos.isEmpty()) {
                return new MovimientoProducto.MensajeRespuesta(204L, "No se encontraron movimientos para el almacén con código: " + alCod, null);
            }
            return new MovimientoProducto.MensajeRespuesta(200L, "Movimientos encontrados para el almacén.", movimientos);
        } catch (Exception e) {
            logger.error("Error al obtener movimientos por almacén: ", e);
            return new MovimientoProducto.MensajeRespuesta(500L, "Error al obtener movimientos por almacén: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene movimientos de productos por tipo de movimiento
     * @param parTipo Tipo de movimiento
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MovimientoProducto.MensajeRespuesta getMovimientosByParTipo(String parTipo) {
        try {
            List<MovimientoProducto> movimientos = movimientoRepository.findByParTipo(parTipo);
            if (movimientos.isEmpty()) {
                return new MovimientoProducto.MensajeRespuesta(204L, "No se encontraron movimientos del tipo: " + parTipo, null);
            }
            return new MovimientoProducto.MensajeRespuesta(200L, "Movimientos encontrados por tipo.", movimientos);
        } catch (Exception e) {
            logger.error("Error al obtener movimientos por tipo: ", e);
            return new MovimientoProducto.MensajeRespuesta(500L, "Error al obtener movimientos por tipo: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene movimientos de productos filtrados por rango de fechas
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MovimientoProducto.MensajeRespuesta getMovimientosByFechas(Date fechaInicio, Date fechaFin) {
        try {
            List<MovimientoProducto> movimientos = movimientoRepository.findByMpFechaBetween(fechaInicio, fechaFin);
            if (movimientos.isEmpty()) {
                return new MovimientoProducto.MensajeRespuesta(204L, "No se encontraron movimientos en el rango de fechas especificado.", null);
            }
            return new MovimientoProducto.MensajeRespuesta(200L, "Movimientos encontrados por rango de fechas.", movimientos);
        } catch (Exception e) {
            logger.error("Error al obtener movimientos por fechas: ", e);
            return new MovimientoProducto.MensajeRespuesta(500L, "Error al obtener movimientos por fechas: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene movimientos de productos filtrados por criterios específicos
     * @param filtro Objeto MovimientoProducto con los criterios de filtrado
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MovimientoProducto.MensajeRespuesta getMovimientosFiltered(MovimientoProducto filtro) {
        try {
            // Configurar el ExampleMatcher para filtros
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("mpCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("proCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("parTipo", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("alCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("mpDoc", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("mpTdoc", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("mpDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            // Crear ejemplo con el filtro y matcher
            Example<MovimientoProducto> example = Example.of(filtro, matcher);

            // Realizar la consulta usando el filtro como ejemplo
            List<MovimientoProducto> movimientos = movimientoRepository.findAll(example);

            if (!movimientos.isEmpty()) {
                return new MovimientoProducto.MensajeRespuesta(200L, "Movimientos encontrados", movimientos);
            } else {
                return new MovimientoProducto.MensajeRespuesta(204L, "No se encontraron movimientos", null);
            }
        } catch (Exception e) {
            logger.error("Error al filtrar movimientos: ", e);
            return new MovimientoProducto.MensajeRespuesta(500L, "Error al filtrar movimientos: " + e.getMessage(), null);
        }
    }

    public MovimientoProducto.MensajeRespuesta insertMovimiento(MovimientoProducto movimiento) {
        try {
            logger.debug("Intentando insertar movimiento de producto: {}", movimiento);

            // Si no se proporciona una fecha, establecer la fecha actual
            if (movimiento.getMpFecha() == null) {
                movimiento.setMpFecha(new Date());
            }

            // Guardar movimiento directamente sin validaciones
            MovimientoProducto nuevoMovimiento = movimientoRepository.save(movimiento);
            logger.debug("Movimiento guardado exitosamente: {}", nuevoMovimiento);

            return new MovimientoProducto.MensajeRespuesta(200L, "Movimiento de producto creado exitosamente.", List.of(nuevoMovimiento));
        } catch (Exception e) {
            logger.error("Error al insertar el movimiento de producto: ", e);
            return new MovimientoProducto.MensajeRespuesta(500L, "Error al insertar el movimiento de producto: " + e.getMessage(), null);
        }
    }

    public MovimientoProducto.MensajeRespuesta updateMovimiento(MovimientoProducto movimiento) {
        try {
            // Validar si el movimiento existe
            if (movimiento.getMpCod() == null) {
                return new MovimientoProducto.MensajeRespuesta(400L, "El código de movimiento no puede ser nulo.", null);
            }

            if (!movimientoRepository.existsById(movimiento.getMpCod())) {
                return new MovimientoProducto.MensajeRespuesta(404L, "Movimiento de producto no encontrado con el código especificado.", null);
            }

            // Verificar que el producto exista
            if (!productoRepository.existsByProCodIgnoreCase(movimiento.getProCod())) {
                return new MovimientoProducto.MensajeRespuesta(400L, "El producto con código " + movimiento.getProCod() + " no existe.", null);
            }

            // Verificar que el tipo de movimiento exista
            if (!valorRepository.existsByParValorIgnoreCase(movimiento.getParTipo())) {
                return new MovimientoProducto.MensajeRespuesta(400L, "El tipo de movimiento con código " + movimiento.getParTipo() + " no existe.", null);
            }

            // Verificar que el tipo de documento exista (si mpTdoc no es nulo)
            if (movimiento.getMpTdoc() != null && !valorRepository.existsByParValorIgnoreCase(movimiento.getMpTdoc())) {
                return new MovimientoProducto.MensajeRespuesta(400L, "El tipo de documento con código " + movimiento.getMpTdoc() + " no existe.", null);
            }

            // Verificar que el almacén exista
            if (!almacenRepository.existsById(movimiento.getAlCod())) {
                return new MovimientoProducto.MensajeRespuesta(400L, "El almacén con código " + movimiento.getAlCod() + " no existe.", null);
            }

            // Guardar los cambios
            MovimientoProducto movimientoActualizado = movimientoRepository.save(movimiento);
            return new MovimientoProducto.MensajeRespuesta(200L, "Movimiento de producto actualizado exitosamente.", List.of(movimientoActualizado));
        } catch (Exception e) {
            logger.error("Error al actualizar el movimiento de producto: ", e);
            return new MovimientoProducto.MensajeRespuesta(500L, "Error al actualizar el movimiento de producto: " + e.getMessage(), null);
        }
    }

    /**
     * Elimina un movimiento de producto por su código
     * @param mpCod Código del movimiento a eliminar
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MovimientoProducto.MensajeRespuesta deleteMovimiento(Long mpCod) {
        try {
            // Verificar si el movimiento existe
            if (!movimientoRepository.existsById(mpCod)) {
                return new MovimientoProducto.MensajeRespuesta(404L, "No se encontró el movimiento de producto con el código: " + mpCod, null);
            }

            try {
                // Intentar eliminar, si falla por restricciones de clave foránea, capturar la excepción
                movimientoRepository.deleteById(mpCod);
                return new MovimientoProducto.MensajeRespuesta(200L, "Movimiento de producto eliminado exitosamente.", null);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                // Capturar error de clave foránea
                return new MovimientoProducto.MensajeRespuesta(409L, "No se puede eliminar el movimiento de producto ya que está siendo utilizado en otros registros.", null);
            }
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar el movimiento de producto: ", e);
            return new MovimientoProducto.MensajeRespuesta(500L, "Error inesperado al eliminar el movimiento de producto: " + e.getMessage(), null);
        }
    }
}
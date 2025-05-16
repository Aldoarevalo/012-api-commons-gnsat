package py.com.nsa.api.commons.components.trx.transferenciaproddet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.trx.transferenciaproddet.model.TransferenciaProductoDetalle;
import py.com.nsa.api.commons.components.trx.transferenciaproddet.model.TransferenciaProductoDetalleId;
import py.com.nsa.api.commons.components.trx.transferenciaproddet.repository.TransferenciaProductoDetalleRepository;
import py.com.nsa.api.commons.components.ref.producto.repository.ProductoRepository;
import py.com.nsa.api.commons.components.ref.parvalor.repository.ValorRepository;
import py.com.nsa.api.commons.components.trx.transferenciaproducto.model.TransferenciaProducto;
import py.com.nsa.api.commons.components.trx.transferenciaproducto.repository.TransferenciaProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransferenciaProductoDetalleService {

    private static final Logger logger = LoggerFactory.getLogger(TransferenciaProductoDetalleService.class);

    @Autowired
    private TransferenciaProductoDetalleRepository detalleRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ValorRepository valorRepository;

    @Autowired
    private TransferenciaProductoRepository transferenciaRepository;

    /**
     * Obtiene todos los detalles de transferencia de productos
     * @return MensajeRespuesta con el resultado de la operación
     */
    public TransferenciaProductoDetalle.MensajeRespuesta getDetallesAll() {
        try {
            List<TransferenciaProductoDetalle> detalles = detalleRepository.findAll();
            if (detalles.isEmpty()) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(204L, "No se encontraron detalles de transferencia de productos.", null);
            }
            return new TransferenciaProductoDetalle.MensajeRespuesta(200L, "Detalles de transferencia obtenidos exitosamente.", detalles);
        } catch (Exception e) {
            logger.error("Error al obtener detalles de transferencia: ", e);
            return new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al obtener detalles de transferencia: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene un detalle de transferencia específico
     * @param trfpCod Código de la transferencia
     * @param trfpdLinea Número de línea del detalle
     * @return MensajeRespuesta con el resultado de la operación
     */
    public TransferenciaProductoDetalle.MensajeRespuesta getDetalleById(Long trfpCod, Integer trfpdLinea) {
        try {
            Optional<TransferenciaProductoDetalle> detalle = detalleRepository.findByTrfpCodAndTrfpdLinea(trfpCod, trfpdLinea);
            if (detalle.isEmpty()) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(204L, "No se encontró el detalle de transferencia con código: " + trfpCod + " y línea: " + trfpdLinea, null);
            }
            return new TransferenciaProductoDetalle.MensajeRespuesta(200L, "Detalle de transferencia encontrado.", List.of(detalle.get()));
        } catch (Exception e) {
            logger.error("Error al obtener el detalle de transferencia: ", e);
            return new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al obtener el detalle de transferencia: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene detalles de transferencia por código de transferencia
     * @param trfpCod Código de la transferencia
     * @return MensajeRespuesta con el resultado de la operación
     */
    public TransferenciaProductoDetalle.MensajeRespuesta getDetallesByTransferencia(Long trfpCod) {
        try {
            List<TransferenciaProductoDetalle> detalles = detalleRepository.findByTrfpCod(trfpCod);
            if (detalles.isEmpty()) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(204L, "No se encontraron detalles para la transferencia con código: " + trfpCod, null);
            }
            return new TransferenciaProductoDetalle.MensajeRespuesta(200L, "Detalles de transferencia encontrados.", detalles);
        } catch (Exception e) {
            logger.error("Error al obtener detalles por transferencia: ", e);
            return new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al obtener detalles por transferencia: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene detalles de transferencia que tienen como referencia una línea específica
     * @param trfpCod Código de la transferencia
     * @param trfpdRefLinea Número de línea de referencia
     * @return MensajeRespuesta con el resultado de la operación
     */
    public TransferenciaProductoDetalle.MensajeRespuesta getDetallesByLineaReferencia(Long trfpCod, Integer trfpdRefLinea) {
        try {
            List<TransferenciaProductoDetalle> detalles = detalleRepository.findByTrfpCodAndTrfpdRefLinea(trfpCod, trfpdRefLinea);
            if (detalles.isEmpty()) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(204L, "No se encontraron detalles con referencia a la línea: " + trfpdRefLinea + " para la transferencia con código: " + trfpCod, null);
            }
            return new TransferenciaProductoDetalle.MensajeRespuesta(200L, "Detalles de transferencia encontrados por referencia.", detalles);
        } catch (Exception e) {
            logger.error("Error al obtener detalles por línea de referencia: ", e);
            return new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al obtener detalles por línea de referencia: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene detalles de transferencia filtrados
     * @param filtro Objeto TransferenciaProductoDetalle con los criterios de filtrado
     * @return MensajeRespuesta con el resultado de la operación
     */
    public TransferenciaProductoDetalle.MensajeRespuesta getDetallesFiltered(TransferenciaProductoDetalle filtro) {
        try {
            // Configurar el ExampleMatcher para filtros
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("trfpCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("trfpdLinea", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("proCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("trfpdUm", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("trfpdRefLinea", ExampleMatcher.GenericPropertyMatchers.exact());

            // Crear ejemplo con el filtro y matcher
            Example<TransferenciaProductoDetalle> example = Example.of(filtro, matcher);

            // Realizar la consulta usando el filtro como ejemplo
            List<TransferenciaProductoDetalle> detalles = detalleRepository.findAll(example);

            if (!detalles.isEmpty()) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(200L, "Detalles de transferencia encontrados", detalles);
            } else {
                return new TransferenciaProductoDetalle.MensajeRespuesta(204L, "No se encontraron detalles de transferencia", null);
            }
        } catch (Exception e) {
            logger.error("Error al filtrar detalles de transferencia: ", e);
            return new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al filtrar detalles de transferencia: " + e.getMessage(), null);
        }
    }

    /**
     * Inserta un nuevo detalle de transferencia de producto
     * @param detalle Detalle de transferencia a insertar
     * @return MensajeRespuesta con el resultado de la operación
     */
    @Transactional
    public TransferenciaProductoDetalle.MensajeRespuesta insertarDetalle(TransferenciaProductoDetalle detalle) {
        try {
            logger.debug("Intentando insertar detalle de transferencia: {}", detalle);

            // Verificar que la transferencia exista
            if (!transferenciaRepository.existsById(detalle.getTrfpCod())) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(400L, "La transferencia con código " + detalle.getTrfpCod() + " no existe.", null);
            }

            // Verificar que el producto exista
            if (!productoRepository.existsByProCodIgnoreCase(detalle.getProCod())) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(400L, "El producto con código " + detalle.getProCod() + " no existe.", null);
            }

            // Verificar que la unidad de medida exista
            if (!valorRepository.existsByParValorIgnoreCase(detalle.getTrfpdUm())) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(400L, "La unidad de medida con código " + detalle.getTrfpdUm() + " no existe.", null);
            }

            // Verificar la línea de referencia si existe
            if (detalle.getTrfpdRefLinea() != null) {
                // Obtener la transferencia actual para verificar si es una recepción
                Optional<TransferenciaProducto> transferencia = transferenciaRepository.findById(detalle.getTrfpCod());

                if (transferencia.isPresent() && transferencia.get().getTrfpRefEnv() != null) {
                    // Es una recepción, buscar la línea en la transferencia de origen
                    Long trfpCodOrigen = transferencia.get().getTrfpRefEnv();
                    if (!detalleRepository.existsByTrfpCodAndTrfpdLinea(trfpCodOrigen, detalle.getTrfpdRefLinea())) {
                        return new TransferenciaProductoDetalle.MensajeRespuesta(400L,
                                "La línea de referencia " + detalle.getTrfpdRefLinea() +
                                        " no existe en la transferencia de origen " + trfpCodOrigen, null);
                    }
                } else {
                    // No es una recepción o no tiene referencia, verificar en la misma transferencia
                    if (!detalleRepository.existsByTrfpCodAndTrfpdLinea(detalle.getTrfpCod(), detalle.getTrfpdRefLinea())) {
                        return new TransferenciaProductoDetalle.MensajeRespuesta(400L,
                                "La línea de referencia " + detalle.getTrfpdRefLinea() +
                                        " no existe para la transferencia " + detalle.getTrfpCod(), null);
                    }
                }
            }

            // Si no se proporciona un número de línea, generar uno automáticamente
            if (detalle.getTrfpdLinea() == null) {
                Integer nextLineNumber = detalleRepository.getNextLineNumber(detalle.getTrfpCod());
                detalle.setTrfpdLinea(nextLineNumber);
            } else {
                // Verificar si ya existe un detalle con la misma transferencia y línea
                if (detalleRepository.existsByTrfpCodAndTrfpdLinea(detalle.getTrfpCod(), detalle.getTrfpdLinea())) {
                    return new TransferenciaProductoDetalle.MensajeRespuesta(409L, "Ya existe un detalle de transferencia con el código " + detalle.getTrfpCod() + " y línea " + detalle.getTrfpdLinea(), null);
                }
            }

            // Guardar detalle de transferencia
            TransferenciaProductoDetalle nuevoDetalle = detalleRepository.save(detalle);
            logger.debug("Detalle de transferencia guardado exitosamente: {}", nuevoDetalle);

            return new TransferenciaProductoDetalle.MensajeRespuesta(200L, "Detalle de transferencia creado exitosamente.", List.of(nuevoDetalle));
        } catch (Exception e) {
            logger.error("Error al insertar el detalle de transferencia: ", e);
            return new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al insertar el detalle de transferencia: " + e.getMessage(), null);
        }
    }

    /**
     * Actualiza un detalle de transferencia existente
     * @param detalle Detalle de transferencia con datos actualizados
     * @return MensajeRespuesta con el resultado de la operación
     */
    @Transactional
    public TransferenciaProductoDetalle.MensajeRespuesta updateDetalle(TransferenciaProductoDetalle detalle) {
        try {
            // Validar si el detalle existe
            if (detalle.getTrfpCod() == null || detalle.getTrfpdLinea() == null) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(400L, "El código de transferencia y el número de línea no pueden ser nulos.", null);
            }

            TransferenciaProductoDetalleId id = new TransferenciaProductoDetalleId(detalle.getTrfpCod(), detalle.getTrfpdLinea());
            if (!detalleRepository.existsById(id)) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(404L, "Detalle de transferencia no encontrado con el código y línea especificados.", null);
            }

            // Verificar que la transferencia exista
            if (!transferenciaRepository.existsById(detalle.getTrfpCod())) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(400L, "La transferencia con código " + detalle.getTrfpCod() + " no existe.", null);
            }

            // Verificar que el producto exista
            if (!productoRepository.existsByProCodIgnoreCase(detalle.getProCod())) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(400L, "El producto con código " + detalle.getProCod() + " no existe.", null);
            }

            // Verificar que la unidad de medida exista
            if (!valorRepository.existsByParValorIgnoreCase(detalle.getTrfpdUm())) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(400L, "La unidad de medida con código " + detalle.getTrfpdUm() + " no existe.", null);
            }

            // Verificar la línea de referencia si existe
            if (detalle.getTrfpdRefLinea() != null) {

                if (!detalleRepository.existsByTrfpCodAndTrfpdLinea(detalle.getTrfpCod(), detalle.getTrfpdRefLinea())) {
                    return new TransferenciaProductoDetalle.MensajeRespuesta(400L, "La línea de referencia " + detalle.getTrfpdRefLinea() + " no existe para la transferencia " + detalle.getTrfpCod(), null);
                }
            }

            // Guardar los cambios
            TransferenciaProductoDetalle detalleActualizado = detalleRepository.save(detalle);
            return new TransferenciaProductoDetalle.MensajeRespuesta(200L, "Detalle de transferencia actualizado exitosamente.", List.of(detalleActualizado));
        } catch (Exception e) {
            logger.error("Error al actualizar el detalle de transferencia: ", e);
            return new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al actualizar el detalle de transferencia: " + e.getMessage(), null);
        }
    }

    /**
     * Elimina un detalle de transferencia
     * @param trfpCod Código de la transferencia
     * @param trfpdLinea Número de línea del detalle
     * @return MensajeRespuesta con el resultado de la operación
     */
    @Transactional
    public TransferenciaProductoDetalle.MensajeRespuesta deleteDetalle(Long trfpCod, Integer trfpdLinea) {
        try {
            // Verificar si el detalle existe
            TransferenciaProductoDetalleId id = new TransferenciaProductoDetalleId(trfpCod, trfpdLinea);
            if (!detalleRepository.existsById(id)) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(404L, "No se encontró el detalle de transferencia con código: " + trfpCod + " y línea: " + trfpdLinea, null);
            }

            // Verificar si hay otros detalles que hacen referencia a este
            List<TransferenciaProductoDetalle> referencias = detalleRepository.findByTrfpCodAndTrfpdRefLinea(trfpCod, trfpdLinea);
            if (!referencias.isEmpty()) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(409L, "No se puede eliminar el detalle ya que está siendo referenciado por otros detalles de transferencia.", null);
            }

            try {
                // Intentar eliminar, si falla por restricciones de clave foránea, capturar la excepción
                detalleRepository.deleteById(id);
                return new TransferenciaProductoDetalle.MensajeRespuesta(200L, "Detalle de transferencia eliminado exitosamente.", null);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                // Capturar error de clave foránea
                return new TransferenciaProductoDetalle.MensajeRespuesta(409L, "No se puede eliminar el detalle de transferencia ya que está siendo utilizado en otros registros.", null);
            }
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar el detalle de transferencia: ", e);
            return new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error inesperado al eliminar el detalle de transferencia: " + e.getMessage(), null);
        }
    }

    /**
     * Actualiza la cantidad recibida de un detalle de transferencia
     * @param trfpCod Código de la transferencia
     * @param trfpdLinea Número de línea del detalle
     * @param cantidadRecibida Nueva cantidad recibida
     * @return MensajeRespuesta con el resultado de la operación
     */
    @Transactional
    public TransferenciaProductoDetalle.MensajeRespuesta actualizarCantidadRecibida(Long trfpCod, Integer trfpdLinea, Integer cantidadRecibida) {
        try {
            // Verificar si el detalle existe
            Optional<TransferenciaProductoDetalle> optDetalle = detalleRepository.findByTrfpCodAndTrfpdLinea(trfpCod, trfpdLinea);
            if (optDetalle.isEmpty()) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(404L, "No se encontró el detalle de transferencia con código: " + trfpCod + " y línea: " + trfpdLinea, null);
            }

            // Validar la cantidad recibida
            TransferenciaProductoDetalle detalle = optDetalle.get();
            if (cantidadRecibida == null || cantidadRecibida < 0) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(400L, "La cantidad recibida no puede ser nula ni negativa.", null);
            }

            if (cantidadRecibida > detalle.getTrfpdCantEnv()) {
                return new TransferenciaProductoDetalle.MensajeRespuesta(400L, "La cantidad recibida no puede ser mayor que la cantidad enviada.", null);
            }

            // Actualizar la cantidad recibida
            detalle.setTrfpdCantRec(cantidadRecibida);
            TransferenciaProductoDetalle detalleActualizado = detalleRepository.save(detalle);


            return new TransferenciaProductoDetalle.MensajeRespuesta(200L, "Cantidad recibida actualizada exitosamente.", List.of(detalleActualizado));
        } catch (Exception e) {
            logger.error("Error al actualizar la cantidad recibida: ", e);
            return new TransferenciaProductoDetalle.MensajeRespuesta(500L, "Error al actualizar la cantidad recibida: " + e.getMessage(), null);
        }
    }
}
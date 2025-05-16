package py.com.nsa.api.commons.components.trx.transferenciaproducto.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.trx.transferenciaproddet.repository.TransferenciaProductoDetalleRepository;
import py.com.nsa.api.commons.components.trx.transferenciaproducto.model.TransferenciaProducto;
import py.com.nsa.api.commons.components.trx.transferenciaproducto.repository.TransferenciaProductoRepository;
import py.com.nsa.api.commons.components.ref.almacen.repository.AlmacenRepository;
import py.com.nsa.api.commons.components.ref.parvalor.repository.ValorRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransferenciaProductoService {

    private static final Logger logger = LoggerFactory.getLogger(TransferenciaProductoService.class);

    @Autowired
    private TransferenciaProductoRepository transferenciaRepository;

    @Autowired
    private AlmacenRepository almacenRepository;

    @Autowired
    private ValorRepository valorRepository;

    @Autowired
    private TransferenciaProductoDetalleRepository detalleRepository;

    /**
     * Obtiene todas las transferencias de productos
     * @return MensajeRespuesta con el resultado de la operación
     */
    public TransferenciaProducto.MensajeRespuesta getTransferenciasAll() {
        try {
            List<TransferenciaProducto> transferencias = transferenciaRepository.findAll();
            if (transferencias.isEmpty()) {
                return new TransferenciaProducto.MensajeRespuesta(204L, "No se encontraron transferencias de productos.", null);
            }
            return new TransferenciaProducto.MensajeRespuesta(200L, "Transferencias de productos obtenidas exitosamente.", transferencias);
        } catch (Exception e) {
            logger.error("Error al obtener transferencias de productos: ", e);
            return new TransferenciaProducto.MensajeRespuesta(500L, "Error al obtener transferencias de productos: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene una transferencia de producto por su código
     * @param trfpCod Código de la transferencia
     * @return MensajeRespuesta con el resultado de la operación
     */
    public TransferenciaProducto.MensajeRespuesta getTransferenciaByTrfpCod(Long trfpCod) {
        try {
            Optional<TransferenciaProducto> transferencia = transferenciaRepository.findByTrfpCod(trfpCod);

            if (transferencia.isEmpty()) {
                return new TransferenciaProducto.MensajeRespuesta(204L, "No se encontró la transferencia de producto con el código: " + trfpCod, null);
            }

            return new TransferenciaProducto.MensajeRespuesta(200L, "Transferencia de producto encontrada.", List.of(transferencia.get()));
        } catch (Exception e) {
            logger.error("Error al obtener la transferencia de producto: ", e);
            return new TransferenciaProducto.MensajeRespuesta(500L, "Error al obtener la transferencia de producto: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene transferencias de productos filtradas por criterios específicos
     * @param filtro Objeto TransferenciaProducto con los criterios de filtrado
     * @return MensajeRespuesta con el resultado de la operación
     */
    public TransferenciaProducto.MensajeRespuesta getTransferenciasFiltered(TransferenciaProducto filtro) {
        try {
            // Configurar el ExampleMatcher para filtros
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("trfpCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("trfpTipo", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("trfpOrigen", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("trfpDestino", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("trfpEstado", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("trfpRefEnv", ExampleMatcher.GenericPropertyMatchers.exact());

            // Crear ejemplo con el filtro y matcher
            Example<TransferenciaProducto> example = Example.of(filtro, matcher);

            // Realizar la consulta usando el filtro como ejemplo
            List<TransferenciaProducto> transferencias = transferenciaRepository.findAll(example);

            if (!transferencias.isEmpty()) {
                return new TransferenciaProducto.MensajeRespuesta(200L, "Transferencias encontradas", transferencias);
            } else {
                return new TransferenciaProducto.MensajeRespuesta(204L, "No se encontraron transferencias", null);
            }
        } catch (Exception e) {
            logger.error("Error al filtrar transferencias: ", e);
            return new TransferenciaProducto.MensajeRespuesta(500L, "Error al filtrar transferencias: " + e.getMessage(), null);
        }
    }

    /**
     * Inserta una nueva transferencia de producto
     * @param transferencia Transferencia de producto a insertar
     * @return MensajeRespuesta con el resultado de la operación
     */
    public TransferenciaProducto.MensajeRespuesta insertarTransferencia(TransferenciaProducto transferencia) {
        try {
            logger.debug("Intentando insertar transferencia de producto: {}", transferencia);

            // Verificar que el tipo de transferencia exista
            if (!valorRepository.existsByParValorIgnoreCase(transferencia.getTrfpTipo())) {
                return new TransferenciaProducto.MensajeRespuesta(400L, "El tipo de transferencia con código " + transferencia.getTrfpTipo() + " no existe.", null);
            }

            // Verificar que el almacén de origen exista
            if (!almacenRepository.existsById(transferencia.getTrfpOrigen())) {
                return new TransferenciaProducto.MensajeRespuesta(400L, "El almacén de origen con código " + transferencia.getTrfpOrigen() + " no existe.", null);
            }

            // Verificar que el almacén de destino exista
            if (!almacenRepository.existsById(transferencia.getTrfpDestino())) {
                return new TransferenciaProducto.MensajeRespuesta(400L, "El almacén de destino con código " + transferencia.getTrfpDestino() + " no existe.", null);
            }

            // Verificar que origen y destino no sean iguales
            if (transferencia.getTrfpOrigen().equals(transferencia.getTrfpDestino())) {
                return new TransferenciaProducto.MensajeRespuesta(400L, "El almacén de origen no puede ser igual al almacén de destino.", null);
            }

            // Verificar la referencia de envío si existe
            if (transferencia.getTrfpRefEnv() != null) {
                if (!transferenciaRepository.existsById(transferencia.getTrfpRefEnv())) {
                    return new TransferenciaProducto.MensajeRespuesta(400L, "La referencia de envío con código " + transferencia.getTrfpRefEnv() + " no existe.", null);
                }
            }

            // Si no se proporciona una fecha, establecer la fecha actual
            if (transferencia.getTrfpFecha() == null) {
                transferencia.setTrfpFecha(new Date());
            }

            // Si no se proporciona un estado, establecer el estado por defecto según el tipo
            if (transferencia.getTrfpEstado() == null) {
                transferencia.setTrfpEstado("E"); // Estado por defecto: Enviado
            }

            // Guardar transferencia
            TransferenciaProducto nuevaTransferencia = transferenciaRepository.save(transferencia);
            logger.debug("Transferencia guardada exitosamente: {}", nuevaTransferencia);

            return new TransferenciaProducto.MensajeRespuesta(200L, "Transferencia de producto creada exitosamente.", List.of(nuevaTransferencia));
        } catch (Exception e) {
            logger.error("Error al insertar la transferencia de producto: ", e);
            return new TransferenciaProducto.MensajeRespuesta(500L, "Error al insertar la transferencia de producto: " + e.getMessage(), null);
        }
    }

    /**
     * Actualiza una transferencia de producto existente
     * @param transferencia Transferencia de producto con datos actualizados
     * @return MensajeRespuesta con el resultado de la operación
     */
    public TransferenciaProducto.MensajeRespuesta updateTransferencia(TransferenciaProducto transferencia) {
        try {
            // Validar si la transferencia existe
            if (transferencia.getTrfpCod() == null) {
                return new TransferenciaProducto.MensajeRespuesta(400L, "El código de transferencia no puede ser nulo.", null);
            }

            if (!transferenciaRepository.existsById(transferencia.getTrfpCod())) {
                return new TransferenciaProducto.MensajeRespuesta(404L, "Transferencia de producto no encontrada con el código especificado.", null);
            }

            // Verificar que el tipo de transferencia exista
            if (!valorRepository.existsByParValorIgnoreCase(transferencia.getTrfpTipo())) {
                return new TransferenciaProducto.MensajeRespuesta(400L, "El tipo de transferencia con código " + transferencia.getTrfpTipo() + " no existe.", null);
            }

            // Verificar que el almacén de origen exista
            if (!almacenRepository.existsById(transferencia.getTrfpOrigen())) {
                return new TransferenciaProducto.MensajeRespuesta(400L, "El almacén de origen con código " + transferencia.getTrfpOrigen() + " no existe.", null);
            }

            // Verificar que el almacén de destino exista
            if (!almacenRepository.existsById(transferencia.getTrfpDestino())) {
                return new TransferenciaProducto.MensajeRespuesta(400L, "El almacén de destino con código " + transferencia.getTrfpDestino() + " no existe.", null);
            }

            // Verificar que origen y destino no sean iguales
            if (transferencia.getTrfpOrigen().equals(transferencia.getTrfpDestino())) {
                return new TransferenciaProducto.MensajeRespuesta(400L, "El almacén de origen no puede ser igual al almacén de destino.", null);
            }

            // Verificar la referencia de envío si existe
            if (transferencia.getTrfpRefEnv() != null) {
                if (!transferenciaRepository.existsById(transferencia.getTrfpRefEnv())) {
                    return new TransferenciaProducto.MensajeRespuesta(400L, "La referencia de envío con código " + transferencia.getTrfpRefEnv() + " no existe.", null);
                }

                // Verificar que no se haga referencia a sí misma
                if (transferencia.getTrfpRefEnv().equals(transferencia.getTrfpCod())) {
                    return new TransferenciaProducto.MensajeRespuesta(400L, "La transferencia no puede hacer referencia a sí misma.", null);
                }
            }

            // Guardar los cambios
            TransferenciaProducto transferenciaActualizada = transferenciaRepository.save(transferencia);
            return new TransferenciaProducto.MensajeRespuesta(200L, "Transferencia de producto actualizada exitosamente.", List.of(transferenciaActualizada));
        } catch (Exception e) {
            logger.error("Error al actualizar la transferencia de producto: ", e);
            return new TransferenciaProducto.MensajeRespuesta(500L, "Error al actualizar la transferencia de producto: " + e.getMessage(), null);
        }
    }

    /**
     * Elimina una transferencia de producto por su código
     * @param trfpCod Código de la transferencia a eliminar
     * @return MensajeRespuesta con el resultado de la operación
     */
    @Transactional
    public TransferenciaProducto.MensajeRespuesta deleteTransferencia(Long trfpCod) {
        try {
            // Verificar si la transferencia existe
            if (!transferenciaRepository.existsById(trfpCod)) {
                logger.info("Transferencia con código {} no encontrada.", trfpCod);
                return new TransferenciaProducto.MensajeRespuesta(404L,
                        "No se encontró la transferencia de producto con el código: " + trfpCod, null);
            }

            // Verificar si hay transferencias que hacen referencia a esta como envío
            List<TransferenciaProducto> referenciasEnvio = transferenciaRepository.findByTrfpRefEnv(trfpCod);
            if (!referenciasEnvio.isEmpty()) {
                logger.info("Transferencia con código {} tiene {} referencias como envío.", trfpCod, referenciasEnvio.size());
                return new TransferenciaProducto.MensajeRespuesta(409L,
                        "No se puede eliminar la transferencia ya que está siendo referenciada como envío en otras transferencias.", null);
            }

            // Verificar si hay detalles asociados
            long detallesCount = detalleRepository.countByTrfpCod(trfpCod);
            if (detallesCount > 0) {
                logger.info("Transferencia con código {} tiene {} detalle(s) asociados.", trfpCod, detallesCount);
                return new TransferenciaProducto.MensajeRespuesta(409L,
                        "No se puede eliminar la transferencia porque tiene " + detallesCount + " detalle(s) asociados a esta TRANSFERENCIA", null);
            }

            // Si no hay detalles ni referencias, proceder con la eliminación
            transferenciaRepository.deleteById(trfpCod);
            logger.info("Transferencia con código {} eliminada exitosamente.", trfpCod);
            return new TransferenciaProducto.MensajeRespuesta(200L,
                    "Transferencia de producto eliminada exitosamente.", null);

        } catch (Exception e) {
            logger.error("Error inesperado al eliminar la transferencia de producto con código {}: ", trfpCod, e);
            return new TransferenciaProducto.MensajeRespuesta(500L,
                    "Error inesperado al eliminar la transferencia de producto: " + e.getMessage(), null);
        }
    }
}
package py.com.nsa.api.commons.components.cfg.productoimpuesto.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.productoimpuesto.model.ProductoImpuesto;
import py.com.nsa.api.commons.components.cfg.productoimpuesto.model.ProductoImpuestoId;
import py.com.nsa.api.commons.components.cfg.productoimpuesto.repository.ProductoImpuestoRepository;
import py.com.nsa.api.commons.components.ref.producto.repository.ProductoRepository;
import py.com.nsa.api.commons.components.cfg.pais.repository.PaisRepository;
import py.com.nsa.api.commons.components.cfg.impuesto.repository.ImpuestoRepository;

import java.util.List;

@Service
public class ProductoImpuestoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoImpuestoService.class);

    @Autowired
    private ProductoImpuestoRepository repository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private ImpuestoRepository impuestoRepository;

    public ProductoImpuesto.MensajeRespuesta getProductoImpuestosAll() {
        try {
            List<ProductoImpuesto> productoImpuestos = repository.findAll();

            if (productoImpuestos.isEmpty()) {
                return new ProductoImpuesto.MensajeRespuesta(204L, "No se encontraron asignaciones de impuestos a productos.", null);
            }
            return new ProductoImpuesto.MensajeRespuesta(200L, "Asignaciones de impuestos a productos obtenidas exitosamente.", productoImpuestos);
        } catch (Exception e) {
            logger.error("Error al obtener asignaciones de impuestos a productos: ", e);
            return new ProductoImpuesto.MensajeRespuesta(500L, "Error al obtener asignaciones de impuestos a productos: " + e.getMessage(), null);
        }
    }

    public ProductoImpuesto.MensajeRespuesta getProductoImpuestosFiltered(ProductoImpuesto filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("proCod", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase())
                    .withMatcher("paCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("impCod", ExampleMatcher.GenericPropertyMatchers.exact());

            Example<ProductoImpuesto> example = Example.of(filtro, matcher);

            List<ProductoImpuesto> productoImpuestos = repository.findAll(example);

            if (!productoImpuestos.isEmpty()) {
                return new ProductoImpuesto.MensajeRespuesta(200L, "Asignaciones de impuestos a productos encontradas", productoImpuestos);
            } else {
                return new ProductoImpuesto.MensajeRespuesta(204L, "No se encontraron asignaciones de impuestos a productos", null);
            }
        } catch (Exception e) {
            logger.error("Error al filtrar asignaciones de impuestos a productos: ", e);
            return new ProductoImpuesto.MensajeRespuesta(500L, "Error al filtrar asignaciones de impuestos a productos: " + e.getMessage(), null);
        }
    }

    public ProductoImpuesto.MensajeRespuesta insert(ProductoImpuesto productoImpuesto) {
        try {
            logger.debug("Intentando insertar asignación de impuesto a producto: {}", productoImpuesto);

            // Validar que el producto exista
            if (!productoRepository.existsByProCodIgnoreCase(productoImpuesto.getProCod())) {
                return new ProductoImpuesto.MensajeRespuesta(400L, "El producto especificado no existe.", null);
            }

            // Validar que el país exista
            if (!paisRepository.existsById(productoImpuesto.getPaCod())) {
                return new ProductoImpuesto.MensajeRespuesta(400L, "El país especificado no existe.", null);
            }

            // Validar que el impuesto exista
            if (!impuestoRepository.existsByImpCod(productoImpuesto.getImpCod())) {
                return new ProductoImpuesto.MensajeRespuesta(400L, "El impuesto especificado no existe.", null);
            }

            // Verificar si ya existe una asignación para este producto y país
            if (repository.existsByProCodAndPaCod(productoImpuesto.getProCod(), productoImpuesto.getPaCod())) {
                return new ProductoImpuesto.MensajeRespuesta(409L, "Ya existe una asignación de impuesto para este producto y país.", null);
            }

            ProductoImpuesto insertedProductoImpuesto = repository.save(productoImpuesto);
            logger.debug("Asignación de impuesto a producto guardada exitosamente: {}", insertedProductoImpuesto);

            return new ProductoImpuesto.MensajeRespuesta(200L, "Asignación de impuesto a producto creada exitosamente.", List.of(insertedProductoImpuesto));
        } catch (Exception e) {
            logger.error("Error al insertar asignación de impuesto a producto: ", e);
            return new ProductoImpuesto.MensajeRespuesta(500L, "Error al insertar asignación de impuesto a producto: " + e.getMessage(), null);
        }
    }

    public ProductoImpuesto.MensajeRespuesta update(ProductoImpuesto productoImpuesto) {
        try {
            // Validar que el producto exista
            if (!productoRepository.existsByProCodIgnoreCase(productoImpuesto.getProCod())) {
                return new ProductoImpuesto.MensajeRespuesta(400L, "El producto especificado no existe.", null);
            }

            // Validar que el país exista
            if (!paisRepository.existsById(productoImpuesto.getPaCod())) {
                return new ProductoImpuesto.MensajeRespuesta(400L, "El país especificado no existe.", null);
            }

            // Validar que el impuesto exista
            if (!impuestoRepository.existsByImpCod(productoImpuesto.getImpCod())) {
                return new ProductoImpuesto.MensajeRespuesta(400L, "El impuesto especificado no existe.", null);
            }

            // Verificar si existe el registro a actualizar
            ProductoImpuestoId id = new ProductoImpuestoId(productoImpuesto.getProCod(), productoImpuesto.getPaCod());
            if (!repository.existsById(id)) {
                return new ProductoImpuesto.MensajeRespuesta(404L, "No se encontró la asignación de impuesto para el producto y país especificados.", null);
            }

            ProductoImpuesto updatedProductoImpuesto = repository.save(productoImpuesto);
            return new ProductoImpuesto.MensajeRespuesta(200L, "Asignación de impuesto a producto actualizada exitosamente.", List.of(updatedProductoImpuesto));
        } catch (Exception e) {
            logger.error("Error al actualizar asignación de impuesto a producto: ", e);
            return new ProductoImpuesto.MensajeRespuesta(500L, "Error al actualizar asignación de impuesto a producto: " + e.getMessage(), null);
        }
    }

    public ProductoImpuesto.MensajeRespuesta delete(String proCod, Long paCod) {
        try {
            ProductoImpuestoId id = new ProductoImpuestoId(proCod, paCod);

            // Verificar si existe el registro
            if (!repository.existsById(id)) {
                return new ProductoImpuesto.MensajeRespuesta(404L, "No se encontró la asignación de impuesto para el producto y país especificados.", null);
            }

            try {
                // Intentar eliminar
                repository.deleteById(id);
                return new ProductoImpuesto.MensajeRespuesta(200L, "Asignación de impuesto a producto eliminada exitosamente.", null);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                // Capturar error de clave foránea
                return new ProductoImpuesto.MensajeRespuesta(409L, "No se puede eliminar la asignación de impuesto a producto ya que está siendo utilizada en otros registros.", null);
            }
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar asignación de impuesto a producto: ", e);
            return new ProductoImpuesto.MensajeRespuesta(500L, "Error inesperado al eliminar asignación de impuesto a producto: " + e.getMessage(), null);
        }
    }
}
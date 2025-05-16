package py.com.nsa.api.commons.components.ref.producto.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.producto.model.Producto;
import py.com.nsa.api.commons.components.ref.producto.repository.ProductoRepository;
import py.com.nsa.api.commons.components.ref.empresa.repository.EmpresaRepository;

import java.util.Date;
import java.util.List;

@Service
public class ProductoService {

    private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    /**
     * Obtiene todos los productos
     */
    public Producto.MensajeRespuesta getProductosAll() {
        try {
            List<Producto> productos = productoRepository.findAll();
            if (productos.isEmpty()) {
                return new Producto.MensajeRespuesta(204L, "No se encontraron productos.", null);
            }
            return new Producto.MensajeRespuesta(200L, "Productos obtenidos exitosamente.", productos);
        } catch (Exception e) {
            logger.error("Error al obtener productos: ", e);
            return new Producto.MensajeRespuesta(500L, "Error al obtener productos: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene productos filtrados por los criterios especificados
     */
    public Producto.MensajeRespuesta getProductosFiltered(Producto filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("proCod", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("proDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("parTipo", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("parUm", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("parServicio", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("proEstado", ExampleMatcher.GenericPropertyMatchers.exact());

            Example<Producto> example = Example.of(filtro, matcher);
            List<Producto> productos = productoRepository.findAll(example);

            if (!productos.isEmpty()) {
                return new Producto.MensajeRespuesta(200L, "Productos encontrados", productos);
            } else {
                return new Producto.MensajeRespuesta(204L, "No se encontraron productos", null);
            }
        } catch (Exception e) {
            logger.error("Error al filtrar productos: ", e);
            return new Producto.MensajeRespuesta(500L, "Error al filtrar productos: " + e.getMessage(), null);
        }
    }

    /**
     * Inserta un nuevo producto
     */
    public Producto.MensajeRespuesta insertarProducto(Producto producto) {
        try {
            logger.debug("Intentando insertar producto: {}", producto);

            // Validar existencia de la empresa
            if (producto.getEmpresa() == null || !empresaRepository.existsById(producto.getEmpresa().getEmCod())) {
                return new Producto.MensajeRespuesta(404L, "No existe una empresa con el código: " +
                        (producto.getEmpresa() != null ? producto.getEmpresa().getEmCod() : "null"), null);
            }

            // Verificar si ya existe un producto con el mismo código
            if (productoRepository.existsByProCodIgnoreCase(producto.getProCod())) {
                return new Producto.MensajeRespuesta(409L, "Ya existe un producto con el mismo código.", null);
            }

            // Verificar si ya existe un producto con la misma descripción
            if (productoRepository.existsByProDescripcionIgnoreCase(producto.getProDescripcion())) {
                return new Producto.MensajeRespuesta(409L, "Ya existe un producto con la misma descripción.", null);
            }

            // Establecer fechas de creación y modificación
            Date fechaActual = new Date();
            producto.setProCreacion(fechaActual);
            producto.setProUltModif(fechaActual);

            // Guardar producto
            Producto nuevoProducto = productoRepository.save(producto);
            logger.debug("Producto guardado exitosamente: {}", nuevoProducto);

            return new Producto.MensajeRespuesta(200L, "Producto creado exitosamente.", List.of(nuevoProducto));
        } catch (Exception e) {
            logger.error("Error al insertar el producto: ", e);
            return new Producto.MensajeRespuesta(500L, "Error al insertar el producto: " + e.getMessage(), null);
        }
    }

    /**
     * Actualiza un producto existente
     */
    public Producto.MensajeRespuesta updateProducto(Producto producto) {
        try {
            // Validar si el producto existe
            if (!productoRepository.existsById(producto.getProCod())) {
                return new Producto.MensajeRespuesta(404L, "Producto no encontrado con el código especificado.", null);
            }

            // Validar existencia de la empresa
            if (producto.getEmpresa() == null || !empresaRepository.existsById(producto.getEmpresa().getEmCod())) {
                return new Producto.MensajeRespuesta(404L, "No existe una empresa con el código: " +
                        (producto.getEmpresa() != null ? producto.getEmpresa().getEmCod() : "null"), null);
            }

            // Verificar si ya existe otro producto con la misma descripción (diferente al actual)
            productoRepository.findByProDescripcionContainingIgnoreCase(producto.getProDescripcion())
                    .stream()
                    .filter(p -> !p.getProCod().equalsIgnoreCase(producto.getProCod()))
                    .findAny()
                    .ifPresent(p -> {
                        throw new RuntimeException("Ya existe otro producto con la misma descripción");
                    });

            // Actualizar fecha de última modificación
            producto.setProUltModif(new Date());

            // Mantener fecha de creación original
            productoRepository.findById(producto.getProCod())
                    .ifPresent(p -> producto.setProCreacion(p.getProCreacion()));

            // Guardar los cambios
            Producto productoActualizado = productoRepository.save(producto);
            return new Producto.MensajeRespuesta(200L, "Producto actualizado exitosamente.", List.of(productoActualizado));
        } catch (RuntimeException e) {
            logger.error("Error al actualizar el producto: ", e);
            return new Producto.MensajeRespuesta(409L, e.getMessage(), null);
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar el producto: ", e);
            return new Producto.MensajeRespuesta(500L, "Error al actualizar el producto: " + e.getMessage(), null);
        }
    }

    /**
     * Elimina un producto por su código
     */
    public Producto.MensajeRespuesta deleteProducto(String proCod) {
        try {
            if (!productoRepository.existsById(proCod)) {
                return new Producto.MensajeRespuesta(404L, "No se encontró el producto con el código: " + proCod, null);
            }

            try {
                productoRepository.deleteById(proCod);
                return new Producto.MensajeRespuesta(200L, "Producto eliminado exitosamente.", null);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                return new Producto.MensajeRespuesta(409L, "No se puede eliminar el producto ya que está siendo utilizado en otros registros.", null);
            }
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar el producto: ", e);
            return new Producto.MensajeRespuesta(500L, "Error inesperado al eliminar el producto: " + e.getMessage(), null);
        }
    }
}
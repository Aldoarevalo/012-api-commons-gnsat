package py.com.nsa.api.commons.components.ref.materialensamblado.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.materialensamblado.model.MaterialEnsamblado;
import py.com.nsa.api.commons.components.ref.materialensamblado.model.MaterialEnsambladoId;
import py.com.nsa.api.commons.components.ref.materialensamblado.repository.MaterialEnsambladoRepository;
import py.com.nsa.api.commons.components.ref.producto.repository.ProductoRepository;
import py.com.nsa.api.commons.components.ref.parvalor.repository.ValorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialEnsambladoService {

    private static final Logger logger = LoggerFactory.getLogger(MaterialEnsambladoService.class);

    @Autowired
    private MaterialEnsambladoRepository materialRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ValorRepository valorRepository;

    /**
     * Obtiene todos los materiales ensamblados
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MaterialEnsamblado.MensajeRespuesta getMaterialesAll() {
        try {
            List<MaterialEnsamblado> materiales = materialRepository.findAll();
            if (materiales.isEmpty()) {
                return new MaterialEnsamblado.MensajeRespuesta(204L, "No se encontraron materiales ensamblados.", null);
            }
            return new MaterialEnsamblado.MensajeRespuesta(200L, "Materiales ensamblados obtenidos exitosamente.", materiales);
        } catch (Exception e) {
            logger.error("Error al obtener materiales ensamblados: ", e);
            return new MaterialEnsamblado.MensajeRespuesta(500L, "Error al obtener materiales ensamblados: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene materiales ensamblados por código de producto
     * @param proCod Código del producto
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MaterialEnsamblado.MensajeRespuesta getMaterialesByProducto(String proCod) {
        try {
            List<MaterialEnsamblado> materiales = materialRepository.findByProCod(proCod);
            if (materiales.isEmpty()) {
                return new MaterialEnsamblado.MensajeRespuesta(204L, "No se encontraron materiales ensamblados para el producto con código: " + proCod, null);
            }
            return new MaterialEnsamblado.MensajeRespuesta(200L, "Materiales ensamblados encontrados.", materiales);
        } catch (Exception e) {
            logger.error("Error al obtener materiales ensamblados por producto: ", e);
            return new MaterialEnsamblado.MensajeRespuesta(500L, "Error al obtener materiales ensamblados por producto: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene un material ensamblado específico
     * @param meLinea Número de línea del material
     * @param proCod Código del producto
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MaterialEnsamblado.MensajeRespuesta getMaterialById(Integer meLinea, String proCod) {
        try {
            Optional<MaterialEnsamblado> material = materialRepository.findByMeLineaAndProCod(meLinea, proCod);
            if (material.isEmpty()) {
                return new MaterialEnsamblado.MensajeRespuesta(204L, "No se encontró el material ensamblado con línea: " + meLinea + " y código de producto: " + proCod, null);
            }
            return new MaterialEnsamblado.MensajeRespuesta(200L, "Material ensamblado encontrado.", List.of(material.get()));
        } catch (Exception e) {
            logger.error("Error al obtener el material ensamblado: ", e);
            return new MaterialEnsamblado.MensajeRespuesta(500L, "Error al obtener el material ensamblado: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene materiales ensamblados filtrados por los criterios especificados
     * @param filtro Objeto MaterialEnsamblado con los criterios de filtrado
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MaterialEnsamblado.MensajeRespuesta getMaterialesFiltered(MaterialEnsamblado filtro) {
        try {
            // Configurar el ExampleMatcher para filtros
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("meLinea", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("proCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("meUm", ExampleMatcher.GenericPropertyMatchers.exact());

            // Crear ejemplo con el filtro y matcher
            Example<MaterialEnsamblado> example = Example.of(filtro, matcher);

            // Realizar la consulta usando el filtro como ejemplo
            List<MaterialEnsamblado> materiales = materialRepository.findAll(example);

            if (!materiales.isEmpty()) {
                return new MaterialEnsamblado.MensajeRespuesta(200L, "Materiales ensamblados encontrados", materiales);
            } else {
                return new MaterialEnsamblado.MensajeRespuesta(204L, "No se encontraron materiales ensamblados", null);
            }
        } catch (Exception e) {
            logger.error("Error al filtrar materiales ensamblados: ", e);
            return new MaterialEnsamblado.MensajeRespuesta(500L, "Error al filtrar materiales ensamblados: " + e.getMessage(), null);
        }
    }

    /**
     * Inserta un nuevo material ensamblado
     * @param material Material ensamblado a insertar
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MaterialEnsamblado.MensajeRespuesta insertarMaterial(MaterialEnsamblado material) {
        try {
            logger.debug("Intentando insertar material ensamblado: {}", material);

            // Verificar que el producto exista
            if (!productoRepository.existsByProCodIgnoreCase(material.getProCod())) {
                return new MaterialEnsamblado.MensajeRespuesta(400L, "El producto con código " + material.getProCod() + " no existe.", null);
            }

            // Verificar que la unidad de medida exista
            if (!valorRepository.existsByParValorIgnoreCase(material.getMeUm())) {
                return new MaterialEnsamblado.MensajeRespuesta(400L, "La unidad de medida con código " + material.getMeUm() + " no existe.", null);
            }

            // Si no se proporciona un número de línea, generar uno automáticamente
            if (material.getMeLinea() == null) {
                Integer nextLineNumber = materialRepository.getNextLineNumber(material.getProCod());
                material.setMeLinea(nextLineNumber);
            } else {
                // Verificar si ya existe un material con la misma línea y producto
                if (materialRepository.existsByMeLineaAndProCod(material.getMeLinea(), material.getProCod())) {
                    return new MaterialEnsamblado.MensajeRespuesta(409L, "Ya existe un material ensamblado con la línea " + material.getMeLinea() + " para el producto " + material.getProCod(), null);
                }
            }

            // Guardar material ensamblado
            MaterialEnsamblado nuevoMaterial = materialRepository.save(material);
            logger.debug("Material ensamblado guardado exitosamente: {}", nuevoMaterial);

            return new MaterialEnsamblado.MensajeRespuesta(200L, "Material ensamblado creado exitosamente.", List.of(nuevoMaterial));
        } catch (Exception e) {
            logger.error("Error al insertar el material ensamblado: ", e);
            return new MaterialEnsamblado.MensajeRespuesta(500L, "Error al insertar el material ensamblado: " + e.getMessage(), null);
        }
    }

    /**
     * Actualiza un material ensamblado existente
     * @param material Material ensamblado con datos actualizados
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MaterialEnsamblado.MensajeRespuesta updateMaterial(MaterialEnsamblado material) {
        try {
            // Validar si el material existe
            if (material.getMeLinea() == null || material.getProCod() == null) {
                return new MaterialEnsamblado.MensajeRespuesta(400L, "La línea y el código de producto no pueden ser nulos.", null);
            }

            MaterialEnsambladoId id = new MaterialEnsambladoId(material.getMeLinea(), material.getProCod());
            if (!materialRepository.existsById(id)) {
                return new MaterialEnsamblado.MensajeRespuesta(404L, "Material ensamblado no encontrado con la línea y código de producto especificados.", null);
            }

            // Verificar que el producto exista
            if (!productoRepository.existsByProCodIgnoreCase(material.getProCod())) {
                return new MaterialEnsamblado.MensajeRespuesta(400L, "El producto con código " + material.getProCod() + " no existe.", null);
            }

            // Verificar que la unidad de medida exista
            if (!valorRepository.existsByParValorIgnoreCase(material.getMeUm())) {
                return new MaterialEnsamblado.MensajeRespuesta(400L, "La unidad de medida con código " + material.getMeUm() + " no existe.", null);
            }

            // Guardar los cambios
            MaterialEnsamblado materialActualizado = materialRepository.save(material);
            return new MaterialEnsamblado.MensajeRespuesta(200L, "Material ensamblado actualizado exitosamente.", List.of(materialActualizado));
        } catch (Exception e) {
            logger.error("Error al actualizar el material ensamblado: ", e);
            return new MaterialEnsamblado.MensajeRespuesta(500L, "Error al actualizar el material ensamblado: " + e.getMessage(), null);
        }
    }

    /**
     * Elimina un material ensamblado
     * @param meLinea Número de línea del material
     * @param proCod Código del producto
     * @return MensajeRespuesta con el resultado de la operación
     */
    public MaterialEnsamblado.MensajeRespuesta deleteMaterial(Integer meLinea, String proCod) {
        try {
            // Verificar si el material existe
            MaterialEnsambladoId id = new MaterialEnsambladoId(meLinea, proCod);
            if (!materialRepository.existsById(id)) {
                return new MaterialEnsamblado.MensajeRespuesta(404L, "No se encontró el material ensamblado con línea: " + meLinea + " y código de producto: " + proCod, null);
            }

            try {
                // Intentar eliminar, si falla por restricciones de clave foránea, capturar la excepción
                materialRepository.deleteById(id);
                return new MaterialEnsamblado.MensajeRespuesta(200L, "Material ensamblado eliminado exitosamente.", null);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                // Capturar error de clave foránea
                return new MaterialEnsamblado.MensajeRespuesta(409L, "No se puede eliminar el material ensamblado ya que está siendo utilizado en otros registros.", null);
            }
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar el material ensamblado: ", e);
            return new MaterialEnsamblado.MensajeRespuesta(500L, "Error inesperado al eliminar el material ensamblado: " + e.getMessage(), null);
        }
    }
}
package py.com.nsa.api.commons.components.ref.almacen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.almacen.model.Almacen;
import py.com.nsa.api.commons.components.ref.almacen.repository.AlmacenRepository;
import py.com.nsa.api.commons.components.cfg.agencia.repository.AgenciaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlmacenService {

    private static final Logger logger = LoggerFactory.getLogger(AlmacenService.class);

    @Autowired
    private AlmacenRepository almacenRepository;

    @Autowired
    private AgenciaRepository agenciaRepository;

    /**
     * Obtiene todos los almacenes
     * @return MensajeRespuesta con el resultado de la operación
     */
    public Almacen.MensajeRespuesta getAlmacenesAll() {
        try {
            List<Almacen> almacenes = almacenRepository.findAll();
            if (almacenes.isEmpty()) {
                return new Almacen.MensajeRespuesta(204L, "No se encontraron almacenes.", null);
            }
            return new Almacen.MensajeRespuesta(200L, "Almacenes obtenidos exitosamente.", almacenes);
        } catch (Exception e) {
            logger.error("Error al obtener almacenes: ", e);
            return new Almacen.MensajeRespuesta(500L, "Error al obtener almacenes: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene un almacén por su código
     * @param alCod Código del almacén
     * @return MensajeRespuesta con el resultado de la operación
     */
    public Almacen.MensajeRespuesta getAlmacenByAlCod(Long alCod) {
        try {
            Optional<Almacen> almacen = almacenRepository.findByAlCod(alCod);

            if (almacen.isEmpty()) {
                return new Almacen.MensajeRespuesta(204L, "No se encontró el almacén con el código: " + alCod, null);
            }

            return new Almacen.MensajeRespuesta(200L, "Almacén encontrado.", List.of(almacen.get()));
        } catch (Exception e) {
            logger.error("Error al obtener el almacén: ", e);
            return new Almacen.MensajeRespuesta(500L, "Error al obtener el almacén: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene almacenes filtrados por los criterios especificados
     * @param filtro Objeto Almacen con los criterios de filtrado
     * @return MensajeRespuesta con el resultado de la operación
     */
    public Almacen.MensajeRespuesta getAlmacenesFiltered(Almacen filtro) {
        try {
            // Configurar el ExampleMatcher para filtros de coincidencia parcial y sin sensibilidad a mayúsculas
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("alCod", ExampleMatcher.GenericPropertyMatchers.exact()) // Coincidencia exacta
                    .withMatcher("alDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("agCod", ExampleMatcher.GenericPropertyMatchers.exact());

            // Crear ejemplo con el filtro y matcher
            Example<Almacen> example = Example.of(filtro, matcher);

            // Realizar la consulta usando el filtro como ejemplo
            List<Almacen> almacenes = almacenRepository.findAll(example);

            if (!almacenes.isEmpty()) {
                return new Almacen.MensajeRespuesta(200L, "Almacenes encontrados", almacenes);
            } else {
                return new Almacen.MensajeRespuesta(204L, "No se encontraron almacenes", null);
            }
        } catch (Exception e) {
            logger.error("Error al filtrar almacenes: ", e);
            return new Almacen.MensajeRespuesta(500L, "Error al filtrar almacenes: " + e.getMessage(), null);
        }
    }

    /**
     * Inserta un nuevo almacén
     * @param almacen Almacén a insertar
     * @return MensajeRespuesta con el resultado de la operación
     */
    public Almacen.MensajeRespuesta insertarAlmacen(Almacen almacen) {
        try {
            logger.debug("Intentando insertar almacén: {}", almacen);

            // Verificar que la descripción del almacén no esté vacía
            if (almacen.getAlDescripcion() == null || almacen.getAlDescripcion().trim().isEmpty()) {
                return new Almacen.MensajeRespuesta(400L, "La descripción del almacén no puede estar vacía.", null);
            }

            // Verificar si ya existe un almacén con la misma descripción
        //    if (almacenRepository.existsByAlDescripcionIgnoreCase(almacen.getAlDescripcion())) {
          //      return new Almacen.MensajeRespuesta(409L, "Ya existe un almacén con la misma descripción.", null);
           // }

            // Verificar que la agencia exista
            if (!agenciaRepository.existsById(almacen.getAgCod())) {
                return new Almacen.MensajeRespuesta(400L, "La agencia con código " + almacen.getAgCod() + " no existe.", null);
            }

            // Guardar almacén
            Almacen nuevoAlmacen = almacenRepository.save(almacen);
            logger.debug("Almacén guardado exitosamente: {}", nuevoAlmacen);

            return new Almacen.MensajeRespuesta(200L, "Almacén creado exitosamente.", List.of(nuevoAlmacen));
        } catch (Exception e) {
            logger.error("Error al insertar el almacén: ", e);
            return new Almacen.MensajeRespuesta(500L, "Error al insertar el almacén: " + e.getMessage(), null);
        }
    }

    /**
     * Actualiza un almacén existente
     * @param almacen Almacén con datos actualizados
     * @return MensajeRespuesta con el resultado de la operación
     */
    public Almacen.MensajeRespuesta updateAlmacen(Almacen almacen) {
        try {
            // Validar si el almacén existe
            if (almacen.getAlCod() == null) {
                return new Almacen.MensajeRespuesta(400L, "El código de almacén no puede ser nulo.", null);
            }

            if (!almacenRepository.existsById(almacen.getAlCod())) {
                return new Almacen.MensajeRespuesta(404L, "Almacén no encontrado con el código especificado.", null);
            }

            // Verificar que la descripción del almacén no esté vacía
            if (almacen.getAlDescripcion() == null || almacen.getAlDescripcion().trim().isEmpty()) {
                return new Almacen.MensajeRespuesta(400L, "La descripción del almacén no puede estar vacía.", null);
            }

            // Verificar si ya existe otro almacén con la misma descripción (diferente al actual)
            if (almacenRepository.existsByAlDescripcionIgnoreCaseAndAlCodNot(almacen.getAlDescripcion(), almacen.getAlCod())) {
                return new Almacen.MensajeRespuesta(409L, "Ya existe otro almacén con la misma descripción.", null);
            }

            // Verificar que la agencia exista
            if (!agenciaRepository.existsById(almacen.getAgCod())) {
                return new Almacen.MensajeRespuesta(400L, "La agencia con código " + almacen.getAgCod() + " no existe.", null);
            }

            // Guardar los cambios
            Almacen almacenActualizado = almacenRepository.save(almacen);
            return new Almacen.MensajeRespuesta(200L, "Almacén actualizado exitosamente.", List.of(almacenActualizado));
        } catch (Exception e) {
            logger.error("Error al actualizar el almacén: ", e);
            return new Almacen.MensajeRespuesta(500L, "Error al actualizar el almacén: " + e.getMessage(), null);
        }
    }

    /**
     * Elimina un almacén por su código
     * @param alCod Código del almacén a eliminar
     * @return MensajeRespuesta con el resultado de la operación
     */
    public Almacen.MensajeRespuesta deleteAlmacen(Long alCod) {
        try {
            // Verificar si el almacén existe
            if (!almacenRepository.existsById(alCod)) {
                return new Almacen.MensajeRespuesta(404L, "No se encontró el almacén con el código: " + alCod, null);
            }

            try {
                // Intentar eliminar, si falla por restricciones de clave foránea, capturar la excepción
                almacenRepository.deleteById(alCod);
                return new Almacen.MensajeRespuesta(200L, "Almacén eliminado exitosamente.", null);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                // Capturar error de clave foránea
                return new Almacen.MensajeRespuesta(409L, "No se puede eliminar el almacén ya que está siendo utilizado en otros registros.", null);
            }
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar el almacén: ", e);
            return new Almacen.MensajeRespuesta(500L, "Error inesperado al eliminar el almacén: " + e.getMessage(), null);
        }
    }
}
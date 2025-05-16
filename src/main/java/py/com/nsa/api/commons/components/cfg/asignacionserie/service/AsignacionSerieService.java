package py.com.nsa.api.commons.components.cfg.asignacionserie.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.asignacionserie.model.AsignacionSerie;
import py.com.nsa.api.commons.components.cfg.asignacionserie.model.clavecompuesta.AsignacionSerieId;
import py.com.nsa.api.commons.components.cfg.asignacionserie.repository.AsignacionSerieRepository;

import java.util.List;

@Service
public class AsignacionSerieService {

    private static final Logger logger = LoggerFactory.getLogger(AsignacionSerieService.class);

    @Autowired
    private AsignacionSerieRepository repository;

    public AsignacionSerie.MensajeRespuesta getAsignacionesAll() {
        try {
            List<AsignacionSerie> asignaciones = repository.findAll();
            if (asignaciones.isEmpty()) {
                return new AsignacionSerie.MensajeRespuesta(204L, "No se encontraron asignaciones de serie.", null);
            }
            return new AsignacionSerie.MensajeRespuesta(200L, "Asignaciones obtenidas exitosamente.", asignaciones);
        } catch (Exception e) {
            logger.error("Error al obtener las asignaciones: ", e);
            return new AsignacionSerie.MensajeRespuesta(500L, "Error al obtener las asignaciones: " + e.getMessage(), null);
        }
    }

    public AsignacionSerie.MensajeRespuesta getAsignacionesFiltered(AsignacionSerieId filtro) {
        try {
            AsignacionSerie asignacionFiltro = new AsignacionSerie();
            asignacionFiltro.setId(filtro);

            // Configurar el ExampleMatcher para la búsqueda
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("id.agCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("id.sCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("id.asUsuario", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("id.asTDoc", ExampleMatcher.GenericPropertyMatchers.exact());

            // Crear el Example con el filtro y el matcher
            Example<AsignacionSerie> example = Example.of(asignacionFiltro, matcher);

            // Realizar la búsqueda
            List<AsignacionSerie> asignaciones = repository.findAll(example);

            if (!asignaciones.isEmpty()) {
                return new AsignacionSerie.MensajeRespuesta(200L, "Asignaciones encontradas", asignaciones);
            } else {
                return new AsignacionSerie.MensajeRespuesta(204L, "No se encontraron asignaciones con los criterios especificados", null);
            }
        } catch (Exception e) {
            logger.error("Error al filtrar asignaciones: ", e);
            return new AsignacionSerie.MensajeRespuesta(500L, "Error al filtrar asignaciones: " + e.getMessage(), null);
        }
    }

    public AsignacionSerie.MensajeRespuesta insertarAsignacion(AsignacionSerie asignacion) {
        try {
            AsignacionSerieId id = asignacion.getId();

            // Verificar si ya existe una asignación con los mismos valores de ID compuesto
            if (repository.existsById(id)) {
                return new AsignacionSerie.MensajeRespuesta(204L,
                        "Ya existe una asignación con los mismos valores.", null);
            }

            // Nueva validación: verificar si existe otra serie diferente para la misma combinación de agencia, usuario y tipo de documento
            List<AsignacionSerie> existentes = repository.findAll(Example.of(
                    AsignacionSerie.builder()
                            .id(new AsignacionSerieId(id.getAgCod(), null, id.getAsUsuario(), id.getAsTDoc()))
                            .build(),
                    ExampleMatcher.matching()
                            .withIgnoreNullValues()
                            .withMatcher("id.agCod", ExampleMatcher.GenericPropertyMatchers.exact())
                            .withMatcher("id.asUsuario", ExampleMatcher.GenericPropertyMatchers.exact())
                            .withMatcher("id.asTDoc", ExampleMatcher.GenericPropertyMatchers.exact())
            ));

            if (!existentes.isEmpty()) {
                // Verificar si alguna de las asignaciones existentes tiene un número de serie diferente
                for (AsignacionSerie existente : existentes) {
                    if (!existente.getId().getSCod().equals(id.getSCod())) {
                        logger.warn("Se intentó asignar una serie diferente {} para AG_COD={}, AS_USUARIO={}, AS_T_DOC={}",
                                id.getSCod(), id.getAgCod(), id.getAsUsuario(), id.getAsTDoc());
                        return new AsignacionSerie.MensajeRespuesta(409L,
                                "No se puede asignar una serie diferente para la misma combinación de agencia, usuario y tipo de documento.", null);
                    }
                }
            }

            // Guardar la nueva asignación
            AsignacionSerie nuevaAsignacion = repository.save(asignacion);
            return new AsignacionSerie.MensajeRespuesta(200L,
                    "Asignación creada exitosamente.", List.of(nuevaAsignacion));
        } catch (Exception e) {
            logger.error("Error al insertar asignación: ", e);
            return new AsignacionSerie.MensajeRespuesta(500L,
                    "Error al insertar la asignación: " + e.getMessage(), null);
        }
    }

    public AsignacionSerie.MensajeRespuesta deleteAsignacion(AsignacionSerieId id) {
        try {
            // Verificación usando la consulta nativa de Oracle
            int count = repository.countByCompositePrimaryKey(
                    id.getAgCod(),
                    id.getSCod(),
                    id.getAsUsuario(),
                    id.getAsTDoc()
            );

            if (count == 0) {
                logger.warn("<=== No se encontró la asignación con AG_COD={}, S_COD={}, AS_USUARIO={}, AS_T_DOC={} ===>",
                        id.getAgCod(), id.getSCod(), id.getAsUsuario(), id.getAsTDoc());
                return new AsignacionSerie.MensajeRespuesta(204L, "Asignación no encontrada.", null);
            }

            try {
                // Si existe, procedemos a eliminar
                repository.deleteById(id);
                logger.info("<=== Asignación eliminada exitosamente: AG_COD={}, S_COD={}, AS_USUARIO={}, AS_T_DOC={} ===>",
                        id.getAgCod(), id.getSCod(), id.getAsUsuario(), id.getAsTDoc());
                return new AsignacionSerie.MensajeRespuesta(200L, "Asignación eliminada exitosamente.", null);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                logger.error("<=== Error de integridad al eliminar asignación: {} ===>", e.getMessage());
                return new AsignacionSerie.MensajeRespuesta(409L,
                        "No se puede eliminar la asignación ya que está siendo utilizada en otros registros.", null);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar asignación: {} ===>", e.getMessage(), e);
            return new AsignacionSerie.MensajeRespuesta(500L,
                    "Error al eliminar la asignación: " + e.getMessage(), null);
        }
    }
}
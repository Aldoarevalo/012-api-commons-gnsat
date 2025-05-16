package py.com.nsa.api.commons.components.cfg.agencia.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.agencia.repository.AgenciaRepository;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import py.com.nsa.api.commons.components.cfg.agencia.dto.AdvancedFilterRequest;
import py.com.nsa.api.commons.components.cfg.agencia.dto.AdvancedFilterRequest.FilterGroup;
import py.com.nsa.api.commons.components.cfg.agencia.dto.AdvancedFilterRequest.FilterCondition;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Predicate;
import java.text.SimpleDateFormat;
import java.text.ParseException;


@Service
public class AgenciaService {
    @Autowired
    private AgenciaRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(AgenciaService.class);

    public Agencia.MensajeRespuesta getAgenciasAll() {
        try {
            List<Agencia> agencias = repository.findAll();

            if (agencias.isEmpty()) {
                return new Agencia.MensajeRespuesta(204L, "No se encontraron Agencias.", null);
            }
            return new Agencia.MensajeRespuesta(200L, "Agencias obtenidas exitosamente.", agencias);
        } catch (Exception e) {
            System.err.println("Error al obtener los documentos: " + e.getMessage());
            e.printStackTrace();
            return new Agencia.MensajeRespuesta(500L, "Error al obtener los Tipos de Documentos: " + e.getMessage(),
                    null);
        }
    }

    public Agencia.MensajeRespuesta getAgenciasFiltered(Agencia filtro) {
        // Configuración del ExampleMatcher para filtros de coincidencia parcial y sin sensibilidad a mayúsculas
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withMatcher("agCod", ExampleMatcher.GenericPropertyMatchers.exact()) // Coincidencia exacta
                .withMatcher("agNombreFantasia", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()) // Contiene e ignora mayúsculas
                .withMatcher("parTipoAgencia", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase())
                .withMatcher("parBloqueo", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase())
                .withMatcher("paCod", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("ciuCod", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("bCod", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("agAgenciaPadre", ExampleMatcher.GenericPropertyMatchers.exact()) // Coincidencia exacta para Long
                .withMatcher("parRubro", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());

        // Crea un Example de Agencia con el filtro y el matcher
        Example<Agencia> example = Example.of(filtro, matcher);

        // Realiza la consulta usando el filtro como Example
        List<Agencia> agencias = repository.findAll(example);

        // Devuelve el resultado en el formato requerido
        if (!agencias.isEmpty()) {
            return new Agencia.MensajeRespuesta(200L, "Agencias encontradas", agencias);
        } else {
            return new Agencia.MensajeRespuesta(204L, "No se encontraron agencias", null);
        }
    }


    public Agencia.MensajeRespuesta insert(Agencia agencia) {
        try {
            logger.error("<=== sql a insertar: {} ===>", agencia);

            agencia.setAgFechaCreacion(new Date());
            agencia.setAgFechaMod(new Date());

            Agencia insertedAgencia = repository.save(agencia);
            return new Agencia.MensajeRespuesta(200L, "Agencia creada exitosamente.", List.of(insertedAgencia));
        } catch (Exception e) {
            System.err.println("Error al insertar la agencia: " + e.getMessage());
            e.printStackTrace();
            return new Agencia.MensajeRespuesta(500L, "Error al insertar Agencia: " + e.getMessage(), null);
        }
    }

    public Agencia.MensajeRespuesta update(Agencia agencia) {
        try {

            Agencia existingAgencia = repository.findById(agencia.getAgCod())
                    .orElseThrow(() -> new RuntimeException("Agencia no encontrada."));

            agencia.setAgFechaCreacion(existingAgencia.getAgFechaCreacion());
            agencia.setAgFechaMod(new Date());
            agencia.setUsuCodCreador(existingAgencia.getUsuCodCreador());

            Agencia updatedAgencia = repository.save(agencia);
            return new Agencia.MensajeRespuesta(200L, "Agencia actualizada exitosamente. ", List.of(updatedAgencia));

        } catch (Exception e) {
            System.err.println("Error al actualizar la agencia: " + e.getMessage());
            e.printStackTrace();
            return new Agencia.MensajeRespuesta(500L, "Error al actualizar la agencia: " + e.getMessage(), null);
        }
    }

    public List<Map<String, Object>> getDistinctAgenciasPais() {
        logger.info("Obteniendo lista de agencias distintas...");

        List<Map<String, Object>> agenciasRaw = repository.findDistinctAgenciasPais();

        List<Map<String, Object>> agenciasFormatted = agenciasRaw.stream().map(agencia -> {
            System.out.println(agencia); Map<String, Object> formattedMap = new HashMap<>();
            formattedMap.put("paCod", agencia.get("codPais"));
            formattedMap.put("paDescripcion", agencia.get("paDescripcion"));
            return formattedMap;
        }).collect(Collectors.toList());

        logger.info("Cantidad de registros obtenidos: {}", agenciasFormatted.size());
        return agenciasFormatted;
    }


    public List<Map<String, Object>> getDistinctCiudades(Long paCod) {
        logger.info("Obteniendo ciudades para el país con código: {}", paCod);

        List<Map<String, Object>> ciudadesRaw = repository.findDistinctCiudades(paCod);

        List<Map<String, Object>> ciudadesFormatted = ciudadesRaw.stream().map(ciudad -> {
            Map<String, Object> formattedMap = new HashMap<>();
            formattedMap.put("codCiudad", ciudad.get("codciudad"));
            formattedMap.put("ciuDescripcion", ciudad.get("ciuDescripcion"));
            return formattedMap;
        }).collect(Collectors.toList());

        logger.info("Cantidad de registros obtenidos: {}", ciudadesFormatted.size());
        return ciudadesFormatted;
    }


    public List<Map<String, Object>> getDistinctTipoAgencias() {
        logger.info("Obteniendo lista de tipos de agencia distintos...");

        List<Map<String, Object>> tipoAgenciasRaw = repository.findDistinctTipoAgencias();

        List<Map<String, Object>> tipoAgenciasFormatted = tipoAgenciasRaw.stream().map(tipo -> {
            Map<String, Object> formattedMap = new HashMap<>();
            formattedMap.put("codTipo", tipo.get("codTipo"));
            formattedMap.put("parDescripcion", tipo.get("parDescripcion"));
            return formattedMap;
        }).collect(Collectors.toList());

        logger.info("Cantidad de registros obtenidos: {}", tipoAgenciasFormatted.size());
        return tipoAgenciasFormatted;
    }


    public Agencia.MensajeRespuesta getAgenciasByUsuario(Long usuCod) {
        try {
            logger.info("Buscando agencias para el usuario: {}", usuCod);
            List<Agencia> agencias = repository.findAgenciasByUsuario(usuCod);

            if (agencias.isEmpty()) {
                return new Agencia.MensajeRespuesta(204L, "No se encontraron agencias para el usuario.", null);
            }
            return new Agencia.MensajeRespuesta(200L, "Agencias obtenidas exitosamente..", agencias);
        } catch (Exception e) {
            logger.error("Error al obtener agencias del usuario: {}", e.getMessage());
            return new Agencia.MensajeRespuesta(500L, "Error al obtener agencias del usuario: " + e.getMessage(), null);
        }
    }


    /**
     * Aplica filtros avanzados a las agencias.
     *
     * @param filterRequest Solicitud de filtros avanzados
     * @return Lista de agencias filtradas
     */
    public List<Agencia> applyAdvancedFilters(AdvancedFilterRequest filterRequest) {
        List<Agencia> allAgencias = repository.findAll();
        List<Agencia> filteredAgencias = new ArrayList<>();

        // Para cada agencia, evaluamos si cumple con todos los grupos de filtros
        for (Agencia agencia : allAgencias) {
            boolean matchesAllGroups = true;

            // Evaluamos cada grupo de filtros
            for (FilterGroup group : filterRequest.getFilterGroups()) {
                boolean matchesGroup = evaluateFilterGroup(agencia, group);

                if (!matchesGroup) {
                    matchesAllGroups = false;
                    break;
                }
            }

            if (matchesAllGroups) {
                filteredAgencias.add(agencia);
            }
        }

        return filteredAgencias;
    }

    /**
     * Evalúa si una agencia cumple con un grupo de filtros.
     *
     * @param agencia Agencia a evaluar
     * @param group Grupo de filtros a aplicar
     * @return true si la agencia cumple con el grupo de filtros
     */
    private boolean evaluateFilterGroup(Agencia agencia, FilterGroup group) {
        // Si no hay condiciones, el grupo se considera verdadero
        if (group.getConditions() == null || group.getConditions().isEmpty()) {
            return true;
        }

        boolean isAndOperator = "AND".equalsIgnoreCase(group.getOperator());

        // Para operador AND, todas las condiciones deben ser verdaderas
        // Para operador OR, al menos una condición debe ser verdadera
        boolean result = isAndOperator; // true para AND (valor inicial), false para OR (valor inicial)

        for (FilterCondition condition : group.getConditions()) {
            // Si el campo está vacío, omitimos esta condición
            if (condition.getField() == null || condition.getField().isEmpty()) {
                continue;
            }

            boolean conditionResult = evaluateCondition(agencia, condition);

            if (isAndOperator) {
                // Para AND, si una condición es falsa, todo el grupo es falso
                if (!conditionResult) {
                    return false;
                }
            } else {
                // Para OR, si una condición es verdadera, todo el grupo es verdadero
                if (conditionResult) {
                    return true;
                }
            }
        }

        return result;
    }

    /**
     * Evalúa una condición individual para una agencia.
     *
     * @param agencia Agencia a evaluar
     * @param condition Condición de filtrado
     * @return true si la condición se cumple
     */
    private boolean evaluateCondition(Agencia agencia, FilterCondition condition) {
        String field = condition.getField();
        String comparison = condition.getComparison();
        String value = condition.getValue();

        // Si el valor de comparación está vacío, omitimos esta condición
        if (value == null || value.isEmpty()) {
            return true;
        }

        // Obtener el valor del campo en la agencia utilizando reflexión o un switch-case
        Object fieldValue = getFieldValue(agencia, field);
        if (fieldValue == null) {
            // Si el campo no tiene valor, solo coincide con operadores de desigualdad
            return "notEquals".equals(comparison) || "notContains".equals(comparison);
        }

        // Comparar según el tipo de comparación
        switch (comparison) {
            case "equals":
                return compareEquals(fieldValue, value);
            case "notEquals":
                return !compareEquals(fieldValue, value);
            case "contains":
                return compareContains(fieldValue, value);
            case "notContains":
                return !compareContains(fieldValue, value);
            case "startsWith":
                return compareStartsWith(fieldValue, value);
            case "endsWith":
                return compareEndsWith(fieldValue, value);
            case "greaterThan":
                return compareGreaterThan(fieldValue, value);
            case "lessThan":
                return compareLessThan(fieldValue, value);
            case "greaterOrEqual":
                return compareGreaterOrEqual(fieldValue, value);
            case "lessOrEqual":
                return compareLessOrEqual(fieldValue, value);
            default:
                return false;
        }
    }

    /**
     * Obtiene el valor de un campo específico de la agencia.
     *
     * @param agencia Agencia de la cual obtener el valor
     * @param field Campo a obtener
     * @return Valor del campo
     */
    private Object getFieldValue(Agencia agencia, String field) {
        switch (field) {
            case "agCod":
                return agencia.getAgCod();
            case "agNombreFantasia":
                return agencia.getAgNombreFantasia();
            case "parTipoAgencia":
                return agencia.getParTipoAgencia();
            case "agEstado":
                return agencia.getAgEstado();
            case "parBloqueo":
                return agencia.getParBloqueo();
            case "paCod":
                return agencia.getPaCod();
            case "ciuCod":
                return agencia.getCiuCod();
            case "bcod":
                return agencia.getBCod();
            case "agAgenciaPadre":
                return agencia.getAgAgenciaPadre();
            case "parRubro":
                return agencia.getParRubro();
            case "agSucursal":
                return agencia.getAgSucursal();
            case "agEsUnipersonal":
                return agencia.getAgEsUnipersonal();
            case "agEsMovil":
                return agencia.getAgEsMovil();
            case "agDireccion":
                return agencia.getAgDireccion();
            case "agTelefono":
                return agencia.getAgTelefono();
            case "agTitularCuenta":
                return agencia.getAgTitularCuenta();
            case "parBanco":
                return agencia.getParBanco();
            case "agSap":
                return agencia.getAgSap();
            case "agFacCredito":
                return agencia.getAgFacCredito();
            case "agNota":
                return agencia.getAgNota();
            case "agFechaCreacion":
                return agencia.getAgFechaCreacion();
            case "agFechaMod":
                return agencia.getAgFechaMod();
            default:
                return null;
        }
    }

// Métodos de comparación

    private boolean compareEquals(Object fieldValue, String value) {
        if (fieldValue == null) return value == null || value.isEmpty();
        return fieldValue.toString().toLowerCase().equals(value.toLowerCase());
    }

    private boolean compareContains(Object fieldValue, String value) {
        if (fieldValue == null) return false;
        return fieldValue.toString().toLowerCase().contains(value.toLowerCase());
    }

    private boolean compareStartsWith(Object fieldValue, String value) {
        if (fieldValue == null) return false;
        return fieldValue.toString().toLowerCase().startsWith(value.toLowerCase());
    }

    private boolean compareEndsWith(Object fieldValue, String value) {
        if (fieldValue == null) return false;
        return fieldValue.toString().toLowerCase().endsWith(value.toLowerCase());
    }

    private boolean compareGreaterThan(Object fieldValue, String value) {
        if (fieldValue == null) return false;

        if (fieldValue instanceof Number && isNumeric(value)) {
            return ((Number) fieldValue).doubleValue() > Double.parseDouble(value);
        } else if (fieldValue instanceof Date && isDate(value)) {
            try {
                Date dateValue = new SimpleDateFormat("yyyy-MM-dd").parse(value);
                return ((Date) fieldValue).after(dateValue);
            } catch (ParseException e) {
                return false;
            }
        }

        return false;
    }

    private boolean compareLessThan(Object fieldValue, String value) {
        if (fieldValue == null) return false;

        if (fieldValue instanceof Number && isNumeric(value)) {
            return ((Number) fieldValue).doubleValue() < Double.parseDouble(value);
        } else if (fieldValue instanceof Date && isDate(value)) {
            try {
                Date dateValue = new SimpleDateFormat("yyyy-MM-dd").parse(value);
                return ((Date) fieldValue).before(dateValue);
            } catch (ParseException e) {
                return false;
            }
        }

        return false;
    }

    private boolean compareGreaterOrEqual(Object fieldValue, String value) {
        return compareGreaterThan(fieldValue, value) || compareEquals(fieldValue, value);
    }

    private boolean compareLessOrEqual(Object fieldValue, String value) {
        return compareLessThan(fieldValue, value) || compareEquals(fieldValue, value);
    }

// Métodos auxiliares

    private boolean isNumeric(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDate(String value) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


}
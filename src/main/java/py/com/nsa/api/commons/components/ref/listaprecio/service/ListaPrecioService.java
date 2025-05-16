package py.com.nsa.api.commons.components.ref.listaprecio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.listaprecio.model.ListaPrecio;
import py.com.nsa.api.commons.components.ref.listaprecio.repository.ListaPrecioRepository;
import py.com.nsa.api.commons.components.ref.producto.repository.ProductoRepository;
import py.com.nsa.api.commons.components.cfg.agencia.repository.AgenciaRepository;

import py.com.nsa.api.commons.components.ref.listaprecio.dto.ListaPrecioAdvancedFilterRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ListaPrecioService {

    private static final Logger logger = LoggerFactory.getLogger(ListaPrecioService.class);

    @Autowired
    private ListaPrecioRepository listaPrecioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private AgenciaRepository agenciaRepository;

    public ListaPrecio.MensajeRespuesta getListaPreciosAll() {
        try {
            List<ListaPrecio> listaPrecios = listaPrecioRepository.findAll();
            if (listaPrecios.isEmpty()) {
                return new ListaPrecio.MensajeRespuesta(204L, "No se encontraron listas de precios.", null);
            }
            return new ListaPrecio.MensajeRespuesta(200L, "Listas de precios obtenidas exitosamente.", listaPrecios);
        } catch (Exception e) {
            logger.error("Error al obtener listas de precios: ", e);
            return new ListaPrecio.MensajeRespuesta(500L, "Error al obtener listas de precios: " + e.getMessage(), null);
        }
    }

    public ListaPrecio.MensajeRespuesta insertarListaPrecio(ListaPrecio listaPrecio) {
        try {
            // Validar existencia del producto
            if (!productoRepository.existsById(listaPrecio.getProCod())) {
                return new ListaPrecio.MensajeRespuesta(404L, "No existe un producto con el código: " + listaPrecio.getProCod(), null);
            }

            // Validar existencia de la agencia si está especificada
            if (listaPrecio.getAgCod() != null && !agenciaRepository.existsById(listaPrecio.getAgCod())) {
                return new ListaPrecio.MensajeRespuesta(404L, "No existe una agencia con el código: " + listaPrecio.getAgCod(), null);
            }

            // Verificar solapamiento de fechas
            List<ListaPrecio> overlappingPrices = listaPrecioRepository.findOverlappingPrices(
                    listaPrecio.getProCod(),
                    listaPrecio.getAgCod(),
                    listaPrecio.getParListaPrecio(),
                    listaPrecio.getParMoneda(),
                    listaPrecio.getLpInicio(),
                    listaPrecio.getLpFin()
            );

            if (!overlappingPrices.isEmpty()) {
                return new ListaPrecio.MensajeRespuesta(409L, "Ya existe una lista de precios con la misma combinación de agencia, lista y moneda en un rango de fechas solapado.", null);
            }

            // Establecer fecha de última modificación
            listaPrecio.setLpUltMod(new Date());

            ListaPrecio nuevaListaPrecio = listaPrecioRepository.save(listaPrecio);
            return new ListaPrecio.MensajeRespuesta(200L, "Lista de precios creada exitosamente.", List.of(nuevaListaPrecio));
        } catch (Exception e) {
            logger.error("Error al insertar la lista de precios: ", e);
            return new ListaPrecio.MensajeRespuesta(500L, "Error al insertar la lista de precios: " + e.getMessage(), null);
        }
    }

    public ListaPrecio.MensajeRespuesta updateListaPrecio(ListaPrecio listaPrecio) {
        try {
            // Validar si la lista de precios existe
            if (listaPrecio.getLpCod() == null || !listaPrecioRepository.existsById(listaPrecio.getLpCod())) {
                return new ListaPrecio.MensajeRespuesta(404L, "Lista de precios no encontrada.", null);
            }

            // Validar existencia del producto
            if (!productoRepository.existsById(listaPrecio.getProCod())) {
                return new ListaPrecio.MensajeRespuesta(404L, "No existe un producto con el código: " + listaPrecio.getProCod(), null);
            }

            // Validar existencia de la agencia si está especificada
            if (listaPrecio.getAgCod() != null && !agenciaRepository.existsById(listaPrecio.getAgCod())) {
                return new ListaPrecio.MensajeRespuesta(404L, "No existe una agencia con el código: " + listaPrecio.getAgCod(), null);
            }

            // Verificar solapamiento de fechas (excluyendo el registro actual)
            List<ListaPrecio> overlappingPrices = listaPrecioRepository.findOverlappingPrices(
                    listaPrecio.getProCod(),
                    listaPrecio.getAgCod(),
                    listaPrecio.getParListaPrecio(),
                    listaPrecio.getParMoneda(),
                    listaPrecio.getLpInicio(),
                    listaPrecio.getLpFin()
            );
            overlappingPrices.removeIf(lp -> lp.getLpCod().equals(listaPrecio.getLpCod()));

            if (!overlappingPrices.isEmpty()) {
                return new ListaPrecio.MensajeRespuesta(409L, "Ya existe otra lista de precios con la misma combinación de agencia, lista y moneda en un rango de fechas solapado.", null);
            }

            // Actualizar fecha de última modificación
            listaPrecio.setLpUltMod(new Date());

            ListaPrecio listaPrecioActualizada = listaPrecioRepository.save(listaPrecio);
            return new ListaPrecio.MensajeRespuesta(200L, "Lista de precios actualizada exitosamente.", List.of(listaPrecioActualizada));
        } catch (Exception e) {
            logger.error("Error al actualizar la lista de precios: ", e);
            return new ListaPrecio.MensajeRespuesta(500L, "Error al actualizar la lista de precios: " + e.getMessage(), null);
        }
    }

    public ListaPrecio.MensajeRespuesta deleteListaPrecio(Long lpCod) {
        try {
            if (!listaPrecioRepository.existsById(lpCod)) {
                return new ListaPrecio.MensajeRespuesta(404L, "No se encontró la lista de precios.", null);
            }

            listaPrecioRepository.deleteById(lpCod);
            return new ListaPrecio.MensajeRespuesta(200L, "Lista de precios eliminada exitosamente.", null);
        } catch (Exception e) {
            logger.error("Error al eliminar la lista de precios: ", e);
            return new ListaPrecio.MensajeRespuesta(500L, "Error al eliminar la lista de precios: " + e.getMessage(), null);
        }
    }

    //Funciones para filtro avanzado
    /**
     * Aplica filtros avanzados a las listas de precios.
     *
     * @param filterRequest Solicitud de filtros avanzados
     * @return Lista de listas de precios filtradas
     */
    public List<ListaPrecio> applyAdvancedFilters(ListaPrecioAdvancedFilterRequest filterRequest) {
        List<ListaPrecio> allListaPrecios = listaPrecioRepository.findAll();
        List<ListaPrecio> filteredListaPrecios = new ArrayList<>();

        // Para cada lista de precio, evaluamos si cumple con todos los grupos de filtros
        for (ListaPrecio listaPrecio : allListaPrecios) {
            boolean matchesAllGroups = true;

            // Evaluamos cada grupo de filtros
            for (ListaPrecioAdvancedFilterRequest.FilterGroup group : filterRequest.getFilterGroups()) {
                boolean matchesGroup = evaluateFilterGroup(listaPrecio, group);

                if (!matchesGroup) {
                    matchesAllGroups = false;
                    break;
                }
            }

            if (matchesAllGroups) {
                filteredListaPrecios.add(listaPrecio);
            }
        }

        return filteredListaPrecios;
    }

    /**
     * Evalúa si una lista de precio cumple con un grupo de filtros.
     *
     * @param listaPrecio Lista de precio a evaluar
     * @param group Grupo de filtros a aplicar
     * @return true si la lista de precio cumple con el grupo de filtros
     */
    private boolean evaluateFilterGroup(ListaPrecio listaPrecio, ListaPrecioAdvancedFilterRequest.FilterGroup group) {
        // Si no hay condiciones, el grupo se considera verdadero
        if (group.getConditions() == null || group.getConditions().isEmpty()) {
            return true;
        }

        boolean isAndOperator = "AND".equalsIgnoreCase(group.getOperator());

        // Para operador AND, todas las condiciones deben ser verdaderas
        // Para operador OR, al menos una condición debe ser verdadera
        boolean result = isAndOperator; // true para AND (valor inicial), false para OR (valor inicial)

        for (ListaPrecioAdvancedFilterRequest.FilterCondition condition : group.getConditions()) {
            // Si el campo está vacío, omitimos esta condición
            if (condition.getField() == null || condition.getField().isEmpty()) {
                continue;
            }

            boolean conditionResult = evaluateCondition(listaPrecio, condition);

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
     * Evalúa una condición individual para una lista de precio.
     *
     * @param listaPrecio Lista de precio a evaluar
     * @param condition Condición de filtrado
     * @return true si la condición se cumple
     */
    private boolean evaluateCondition(ListaPrecio listaPrecio, ListaPrecioAdvancedFilterRequest.FilterCondition condition) {
        String field = condition.getField();
        String comparison = condition.getComparison();
        String value = condition.getValue();

        // Si el valor de comparación está vacío, omitimos esta condición
        if (value == null || value.isEmpty()) {
            return true;
        }

        // Obtener el valor del campo en la lista de precio
        Object fieldValue = getFieldValue(listaPrecio, field);
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
     * Obtiene el valor de un campo específico de la lista de precio.
     *
     * @param listaPrecio Lista de precio de la cual obtener el valor
     * @param field Campo a obtener
     * @return Valor del campo
     */
    private Object getFieldValue(ListaPrecio listaPrecio, String field) {
        switch (field) {
            case "lpCod":
                return listaPrecio.getLpCod();
            case "proCod":
                return listaPrecio.getProCod();
            case "parListaPrecio":
                return listaPrecio.getParListaPrecio();
            case "agCod":
                return listaPrecio.getAgCod();
            case "parMoneda":
                return listaPrecio.getParMoneda();
            case "lpPrecio":
                return listaPrecio.getLpPrecio();
            case "lpInicio":
                return listaPrecio.getLpInicio();
            case "lpFin":
                return listaPrecio.getLpFin();
            case "lpUltMod":
                return listaPrecio.getLpUltMod();
            case "usuCod":
                return listaPrecio.getUsuCod();
            default:
                return null;
        }
    }

    // Métodos de comparación

    private boolean compareEquals(Object fieldValue, String value) {
        if (fieldValue == null) return value == null || value.isEmpty();

        if (fieldValue instanceof BigDecimal) {
            try {
                return ((BigDecimal) fieldValue).compareTo(new BigDecimal(value)) == 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }

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

        if (fieldValue instanceof BigDecimal) {
            try {
                return ((BigDecimal) fieldValue).compareTo(new BigDecimal(value)) > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (fieldValue instanceof Number && isNumeric(value)) {
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

        if (fieldValue instanceof BigDecimal) {
            try {
                return ((BigDecimal) fieldValue).compareTo(new BigDecimal(value)) < 0;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (fieldValue instanceof Number && isNumeric(value)) {
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
            new SimpleDateFormat ("yyyy-MM-dd").parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
package py.com.nsa.api.commons.components.ref.listaprecio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Estructura de datos para manejar filtros avanzados de listas de precios.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaPrecioAdvancedFilterRequest {

    /**
     * Lista de grupos de filtros.
     */
    private List<FilterGroup> filterGroups;

    /**
     * Grupo de filtros que contiene condiciones.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilterGroup {
        /**
         * Identificador del grupo.
         */
        private Long id;

        /**
         * Operador lógico para aplicar entre condiciones (AND/OR).
         */
        private String operator;

        /**
         * Lista de condiciones en el grupo.
         */
        private List<FilterCondition> conditions;
    }

    /**
     * Condición individual de filtrado.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilterCondition {
        /**
         * Identificador de la condición.
         */
        private Long id;

        /**
         * Campo a filtrar.
         */
        private String field;

        /**
         * Operador de comparación.
         */
        private String comparison;

        /**
         * Valor a comparar.
         */
        private String value;
    }
}
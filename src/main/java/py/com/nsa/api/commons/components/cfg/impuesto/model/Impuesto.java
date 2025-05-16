package py.com.nsa.api.commons.components.cfg.impuesto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CFG_IMPUESTO", schema = "GNSAT")
public class Impuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMP_COD")
    @JsonProperty("impCod")
    private Long impCod;

    @NotBlank(message = "La descripción del impuesto no puede estar vacía")
    @Size(max = 10, message = "La descripción no puede exceder los 10 caracteres")
    @Column(name = "IMP_DESCRIPCION")
    @JsonProperty("impDescripcion")
    private String impDescripcion;

    @NotNull(message = "El porcentaje del impuesto no puede ser nulo")
    @DecimalMin(value = "0.00", message = "El porcentaje mínimo es 0.00")
    @DecimalMax(value = "100.00", message = "El porcentaje máximo es 100.00")
    @Column(name = "IMP_PORCENTAJE", precision = 5, scale = 2)
    @JsonProperty("impPorcentaje")
    private BigDecimal impPorcentaje;

    @Size(max = 10, message = "El código SAP no puede exceder los 10 caracteres")
    @Column(name = "IMP_CODSAP")
    @JsonProperty("impCodsap")
    private String impCodsap;

    @NotNull(message = "La base nominal no puede ser nula")
    @DecimalMin(value = "0.00", message = "La base nominal mínima es 0.00")
    @DecimalMax(value = "100.00", inclusive = true, message = "La base nominal máxima es 100.00")
    @Column(name = "IMP_BASENOMINAL", precision = 5, scale = 2)
    @JsonProperty("impBasenominal")
    private BigDecimal impBasenominal;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Impuesto> impuestos;
    }
}

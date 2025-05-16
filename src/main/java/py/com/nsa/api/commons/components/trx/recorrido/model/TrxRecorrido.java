package py.com.nsa.api.commons.components.trx.recorrido.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import py.com.nsa.api.commons.components.ref.parada.model.Parada;
import py.com.nsa.api.commons.components.trx.viaje.model.Viaje;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TRX_RECORRIDO", schema = "GNSAT")
public class TrxRecorrido {

    @EmbeddedId
    private TrxRecorridoId id;

    @Column(name = "R_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("rHora")
    private Date rHora;

    @Column(name = "R_ESTADO", length = 1)
    @JsonProperty("rEstado")
    private String rEstado;

    @Column(name = "R_TARIFAGS")
    @JsonProperty("rTarifaGs")
    private BigDecimal rTarifaGs;

    @Column(name = "R_TARIFAARS")
    @JsonProperty("rTarifaArs")
    private BigDecimal rTarifaArs;

    @Column(name = "R_TARIFARS")
    @JsonProperty("rTarifaRs")
    private BigDecimal rTarifaRs;

    @Column(name = "RT_TARIFAUSD")
    @JsonProperty("rtTarifaUsd")
    private BigDecimal rtTarifaUsd;

    @ManyToOne
    @JoinColumn(name = "V_COD", referencedColumnName = "V_COD", insertable = false, updatable = false)
    private Viaje viaje;

    @ManyToOne
    @JoinColumn(name = "PARA_COD", referencedColumnName = "PARA_COD", insertable = false, updatable = false)
    private Parada parada;

    // Nueva clase estática MensajeRespuesta
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<TrxRecorrido> recorridos;
    }
}
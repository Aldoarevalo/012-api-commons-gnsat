package py.com.nsa.api.commons.components.ref.tarifaruta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.parada.model.Parada;
import py.com.nsa.api.commons.components.ref.rutadet.model.RutaDet;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.ref.rutadet.model.pk.RutaDetPK;
import py.com.nsa.api.commons.components.ref.tarifaruta.model.pk.TarifaRutaPK;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_TARIFA_RUTA", schema = "GNSAT")
@IdClass(value = TarifaRutaPK.class) // Clase que representa la clave primaria compuesta
public class TarifaRuta {

    @Id
    @Column(name = "RUC_COD")
    private Long rucCod;

    @Id
    @Column(name = "RUD_SECUENCIA")
    private Long rudSecuencia;

    @Column(name = "PARA_COD", nullable = false)
    private Long paraCod;

    @Column(name = "RT_TARIFAGS", precision = 10, scale = 2)
    private BigDecimal rtTarifaGs;

    @Column(name = "RT_TARIFAARS", precision = 10, scale = 2)
    private BigDecimal rtTarifaArs;

    @Column(name = "RT_TARIFARS", precision = 10, scale = 2)
    private BigDecimal rtTarifaRs;

    @Column(name = "RT_TARIFAUSD", nullable = false, precision = 10, scale = 2)
    private BigDecimal rtTarifaUsd;

    @Column(name = "USU_COD", nullable = false)
    private Long usuCod;

    @Column(name = "RT_ULTMOD", nullable = false)
    // @Temporal(TemporalType.DATE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date rtUltmod;

    // Relationships
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "RUC_COD", referencedColumnName = "RUC_COD", insertable = false, updatable = false),
            @JoinColumn(name = "RUD_SECUENCIA", referencedColumnName = "RUD_SECUENCIA", insertable = false, updatable = false)
    })
    private RutaDet rutaDet;

    @ManyToOne
    @JoinColumn(name = "USU_COD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "PARA_COD", referencedColumnName = "PARA_COD", insertable = false, updatable = false)
    private Parada parada;

    // Transient field to accept a list of TarifaRuta records in the request
    @Transient
    private List<TarifaRuta> tarifas;

    // Response class
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<TarifaRuta> tarifasRuta;
    }
}
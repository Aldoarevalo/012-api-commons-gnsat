package py.com.nsa.api.commons.components.cfg.limiteagencia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.limiteagencia.model.pk.LimiteAgenciaPK;
import py.com.nsa.api.commons.components.cfg.tipo_limite.model.TipoLimite;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_LIMITE_AGENCIA", schema = "GNSAT")
@IdClass(value = LimiteAgenciaPK.class)
public class LimiteAgencia {

    @Id
    @Column(name = "AG_COD", nullable = false)
    private Long agCod;

    @Id
    @Column(name = "TL_COD_LIMITE", nullable = false)
    private Long tlCodLimite;

    @Column(name = "LA_MONTO", precision = 38, scale = 2)
    private BigDecimal laMonto;

    @Column(name = "LA_CANTIDAD")
    private Long laCantidad;

    @Column(name = "USU_CODCREADOR", nullable = false)
    private Long usuCodCreador;

    @Column(name = "USU_CODMOD")
    private Long usuCodMod;

    @Column(name = "LA_FECHA_CREACION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date laFechaCreacion;

    @Column(name = "LA_FECHAMOD")
    @Temporal(TemporalType.DATE)
    private Date laFechaMod;

    @ManyToOne
    @JoinColumn(name = "AG_COD", referencedColumnName = "AG_COD", insertable = false, updatable = false)
    private Agencia agencia;

    @ManyToOne
    @JoinColumn(name = "TL_COD_LIMITE", referencedColumnName = "TL_COD", insertable = false, updatable = false)
    private TipoLimite tipolimite;

    @ManyToOne
    @JoinColumn(name = "USU_CODCREADOR", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuarioCreador;

    @ManyToOne
    @JoinColumn(name = "USU_CODMOD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuarioModificador;

    @Transient
    private Long agCodold;

    @Transient
    private Long tlCodold;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<LimiteAgencia> limiteAgencia;
    }
}

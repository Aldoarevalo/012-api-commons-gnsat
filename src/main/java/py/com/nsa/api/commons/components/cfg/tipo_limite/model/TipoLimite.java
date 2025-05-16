package py.com.nsa.api.commons.components.cfg.tipo_limite.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_TIPO_LIMITE", schema = "GNSAT")
public class TipoLimite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "limite_generator")
    @SequenceGenerator(name = "limite_generator", sequenceName = "GNSAT.SEQ_CFG_TIPO_LIMITE", allocationSize = 1)
    @Column(name = "TL_COD")
    private Long tlCod;

    @Column(name = "TL_DESCRIPCION", nullable = false, length = 30)
    private String tlDescripcion;

    @Column(name = "TL_ES_OBLIGATORIO", nullable = false, length = 1)
    private String tlEsObligatorio;

    @Column(name = "PAR_MONEDA", length = 4)
    private String parMoneda;

    @Column(name = "PAR_SERVICIO", nullable = false, length = 4)
    private String parServicio;

    @Column(name = "PA_COD", nullable = false)
    private Long paCod;

    @Column(name = "TL_CONTROL_TIEMPO", length = 4)
    private String tlControlTiempo;

    @Column(name = "TL_NOTIFICAR", nullable = false)
    private Long tlNotificar;

    @Column(name = "TL_TIPOCONTROL", nullable = false, length = 4)
    private String tlTipocontrol;

    @Column(name = "TL_ACCION", nullable = false, length = 4)
    private String tlAccion;

    @Column(name = "TL_ESTADO", nullable = false, length = 1)
    private String tlEstado;

    @Column(name = "PAR_TIEMPO_CONTROL", nullable = false, length = 4)
    private String parTiempoControl;

    @Column(name = "TL_TOPE", nullable = false)
    private Long tlTope;

    @Column(name = "USU_CREADOR", nullable = false)
    private Long usuCreador;

    @Column(name = "USU_MOD")
    private Long usuMod;

    @Column(name = "TL_FECHACREACION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date tlFechacreacion;

    @Column(name = "TL_FECHAMOD")
    @Temporal(TemporalType.DATE)
    private Date tlFechamod;

    @ManyToOne
    @JoinColumn(name = "PA_COD", referencedColumnName = "PA_COD", insertable = false, updatable = false)
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "PAR_MONEDA", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parMonedaValor;

    @ManyToOne
    @JoinColumn(name = "PAR_SERVICIO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parServicioValor;

    @ManyToOne
    @JoinColumn(name = "PAR_TIEMPO_CONTROL", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parTiempoControlValor;

    @ManyToOne
    @JoinColumn(name = "TL_TIPOCONTROL", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor tlTipocontrolValor;

    @ManyToOne
    @JoinColumn(name = "TL_ACCION", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor tlAccionValor;

    @ManyToOne
    @JoinColumn(name = "USU_CREADOR", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuCreadorRef;

    @ManyToOne
    @JoinColumn(name = "USU_MOD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuModRef;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<TipoLimite> tipolimites;
    }
}
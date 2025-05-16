package py.com.nsa.api.commons.components.cfg.servicio_agencia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.servicio_agencia.model.pk.ServicioAgenciaPK;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_SERVICIO_AGENCIA", schema = "GNSAT")
@IdClass(value = ServicioAgenciaPK.class)
public class ServicioAgencia {

    @Id
    @Column(name = "PAR_SERVICIO", nullable = false, length = 4)
    private String parServicio;

    @Id
    @Column(name = "AG_COD", nullable = false)
    private Long agCod;

    @Column(name = "SA_COMI_PORCEN")
    private Long saComiPorcen;

    @Column(name = "SA_COMI_FIJO", precision = 38, scale = 2)
    private BigDecimal saComiFijo;

    @Column(name = "SA_MONEDA", length = 4)
    private String saMoneda;

    @Column(name = "USU_COD_CREADOR", nullable = false)
    private Long usuCodCreador;

    @Column(name = "USU_COD_MOD")
    private Long usuCodMod;

    @Column(name = "SA_FECHACREA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date saFechaCrea;

    @Column(name = "SA_FECHAMOD")
    @Temporal(TemporalType.DATE)
    private Date saFechaMod;

    @ManyToOne
    @JoinColumn(name = "AG_COD", referencedColumnName = "AG_COD", insertable = false, updatable = false)
    private Agencia agencia;

    @ManyToOne
    @JoinColumn(name = "SA_MONEDA", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor moneda;

    @ManyToOne
    @JoinColumn(name = "PAR_SERVICIO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor servicio;

    @ManyToOne
    @JoinColumn(name = "USU_COD_CREADOR", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuarioCreador;

    @ManyToOne
    @JoinColumn(name = "USU_COD_MOD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuarioModificador;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<ServicioAgencia> servicioAgencias;
    }
}

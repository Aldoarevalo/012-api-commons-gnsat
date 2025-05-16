package py.com.nsa.api.commons.components.cfg.cliente_limite.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.cliente.model.Cliente;
import py.com.nsa.api.commons.components.cfg.cliente_limite.model.pk.ClienteLimitePK;
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
@Table(name = "CFG_CLIENTE_LIMITE", schema = "GNSAT")
@IdClass(value = ClienteLimitePK.class)
public class ClienteLimite {

    @Id
    @Column(name = "C_CODCLIENTE", nullable = false)
    private Long cCodCliente;

    @Id
    @Column(name = "TL_COD", nullable = false)
    private Long tlCod;

    @Column(name = "C_ESTADO", length = 1, nullable = false)
    private String cEstado;

    @Column(name = "CLI_MONTO")
    private BigDecimal cliMonto;

    @Column(name = "CLI_CANT_TRANSACC")
    private Long cliCantTransacc;

    @Column(name = "CLI_NOMBRE", length = 20, nullable = false)
    private String cliNombre;

    @Column(name = "USU_GRAB", nullable = false)
    private Long usuGrab;

    @Column(name = "USU_MOD", nullable = false)
    private Long usuMod;

    @Column(name = "CLI_FECHAGRAB", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date cliFechaGrab;

    @Column(name = "CLI_FECHAMOD", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date cliFechaMod;

    @ManyToOne
    @JoinColumn(name = "C_CODCLIENTE", referencedColumnName = "C_COD", insertable = false, updatable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "TL_COD", referencedColumnName = "TL_COD", insertable = false, updatable = false)
    private TipoLimite tipoLimite;

    @ManyToOne
    @JoinColumn(name = "USU_GRAB", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuarioCreador;

    @ManyToOne
    @JoinColumn(name = "USU_MOD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuarioModificador;

    @Transient
    private Long tlCodold;

    @Transient
    private Long cCodClienteold;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<ClienteLimite> clienteLimites;
    }
}
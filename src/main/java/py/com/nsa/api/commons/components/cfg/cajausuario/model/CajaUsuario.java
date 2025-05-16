package py.com.nsa.api.commons.components.cfg.cajausuario.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.cajaagencia.model.CajaAgencia;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_CAJA_USUARIO", schema = "GNSAT")
public class CajaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "caja_usuario_generator")
    @SequenceGenerator(name = "caja_usuario_generator", sequenceName = "GNSAT.SEQ_CFG_CAJA_USUARIO", allocationSize = 1)
    @Column(name = "CJU_COD_CJ_USU")
    private Long id;

    @Column(name = "CJU_COD_AGENCIA")
    private Long agencia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CJU_COD_AGENCIA", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Agencia agenciaEntity;

    @Column(name = "CJU_COD_USUARIO")
    private Long usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CJU_COD_USUARIO", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuarioEntity;

    @Column(name = "CJU_SALDO_U", precision = 22, scale = 2, nullable = false)
    private BigDecimal saldo;

    @Column(name = "CJU_MONEDA", length = 4, nullable = false)
    private String moneda;

    @Column(name = "CJU_OPERACION", length = 1, nullable = false)
    private String operacion;

    @Column(name = "CJU_FECHA_OP")
    @Temporal(TemporalType.DATE)
    private Date fechaOp;

    @Column(name = "CJU_USUARIO_OP")
    private Long usuarioOp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CJU_USUARIO_OP", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuarioOperador;

    @Column(name = "CJU_COD_CJ_AG")
    private Long caja;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CJU_COD_CJ_AG", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CajaAgencia cajaAgencia;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private java.util.List<CajaUsuario> cajaUsuarios;
    }
}
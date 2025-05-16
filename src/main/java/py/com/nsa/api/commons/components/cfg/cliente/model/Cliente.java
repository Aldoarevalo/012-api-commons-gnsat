package py.com.nsa.api.commons.components.cfg.cliente.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.tipo_limite.model.TipoLimite;
import py.com.nsa.api.commons.components.ref.persona.model.Persona;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_CLIENTE", schema = "GNSAT")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cfg_cliente_generator")
    @SequenceGenerator(name = "cfg_cliente_generator", sequenceName = "GNSAT.SEQ_CFG_CLIENTE", allocationSize = 1)
    @Column(name = "C_COD")
    private Long cCod;

    @Column(name = "P_COD")
    private Long pCod;


    @Column(name = "C_RUC", length = 20, nullable = false)
    private String cRuc;

    @Column(name = "C_TEL_CONTACTO", length = 20, nullable = false)
    private String cTelContacto;

    @Column(name = "C_ES_CORRENTISTA", length = 1, nullable = false)
    private String cEsCorrentista;

    @Column(name = "C_LIMITE_NACIONAL")
    private Long cLimiteNacional;

    @Column(name = "C_LIMITE_WU", precision = 22, scale = 2, columnDefinition = "NUMBER(22,2)")
    private BigDecimal cLimiteWu;

    @Column(name = "C_TERMINO_PAGO", nullable = false)
    private Long cTerminoPago;

    @Column(name = "C_ESTADO", nullable = false)
    private Long cEstado;

    @Column(name = "C_FECHA_CREACION", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date cFechaCreacion;

    @Column(name = "C_USU_CREACION", nullable = false)
    private Long cUsuCreacion;

    @Column(name = "C_USU_MODIFICACION")
    private Long cUsuModificacion;

    @Column(name = "C_FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cFechaModificacion;

    @Column(name = "C_OCUPACION", length = 4, nullable = false)
    private String cOcupacion;

    @Column(name = "C_CATEGORIA", length = 4, nullable = false)
    private String cCategoria;

    @ManyToOne
    @JoinColumn(name = "P_COD", referencedColumnName = "P_COD", insertable = false, updatable = false)
    private Persona persona;


    @Transient
    private String pdireccion;

    @Transient
    private String ptelefono;

    @Transient
    private Long bCod;

    @Transient
    private Long paCod;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;  // "ok", "info", "error"
        private String mensaje; // Mensaje descriptivo
        private List<Cliente> clientes; // Lista de clientes (opcional)
    }
}
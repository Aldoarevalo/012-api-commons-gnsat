package py.com.nsa.api.commons.components.cfg.agencia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.barrio.model.Barrio;
import py.com.nsa.api.commons.components.cfg.ciudad.model.Ciudad;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.ref.persona.model.Persona;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_AGENCIA", schema = "GNSAT")
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agencia_generator")
    @SequenceGenerator(name = "agencia_generator", sequenceName = "GNSAT.SEQ_CFG_AGENCIA", allocationSize = 1)
    @Column(name = "AG_COD", nullable = false)
    private Long agCod;

    @Column(name = "P_COD_AGENCIA", nullable = false)
    private Long pCodAgencia;

    @Column(name = "P_COD_ENCARGADO", nullable = false)
    private Long pCodEncargado;

    @Column(name = "P_COD_REP_LEGAL")
    private Long pCodRepLegal;

    @Column(name = "AG_NOMBRE_FANTASIA", length = 20)
    private String agNombreFantasia;

    @Column(name = "AG_SUCURSAL", nullable = false, length = 1)
    private String agSucursal;

    @Column(name = "AG_ARQUEO_AUTOMATICO", nullable = false, length = 1)
    private String agArqueoAutomatico;

    @Column(name = "PAR_CAPTACION", nullable = false, length = 4)
    private String parCaptacion;

    @Column(name = "PAR_COD_FONDEO", nullable = false, length = 4)
    private String parCodFondeo;

    @Column(name = "PAR_TIPOAGENCIA", nullable = false, length = 4)
    private String parTipoAgencia;

    @Column(name = "AG_COUNTER")
    private String agCounter;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss")
    @Column(name = "AG_HORA_CIERRE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date agHoraCierre;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss")
    @Column(name = "AG_HORA_APERTURA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date agHoraApertura;

    @Column(name = "PAR_RUBRO", nullable = false, length = 4)
    private String parRubro;

    @Column(name = "AG_ES_UNIPERSONAL", nullable = false, length = 1)
    private String agEsUnipersonal;

    @Column(name = "AG_CUENTA_BANCARIA", nullable = false, length = 20)
    private String agCuentaBancaria;

    @Column(name = "PAR_RIESGO", nullable = false, length = 4)
    private String parRiesgo;

    @Column(name = "AG_AGENCIA_PADRE")
    private Long agAgenciaPadre;

    @Column(name = "AG_CUENTA_WESTERN", length = 20)
    private String agCuentaWestern;

    @Column(name = "AG_ES_MOVIL", nullable = false, length = 1)
    private String agEsMovil;

    @Column(name = "AG_LATITUD")
    private Float agLatitud;

    @Column(name = "AG_LONGITUD")
    private Float agLongitud;

    @Column(name = "AG_CAP_ENCO")
    private Float agCapEnco;

    @Column(name = "AG_DIRECCION", nullable = false, length = 100)
    private String agDireccion;

    @Column(name = "B_COD", nullable = false)
    private Long bCod;

    @Column(name = "PA_COD", nullable = false)
    private Long paCod;

    @Column(name = "CIU_COD", nullable = false)
    private Long ciuCod;

    @Column(name = "AG_TELEFONO", nullable = false, length = 20)
    private String agTelefono;

    @Column(name = "AG_ESTADO", nullable = false, length = 1)
    private String agEstado;

    @Column(name = "PAR_BLOQUEO", nullable = false, length = 4)
    private String parBloqueo;

    @Column(name = "AG_TITULAR_CUENTA", nullable = false, length = 60)
    private String agTitularCuenta;

    @Column(name = "PAR_BANCO", nullable = false, length = 4)
    private String parBanco;

    @Column(name = "AG_COD_SAP", nullable = false, length = 10)
    private String agSap;

    @Column(name = "AG_FACTURA_CREDITO", nullable = false, length = 1)
    private String agFacCredito;

    @Column(name = "AG_NOTA", length = 130)
    private String agNota;

    // New fields for user tracking
    @Column(name = "USU_COD_CREADOR", nullable = false)
    private Long usuCodCreador;

    @Column(name = "USU_COD_MOD")
    private Long usuCodMod;


    @Column(name = "AG_FECHACREACION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date agFechaCreacion;


    @Column(name = "AG_FECHAMOD")
    @Temporal(TemporalType.DATE)
    private Date agFechaMod;

    // Existing relationships
    @ManyToOne
    @JoinColumn(name = "PA_COD", referencedColumnName = "PA_COD", insertable = false, updatable = false)
    private Pais paisagencia;

    @ManyToOne
    @JoinColumn(name = "CIU_COD", referencedColumnName = "CIU_COD", insertable = false, updatable = false)
    private Ciudad ciudadagencia;

    @ManyToOne
    @JoinColumn(name = "B_COD", referencedColumnName = "B_COD", insertable = false, updatable = false)
    private Barrio barrioagencia;


    @ManyToOne
    @JoinColumn(name = "P_COD_AGENCIA", referencedColumnName = "P_COD", insertable = false, updatable = false)
    private Persona personaagencia;

    @ManyToOne
    @JoinColumn(name = "P_COD_ENCARGADO", referencedColumnName = "P_COD", insertable = false, updatable = false)
    private Persona encargado;

    @ManyToOne
    @JoinColumn(name = "P_COD_REP_LEGAL", referencedColumnName = "P_COD", insertable = false, updatable = false)
    private Persona representanteLegal;

    @ManyToOne
    @JoinColumn(name = "PAR_CAPTACION", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorCaptacion;

    @ManyToOne
    @JoinColumn(name = "PAR_COD_FONDEO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorFondeo;

    @ManyToOne
    @JoinColumn(name = "PAR_TIPOAGENCIA", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorTipoAgencia;

    @ManyToOne
    @JoinColumn(name = "PAR_RUBRO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorRubro;

    @ManyToOne
    @JoinColumn(name = "PAR_RIESGO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorRiesgo;

    @ManyToOne
    @JoinColumn(name = "PAR_BLOQUEO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorBloqueo;

    @ManyToOne
    @JoinColumn(name = "PAR_BANCO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorBanco;

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
        private List<Agencia> agencias;
    }
}
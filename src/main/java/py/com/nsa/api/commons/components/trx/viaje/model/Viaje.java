package py.com.nsa.api.commons.components.trx.viaje.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.empresa.model.Empresa;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import py.com.nsa.api.commons.components.ref.producto.model.Producto;
import py.com.nsa.api.commons.components.ref.rutacab.model.RutaCab;
import py.com.nsa.api.commons.components.ref.vehiculo.model.Vehiculo;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.trx.recorrido.model.TrxRecorrido;
import py.com.nsa.api.commons.components.trx.viajechofer.model.ViajeChofer;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TRX_VIAJE", schema = "GNSAT")
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "viaje_generator")
    @SequenceGenerator(name = "viaje_generator", sequenceName = "GNSAT.SEQ_TRX_VIAJE", allocationSize = 1)
    @Column(name = "V_COD", nullable = false)
    @JsonProperty("vCod")
    private Integer vCod;

    @Column(name = "V_FECHA", nullable = false)
    @JsonProperty("vFecha")
    private LocalDate vFecha;

    @Column(name = "RUC_COD", nullable = false)
    @JsonProperty("rucCod")
    private Long rucCod;

    @Column(name = "PRO_COD", nullable = false, length = 7)
    @JsonProperty("proCod")
    private String proCod;

    @Column(name = "V_EMPRESA", nullable = false)
    @JsonProperty("vEmpresa")
    private Long vEmpresa;

    @Column(name = "V_HORA", nullable = false, length = 8)
    @JsonProperty("vHora")
    private String vHora;

    @Column(name = "V_ESTADO", nullable = false, length = 1)
    @JsonProperty("vEstado")
    private String vEstado;

    @Column(name = "V_REFUERZO", nullable = false, length = 1)
    @JsonProperty("vRefuerzo")
    private String vRefuerzo;

    @Column(name = "V_DIA", nullable = false, length = 4)
    @JsonProperty("vDia")
    private String vDia;

    @Column(name = "V_DURACION", nullable = false, length = 8)
    @JsonProperty("vDuracion")
    private String vDuracion;

    @Column(name = "V_SERVICIO", nullable = false, length = 4)
    @JsonProperty("vServicio")
    private String vServicio;

    @Column(name = "V_VEHICULO")
    @JsonProperty("vVehiculo")
    private Long vVehiculo;

    @Column(name = "V_TIPOVIAJE", nullable = false, length = 1)
    @JsonProperty("vTipoviaje")
    private String vTipoviaje;

    @Builder.Default
    @Column(name = "V_CANJE", nullable = false)
    @JsonProperty("vCanje")
    private Integer vCanje = 3;

    @Column(name = "V_ULTMODIF", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonProperty("vUltmodif")
    private Date vUltmodif;

    @Column(name = "USU_COD", nullable = false)
    @JsonProperty("usuCod")
    private Long usuCod;

    ///Nuevo campo agregado
    @Column(name = "PAR_TARIFA", nullable = false)
    @JsonProperty("paraTipoTarifa")
    private String paraTipoTarifa;


    @Column(name = "V_FECHAREGISTRO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("vFechaRegistro")
    private Date vFechaRegistro;
    // Relaciones con otras entidades
    @ManyToOne
    @JoinColumn(name = "RUC_COD", referencedColumnName = "RUC_COD", insertable = false, updatable = false)
    private RutaCab rutaCab;

    @ManyToOne
    @JoinColumn(name = "PRO_COD", referencedColumnName = "PRO_COD", insertable = false, updatable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "V_EMPRESA", referencedColumnName = "EM_COD", insertable = false, updatable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "V_DIA", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor dia;

    @ManyToOne
    @JoinColumn(name = "V_SERVICIO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor servicio;

    @ManyToOne
    @JoinColumn(name = "V_VEHICULO", referencedColumnName = "VE_COD", insertable = false, updatable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "USU_COD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "PAR_TARIFA", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor tipotarifa;

    // Campos transitorios para manejar listas de choferes y recorridos
    @Transient
    private List<ViajeChofer> choferes;

    @Transient
    private List<TrxRecorrido> recorridos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Viaje> viajes;
    }
}
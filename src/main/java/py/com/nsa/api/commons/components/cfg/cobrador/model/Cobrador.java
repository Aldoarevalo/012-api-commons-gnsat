package py.com.nsa.api.commons.components.cfg.cobrador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.cobrador.model.pk.CobradorPK;
import py.com.nsa.api.commons.components.cfg.comisioncobrador.modelo.ComisionCobrador;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.ref.moneda.model.Moneda;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_COBRADOR", schema = "GNSAT")
@IdClass(CobradorPK.class)
public class Cobrador implements Serializable {

    @Id
    @Column(name = "COBR_CODIGO")
    private Long cobrCodigo;

    @Id
    @Column(name = "CAR_CODIGO")
    private Long carCodigo;

    @Column(name = "COBR_DESCRIPCION")
    private String cobrDescripcion;

    @Column(name = "COM_CODIGO")
    private Long comCodigo;

    @Column(name = "COBR_LIM_INF_COMIS")
    private Double cobrLimInfComis;

    @Column(name = "COBR_LIM_SUP_COMIS")
    private Double cobrLimSupComis;

    @Column(name = "COD_MONEDA_LIMITES")
    private Long codMonedaLimites;

    @Column(name = "USU_CODIGO")
    private Long usuCodigo;

    @Column(name = "MAC_NRO_PLAN_CUENTA")
    private Long macNroPlanCuenta;

    @Column(name = "MAC_COD_CAPITULO_ACTIVO")
    private Integer macCodCapituloActivo;

    @Column(name = "MAC_NRO_CUENTA_ACTIVO")
    private String macNroCuentaActivo;

    @Column(name = "MAC_COD_CAPITULO_PASIVO")
    private Integer macCodCapituloPasivo;

    @Column(name = "MAC_NRO_CUENTA_PASIVO")
    private String macNroCuentaPasivo;

    @ManyToOne
    @JoinColumn(name = "COM_CODIGO", referencedColumnName = "COM_CODIGO", insertable = false, updatable = false)
    private ComisionCobrador comisionCobrador;

    @ManyToOne
    @JoinColumn(name = "COD_MONEDA_LIMITES", referencedColumnName = "MON_CODIGO", insertable = false, updatable = false)
    private Moneda moneda;

    @ManyToOne
    @JoinColumn(name = "USU_COD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuario;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Cobrador> cobradores;
    }
}

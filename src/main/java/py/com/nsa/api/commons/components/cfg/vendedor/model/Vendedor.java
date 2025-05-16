package py.com.nsa.api.commons.components.cfg.vendedor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.cfg.vendedor.model.pk.VendedorPK;
import py.com.nsa.api.commons.components.ref.cargo.model.Cargo;


import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_VENDEDOR", schema = "GNSAT")
@IdClass(value = VendedorPK.class) // Clase que representa la clave primaria compuesta
public class Vendedor {

    @Id
    @Column(name = "VEND_CODIGO")
    private Long vendCodigo;

    @Id
    @Column(name = "CAR_CODIGO")
    private Long carCodigo;

    @Column(name = "VEND_DESCRIPCION")
    private String vendDescripcion;

    @Column(name = "VEND_LIM_INF_COMIS")
    private Double vendLimInfComis;

    @Column(name = "USU_COD")
    private Long usuCodigo;

    @Column(name = "VEND_LIM_SUP_COMIS")
    private Double vendLimSupComis;

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

    @Column(name = "MAC_COD_VENDSAP")
    private String macCodVendsap;


    @ManyToOne
    @JoinColumn(name = "CAR_CODIGO", referencedColumnName = "CAR_CODIGO", insertable = false, updatable = false)
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "USU_COD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuario;


    // es necesario agregar las relaciones con otras entidades, con las anotaciones correspondientes
    // Por ejemplo, si hay una relación con otra entidad por "MAC_NRO_PLAN_CUENTA" o "MAC_COD_CAPITULO",
    // puedes agregar @ManyToOne y @JoinColumn según corresponda. cuando se cree la tabla MAESTRO_CUENTA

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Vendedor> vendedores;
    }
}

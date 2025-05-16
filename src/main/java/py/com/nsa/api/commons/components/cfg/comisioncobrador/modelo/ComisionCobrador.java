package py.com.nsa.api.commons.components.cfg.comisioncobrador.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_COMISION_COBRADOR", schema = "GNSAT")
public class ComisionCobrador implements Serializable {

    @Id
    @Column(name = "COM_CODIGO")
    private Long comCodigo;

    @Column(name = "COM_DESCRIPCION")
    private String comDescripcion;

    @Column(name = "MAC_NRO_PLAN_CUENTA")
    private Long macNroPlanCuenta;

    @Column(name = "MAC_COD_CAPITULO")
    private Integer macCodCapitulo;

    @Column(name = "MAC_NRO_CUENTA")
    private String macNroCuenta;

    // es necesario agregar las relaciones con otras entidades, con las anotaciones correspondientes
    // Por ejemplo, si hay una relación con otra entidad por "MAC_NRO_PLAN_CUENTA" o "MAC_COD_CAPITULO",
    // puedes agregar @ManyToOne y @JoinColumn según corresponda. cuando se cree la tabla MAESTRO_CUENTA

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<ComisionCobrador> comisionCobrador;
    }
}

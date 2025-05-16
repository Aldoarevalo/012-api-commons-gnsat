package py.com.nsa.api.commons.components.trx.viajechofer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.empleado.model.Empleado;
import py.com.nsa.api.commons.components.trx.viaje.model.Viaje;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TRX_VIAJE_CHOFER", schema = "GNSAT")
@IdClass(ViajeChofer.IdClass.class)
public class ViajeChofer {

    @Id
    @Column(name = "V_COD", nullable = false)
    @JsonProperty("vCod")
    private Integer vCod;

    @Id
    @Column(name = "E_COD", nullable = false)
    @JsonProperty("eCod")
    private Long eCod;

    @ManyToOne
    @JoinColumn(name = "V_COD", insertable = false, updatable = false)
    private Viaje viaje;

    @ManyToOne
    @JoinColumn(name = "E_COD", insertable = false, updatable = false)
    private Empleado empleado;

    // Clase interna para la clave primaria compuesta
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IdClass implements Serializable {
        private Integer vCod;
        private Long eCod;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<ViajeChofer> viajeChoferes;
    }
}
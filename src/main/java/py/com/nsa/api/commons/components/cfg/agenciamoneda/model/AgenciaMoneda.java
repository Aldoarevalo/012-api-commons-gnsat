package py.com.nsa.api.commons.components.cfg.agenciamoneda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.agenciamoneda.model.pk.AgenciaMonedaPK;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_AGENCIA_MONEDA", schema = "GNSAT")
@IdClass(value = AgenciaMonedaPK.class)
public class AgenciaMoneda {

    @Id
    @Column(name = "AG_COD", nullable = false)
    private Long agCod;

    @Id
    @Column(name = "PAR_MONEDA", nullable = false, length = 4)
    private String parMoneda;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<AgenciaMoneda> agenciaMoneda;
    }
}
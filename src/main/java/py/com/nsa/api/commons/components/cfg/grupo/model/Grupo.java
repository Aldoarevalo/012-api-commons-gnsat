// Modelo (Grupo.java)
package py.com.nsa.api.commons.components.cfg.grupo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_GRUPO", schema = "GNSAT")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grupo_generator")
    @SequenceGenerator(name = "grupo_generator", sequenceName = "GNSAT.SEQ_CFG_GRUPO", allocationSize = 1)
    @Column(name = "GRU_COD", nullable = false)
    private Integer gruCodigo;

    @Column(name = "GRU_NOMBRE", nullable = false, length = 20)
    private String gruNombre;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Grupo> grupos;
    }
}

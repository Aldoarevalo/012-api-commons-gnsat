package py.com.nsa.api.commons.components.cfg.pais.model;

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
@Table(name = "CFG_PAIS", schema = "GNSAT")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pais")
    @SequenceGenerator(name = "seq_pais", sequenceName = "GNSAT.SEQ_CFG_PAIS", allocationSize = 1)
    @Column(name = "PA_COD")
    private Long paCod;

    @Column(name = "PA_DESCRIPCION")
    private String paDescripcion;

    @Column(name = "PA_COD_TEL")
    private String paCodTel;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Pais> paises;
    }
}

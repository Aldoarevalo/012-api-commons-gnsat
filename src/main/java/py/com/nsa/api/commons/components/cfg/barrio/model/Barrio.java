package py.com.nsa.api.commons.components.cfg.barrio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.ciudad.model.Ciudad;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_BARRIO", schema = "GNSAT")
public class Barrio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_barrio")
    @SequenceGenerator(name = "seq_barrio", sequenceName = "GNSAT.SEQ_CFG_BARRIO", allocationSize = 1)
    @Column(name = "B_COD")
    private Long bCod;

    @Column(name = "B_DESCRIPCION", nullable = false, length = 30)
    private String bdescripcion;

    @Column(name = "CIU_COD", nullable = false)
    private Long ciuCod;

    @Column(name = "B_POSTAL", length = 15)
    private String bPostal;

    @ManyToOne
    @JoinColumn(name = "CIU_COD", referencedColumnName = "CIU_COD", insertable = false, updatable = false)
    private Ciudad ciudad;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Barrio> barrios;
    }
}

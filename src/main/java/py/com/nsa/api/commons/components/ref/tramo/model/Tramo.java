package py.com.nsa.api.commons.components.ref.tramo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.parada.model.Parada;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_TRAMO", schema = "GNSAT")
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tramo_generator")
    @SequenceGenerator(name = "tramo_generator", sequenceName = "GNSAT.SEQ_REF_TRAMO", allocationSize = 1)
    @Column(name = "TR_COD")
    private Long trCod;

    @Column(name = "PARA_COD_ORIGEN")
    private Long paraCodOrigen;

    @Column(name = "PARA_COD_DESTINO")
    private Long paraCodDestino;

    @Column(name = "TR_ESTADO")
    private String trEstado;

    @Column(name = "TR_FECHA_MOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trFechaMod;

    @Column(name = "TR_USU_COD")
    private Long trUsuCod;

    @ManyToOne
    @JoinColumn(name = "PARA_COD_ORIGEN", referencedColumnName = "PARA_COD", insertable = false, updatable = false)
    private Parada paradaOrigen;


    @ManyToOne
    @JoinColumn(name = "PARA_COD_DESTINO", referencedColumnName = "PARA_COD", insertable = false, updatable = false)
    private Parada paradaDestino;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private java.util.List<Tramo> tramos;
    }
}


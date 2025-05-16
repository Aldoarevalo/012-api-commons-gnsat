package py.com.nsa.api.commons.components.ref.tipoiva.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_TIPO_IVA", schema = "GNSAT")
public class TipoIva {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_iva")
    @SequenceGenerator(name = "seq_tipo_iva", sequenceName = "GNSAT.SEQ_REF_TIPO_IVA", allocationSize = 1)
    @Column(name = "IVA_CODIGO")
    private Long ivaCodigo;

    @Column(name = "PA_COD")
    private Long paiCodigo;

    @Column(name = "IVA_DESCRIPCION")
    private String ivaDescripcion;

    @Column(name = "IVA_PORCENTAJE")
    private Float ivaPorcentaje;

    @Column(name = "IVA_IMPRIMIBLE")
    private String ivaImprimible;

    @Column(name = "IVA_HABILITADO")
    private String ivaHabilitado;

    @Column(name = "IVA_IDSAP")
    private String ivaIdSap;

    @ManyToOne
    @JoinColumn(name = "PA_COD", referencedColumnName = "PA_COD", insertable = false, updatable = false)
    private Pais pais;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<TipoIva> tiposIva;
    }
}
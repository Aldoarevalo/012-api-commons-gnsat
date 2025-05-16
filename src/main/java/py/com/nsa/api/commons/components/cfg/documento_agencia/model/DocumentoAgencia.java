package py.com.nsa.api.commons.components.cfg.documento_agencia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.tipodocagencia.model.TipoDocAgencia;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_DOC_AGENCIA", schema = "GNSAT")
public class DocumentoAgencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "docagencia_generator")
    @SequenceGenerator(name = "docagencia_generator", sequenceName = "GNSAT.SEQ_CFG_DOC_AGENCIA", allocationSize = 1)
    @Column(name = "DC_DOC")
    private Long dcDoc;

    @Column(name = "AG_COD")
    private Long agCod;

    @Column(name = "TCD_COD")
    private Long tcdCod;

    @Column(name = "DC_DESCRIPCION")
    private String dcDescripcion;

    @Column(name = "DC_VENCIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dcVencimiento;

    @Lob // Anotación para indicar que es un campo binario de gran tamaño
    @Column(name = "DC_ADJUNTO")
    private byte[] dcAdjunto;

    @ManyToOne
    @JoinColumn(name = "TCD_COD", referencedColumnName = "TCD_COD", insertable = false, updatable = false)
    private TipoDocAgencia tipoDocAgencia;


    @ManyToOne
    @JoinColumn(name = "AG_COD", referencedColumnName = "AG_COD", insertable = false, updatable = false)
    private Agencia agencia;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<DocumentoAgencia> documentoAgencias;
    }

}

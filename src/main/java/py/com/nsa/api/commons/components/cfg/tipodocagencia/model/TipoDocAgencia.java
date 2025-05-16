package py.com.nsa.api.commons.components.cfg.tipodocagencia.model;

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
@Table(name = "CFG_TIPO_DOC_AGENCIA", schema = "GNSAT", uniqueConstraints = {
    @UniqueConstraint(name = "TIPODOCAGENCIA_NOMBRE_DOC_UK", columnNames = "TCD_NOMBRE_DOC")
})
public class TipoDocAgencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_doc_agencia")
    @SequenceGenerator(name = "seq_tipo_doc_agencia", sequenceName = "GNSAT.SEQ_CFG_TIPO_DOC_AGENCIA", allocationSize = 1)
    @Column(name = "TCD_COD", nullable = false)
    private Long tcdCod;

    @Column(name = "TCD_NOMBRE_DOC", length = 20, nullable = false)
    private String tcdNombreDoc;

    @Column(name = "TCD_ES_OBLIGATORIO", length = 1, nullable = false)
    private String tcdEsObligatorio;

    @Column(name = "TCD_TPERSONA", length = 1, nullable = false)
    private String tcdTPersona;

    @PrePersist
    @PreUpdate
    private void validateFields() {
        if (tcdEsObligatorio == null || !tcdEsObligatorio.matches("[SN]")) {
            throw new IllegalArgumentException("El campo TCD_ES_OBLIGATORIO debe ser 'S' o 'N'.");
        }
        //if (tcdTPersona == null || !tcdTPersona.matches("[FJ]")) {
        if (tcdTPersona == null || (!tcdTPersona.equals("F") && !tcdTPersona.equals("J"))) {
            throw new IllegalArgumentException("El campo TCD_TPERSONA debe ser 'F' o 'J'.");
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<TipoDocAgencia> tipoDocAgencias;
    }
}

package py.com.nsa.api.commons.components.cfg.tipo_documento_conf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "CFG_CONF_T_DOC", schema = "GNSAT")
public class TipoDocumentoConf {

    //definicion de mapeo
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conf_tipo_documento")
    @SequenceGenerator(name = "seq_conf_tipo_documento", sequenceName = "GNSAT.SEQ_CFG_CONF_T_DOC", allocationSize = 1)
    @Column(name = "DOC_COD")
    private Long docCod;

    @NotBlank(message = "El nombre del tipo de documento es requerido")
    @Size(max = 10, message = "El nombre no puede exceder los 10 caracteres")
    @Column(name = "DOC_NOMBRE", nullable = false, length = 10)
    private String docNombre;

    @NotBlank(message = "La descripción del tipo de documento es requerida")
    @Size(max = 50, message = "La descripción no puede exceder los 50 caracteres")
    @Column(name = "DOC_DESCRIPCION", nullable = false, length = 50)
    private String docDescripcion;

    @NotNull(message = "El código de país es requerido")
    @Column(name = "PA_COD", nullable = false)
    private Long paCod;

    @ManyToOne
    @JoinColumn(name = "PA_COD", referencedColumnName = "PA_COD", insertable = false, updatable = false)
    private Pais pais;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<TipoDocumentoConf> tiposDocumento;
    }
}
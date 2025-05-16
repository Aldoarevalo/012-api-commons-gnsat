package py.com.nsa.api.commons.components.cfg.conftdoc.model;

import jakarta.persistence.*;
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
public class ConfTDoc {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conf_t_doc")
    @SequenceGenerator(name = "seq_conf_t_doc", sequenceName = "GNSAT.SEQ_CFG_CONF_T_DOC", allocationSize = 1)
    @Column(name = "DOC_COD")
    private Long docCod;

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(max = 10, message = "El nombre no puede exceder los 10 caracteres")
    @Column(name = "DOC_NOMBRE", length = 10, nullable = false)
    private String docNombre;

    @NotNull(message = "La descripción no puede ser nula")
    @Size(max = 50, message = "La descripción no puede exceder los 50 caracteres")
    @Column(name = "DOC_DESCRIPCION", length = 50, nullable = false)
    private String docDescripcion;

    @NotNull(message = "El código de país no puede ser nulo")
    @Column(name = "PA_COD", nullable = false)
    private Long paCod;

    // Relación con la entidad País
    @ManyToOne
    @JoinColumn(name = "PA_COD", referencedColumnName = "PA_COD", insertable = false, updatable = false)
    private Pais pais;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<ConfTDoc> Conftdocs;
    }
}

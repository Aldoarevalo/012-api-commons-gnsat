package py.com.nsa.api.commons.components.ref.pdoc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.conftdoc.model.ConfTDoc;
import py.com.nsa.api.commons.components.ref.pdoc.model.pk.PDocPK;
import py.com.nsa.api.commons.components.ref.persona.model.Persona;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_P_DOC", schema = "GNSAT")
@IdClass(value = PDocPK.class) // Clase que representa la clave primaria compuesta
public class PDoc {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOC_COD")
    private Long docCod;
    @Id
    @NotNull(message = "El número de documento no puede ser nulo")
    @Column(name = "P_DOC_NRO_DOC", length = 15, nullable = false)
    private String PDocNroDoc;
    @Id
    @NotNull(message = "El código de persona no puede ser nulo")
    @Column(name = "P_COD", nullable = false)
    private Long pcod;

    @NotNull(message = "La fecha de vencimiento no puede ser nula")
    @Column(name = "P_VENCIMIENTO", nullable = false)
    private java.util.Date pVencimiento;

    @ManyToOne
    @JoinColumn(name = "DOC_COD", referencedColumnName = "DOC_COD", insertable = false, updatable = false)
    private ConfTDoc conftdoc;

    @ManyToOne
    @JoinColumn(name = "P_COD", referencedColumnName = "P_COD", insertable = false, updatable = false)
    private Persona persona;

    @Transient
    private Long docCodold;

    @Transient
    private String pdocNroDocold;

    @Transient
    private Long perCodold;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private java.util.List<PDoc> PDocs;

    }
}

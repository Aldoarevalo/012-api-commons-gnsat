package py.com.nsa.api.commons.components.ref.persona.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.barrio.model.Barrio;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;
import py.com.nsa.api.commons.components.ref.pdoc.model.PDoc;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_PERSONA", schema = "GNSAT")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_persona")
    @SequenceGenerator(name = "seq_persona", sequenceName = "GNSAT.SEQ_CFG_REF_PERSONA", allocationSize = 1)
    @Column(name = "P_COD")
    private Long pcod;

    @Column(name = "B_COD")
    private Long bCod;

    @Column(name = "PA_CODIGO")
    private Long paCod;

    @Column(name = "P_NOMBRE")
    private String pnombre;

    @Column(name = "P_APELLIDO")
    private String papellido;

    @Column(name = "P_DIRECCION")
    private String pdireccion;

    @Column(name = "P_EMAIL")
    private String pemail;

    @Column(name = "P_ESFISICA")
    private String pesFisica;

    @Column(name = "P_FECNAC")
    @Temporal(TemporalType.DATE)
    private Date pfechaNacimiento;

    @Column(name = "P_SEXO")
    private String psexo;

    @Column(name = "P_TELEFONO")
    private String ptelefono;

    @Column(name = "P_CELULAR")
    private String pcelular;

    @ManyToOne
    @JoinColumn(name = "B_COD", referencedColumnName = "B_COD", insertable = false, updatable = false)
    private Barrio barrio;

    @ManyToOne
    @JoinColumn(name = "PA_CODIGO", referencedColumnName = "PA_COD", insertable = false, updatable = false)
    private Pais pais;

    @Transient
    private List<String> PDocNroDoc;

    @Transient
    private Long oldpcod;

    @Transient
    private Long nuevapcod;

    @Transient
    private List<Map<String, Object>> empleado;

    @Transient
    private List<Map<String, Object>> documentos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Persona> personas;
    }

}

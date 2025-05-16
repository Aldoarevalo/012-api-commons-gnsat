package py.com.nsa.api.commons.components.cfg.usuario.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.empleado.model.Empleado;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_USUARIO", schema = "GNSAT")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "GNSAT.SEQ_CFG_USUARIO", allocationSize = 1)
    @Column(name = "USU_COD")
    private Long usuCod;

    @Column(name = "USU_NOMBRE")
    private String usuNombre;

    // @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "USU_CONTRASENA")
    private String usuContrasena;

    @Column(name = "USU_FECHA_MOD")
    @Temporal(TemporalType.DATE)
    private Date usuFechaMod;

    @Column(name = "E_COD")
    private Long eCod;

    @Column(name = "USU_ESTADO")
    private String usuEstado;

    @Column(name = "USU_CAMBIO_PASS")
    private String usuCambioPass;

    @Column(name = "USU_CONTADOR")
    private Long usuContador;

    @Column(name = "USU_FECHAVENC")
    @Temporal(TemporalType.TIMESTAMP) // Esto asegura que Hibernate maneje fecha y hora
    private Date usuFechavenc;

    @Transient
    private String pnombre;

    @Transient
    private String papellido;

    @Transient
    private List<Long> agCod;

    @Transient
    private Long usuAgeCod;

    @Transient
    private Long bCod;

    @Transient
    private Long ciuCod;

    @Transient
    private String bdescripcion;

    @Transient
    private String bPostal; // Campo transitorio para almacenar el código postal

    @Transient
    private Long action;

    @OneToOne
    @JoinColumn(name = "E_COD", referencedColumnName = "E_COD", insertable = false, updatable = false)
    private Empleado empleado;

    @Transient
    private List<Map<String, Object>> agencias;

    /*
     * @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch =
     * FetchType.LAZY)
     * 
     * @JsonManagedReference // Evita problemas de referencia cíclica
     * private List<UsuarioAgencia> agencias;
     */

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Usuario> usuarios;
    }

}
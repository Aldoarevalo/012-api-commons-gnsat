package py.com.nsa.api.commons.components.cfg.rol.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_ROL", schema = "GNSAT")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_generator")
    @SequenceGenerator(name = "rol_generator", sequenceName = "GNSAT.SEQ_CFG_ROL", allocationSize = 1)
    @Column(name = "ROL_COD")
    private Long rolCodigo;

    @Column(name = "ROL_DESCRIPCION")
    private String rolDescripcion;

    @Column(name = "ROL_ESTADO")
    private String rolEstado;

    @Column(name = "ROL_FECHA")
    private Date rolFecha;

    @Column(name = "ROL_NOMBRE")
    private String rolNombre;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Rol> roles;
    }

}

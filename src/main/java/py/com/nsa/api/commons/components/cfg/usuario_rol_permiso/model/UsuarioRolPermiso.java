package py.com.nsa.api.commons.components.cfg.usuario_rol_permiso.model;

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
@Table(name = "CFG_USUARIO_ROL", schema = "GNSAT")
public class UsuarioRolPermiso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permiso_generator")
    @SequenceGenerator(name = "permiso_generator", sequenceName = "GNSAT.SEQ_CFG_PERMISO", allocationSize = 1)
    @Column(name = "USU_ROL_COD")
    private Long usuRolCod;

    @Column(name = "USU_COD")
    private Long usuCod;

    @Column(name = "ROL_COD")
    private Long rolCod;

    @Column(name = "USU_ROL_FECHA")
    private Date usuRolFecha;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<UsuarioRolPermiso> rolPermisos;
    }

}

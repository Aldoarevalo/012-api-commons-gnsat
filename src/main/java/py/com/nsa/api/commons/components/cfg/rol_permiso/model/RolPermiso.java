package py.com.nsa.api.commons.components.cfg.rol_permiso.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.permiso.model.CfgPermiso;
import py.com.nsa.api.commons.components.cfg.rol.model.Rol;
import py.com.nsa.api.commons.components.cfg.usuario_rol.model.CfgUsuarioRol;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_ROL_PERMISO", schema = "GNSAT")
public class RolPermiso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_permiso_generator")
    @SequenceGenerator(name = "rol_permiso_generator", sequenceName = "GNSAT.SEQ_CFG_ROL_PERMISO", allocationSize = 1)
    @Column(name = "ROL_PERM_COD")
    private Long rolPermCod;

    @Column(name = "ROL_COD")
    private Long rolCodigo;

    @Column(name = "PER_COD")
    private Long perCodigo;

    @ManyToOne
    @JoinColumn(name = "PER_COD", referencedColumnName = "PER_COD", insertable = false, updatable = false)
    private CfgPermiso permiso;

    @ManyToOne
    @JoinColumn(name = "ROL_COD", referencedColumnName = "ROL_COD", insertable = false, updatable = false)
    private Rol rol;

    @Column(name = "ROL_PER_EJE")
    private String rolPerEje;

    @Column(name = "ROL_PER_ELI")
    private String rolPerEli;

    @Column(name = "ROL_PER_INS")
    private String rolPerIns;

    @Column(name = "ROL_PER_LECT")
    private String rolPerLect;

    @Column(name = "ROL_PER_MOD")
    private String rolPerMod;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<RolPermiso> rolPermisos;
    }

}

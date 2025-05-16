package py.com.nsa.api.commons.components.cfg.usuario_rol.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.rol.model.Rol;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_USUARIO_ROL", schema = "GNSAT")
public class CfgUsuarioRol {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_rol_generator")
    @SequenceGenerator(name = "usuario_rol_generator", sequenceName = "GNSAT.SEQ_CFG_USUARIO_ROL", allocationSize = 1)
    @Column(name = "USU_ROL_COD")
    private Long usuRolCod;

    @Column(name = "USU_COD")
    private Long usuCod;

    @Column(name = "ROL_COD")
    private Long rolCodigo;

    @Column(name = "USU_ROL_FECHA")
    private Date usuRolFecha;

    @ManyToOne
    @JoinColumn(name = "ROL_COD", referencedColumnName = "ROL_COD", insertable = false, updatable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "USU_COD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuario;

    /*
    //lista de roles asociados a un usuario
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usu_cod"),
            inverseJoinColumns = @JoinColumn(name = "rol_cod")
    )
    private Set<Rol> roles = new HashSet<>();
    */

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<CfgUsuarioRol> usuariosRol;
    }
}
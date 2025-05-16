// Modelo (GrupoUsuario.java)
package py.com.nsa.api.commons.components.cfg.grupo_usuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.cfg.grupo.model.Grupo;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_GRUPO_USUARIO", schema = "GNSAT")
public class GrupoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grupo_usuario_seq")
    @SequenceGenerator(name = "grupo_usuario_seq", sequenceName = "GNSAT.SEQ_CFG_GRUPO_USUARIO", allocationSize = 1)
    @Column(name = "USU_GRU_COD")
    private Integer usuGruCod;

    @Column(name = "USU_COD", nullable = false)
    private Integer usuCod;

    @Column(name = "GRU_COD", nullable = false)
        private Integer gruCod;

    @ManyToOne
    @JoinColumn(name = "USU_COD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "GRU_COD", referencedColumnName = "GRU_COD", insertable = false, updatable = false)
    private Grupo grupo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<GrupoUsuario> gruposUsuarios;
    }
}

package py.com.nsa.api.commons.components.cfg.usuarioagencia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;

import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_USUARIO_AGENCIA", schema = "GNSAT")
public class UsuarioAgencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agencia_seq")
    @SequenceGenerator(name = "agencia_seq", sequenceName = "GNSAT.SEQ_CFG_USUARIO_AGENCIA", allocationSize = 1)
    @Column(name = "USU_AGE_COD")
    private Long usuAgeCod;

    @Column(name = "USU_AGE_USU_COD")
    private Long usuCod;

    @Column(name = "USU_AGE_AGE_COD")
    private Long agCod;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USU_AGE_AGE_COD", referencedColumnName = "AG_COD", insertable = false, updatable = false)
    private Agencia agencia;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USU_AGE_USU_COD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    // @JsonBackReference // Marca el lado inverso de la relación
    private Usuario usuario;

    @Transient
    private List<Map<String, Object>> agencias;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<UsuarioAgencia> usuarioAgencias;
    }
}
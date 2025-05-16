package py.com.nsa.api.commons.components.cfg.tipo_perfil_usuario.model;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_PERFIL_USUARIO", schema = "GNSAT")
public class TipoPerfilUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PEU_CODIGO")
    private Integer peuCodigo;

    @Column(name = "PEU_NOM_PERFIL")
    private String nombrePerfil;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String message;
        private List<TipoPerfilUsuario> perfiles;
    }
}

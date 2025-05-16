package py.com.nsa.api.commons.components.cfg.usuario_app_servicio.model.pk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminPK implements Serializable {

    //TODO. definir la clave compuesta

    //codigo de servicio
    private Long seaCodigo;

    //codigo de app
    private Long appCodigo;

    //TODO. codigo de perfil o de usuario. elegir uno solo, basado en cual tabla?
    private Long perfilCodigo;
    private Long usuCodigo;
}
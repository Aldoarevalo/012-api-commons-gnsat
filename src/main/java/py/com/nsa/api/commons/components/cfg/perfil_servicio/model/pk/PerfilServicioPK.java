package py.com.nsa.api.commons.components.cfg.perfil_servicio.model.pk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilServicioPK implements Serializable {

    //codigo de perfil
    private Long cod_perfil;

    //codigo de servicio/permiso
    private Long cod_servicio;

}
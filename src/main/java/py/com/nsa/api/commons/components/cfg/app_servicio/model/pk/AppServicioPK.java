package py.com.nsa.api.commons.components.cfg.app_servicio.model.pk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppServicioPK implements Serializable {

    //codigo de servicio
    private Long seaCodigo;

    //codigo de app
    private Long appCodigo;

}
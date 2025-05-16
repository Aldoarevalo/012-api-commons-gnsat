package py.com.nsa.api.commons.components.cfg.usuarioagencia.model.pk;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioAgenciaPK implements Serializable {

    @Column(name = "AGE_CODIGO")
    private Long agCod;

    @Column(name = "USU_COD")
    private Long usuCod;
}

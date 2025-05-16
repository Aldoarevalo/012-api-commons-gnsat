package py.com.nsa.api.commons.components.cfg.asignacionserie.model.clavecompuesta;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AsignacionSerieId implements Serializable {

    @JsonProperty("agCod")
    @Column(name = "AG_COD")
    private Long agCod;

    @JsonProperty("sCod")
    @Column(name = "S_COD")
    private String sCod;

    @JsonProperty("asUsuario")
    @Column(name = "AS_USUARIO")
    private Long asUsuario;

    @JsonProperty("asTDoc")
    @Column(name = "AS_T_DOC")
    private String asTDoc;
}
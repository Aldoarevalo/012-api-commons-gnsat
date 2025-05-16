package py.com.nsa.api.commons.components.cfg.detalleserie.model.clavecompuesta;

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
public class DetalleSerieId implements Serializable {

    @JsonProperty("sSerie")
    @Column(name = "S_COD") // Especificando el nombre de la columna exacto
    private String sSerie;

    @JsonProperty("detLinea")
    @Column(name = "DET_LINEA") // Especificando el nombre de la columna exacto
    private Long detLinea;
}

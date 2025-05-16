package py.com.nsa.api.commons.components.cfg.detalleserie.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.detalleserie.model.clavecompuesta.DetalleSerieId;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CFG_DETALLE_SERIE", schema = "GNSAT")
public class DetalleSerie {

    @EmbeddedId
    private DetalleSerieId id;

    @Column(name = "DET_INICIAL")
    private String detInicial;

    @Column(name = "DET_FINAL")
    private String detFinal;

    @Column(name = "DET_ULTIMO")
    private String detUltimo;

    @Column(name = "DET_FECHA_INICIAL")
    private Date detFechaInicial;

    @Column(name = "DET_FECHA_FIN")
    private Date detFechaFin;

    @Column(name = "DET_TIMBRADO")
    private String detTimbrado;

    @Column(name = "DET_CERRADO")
    private String detCerrado;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        @JsonProperty("status")
        private Long status;

        @JsonProperty("mensaje")
        private String mensaje;

        @JsonProperty("detalles")
        private List<DetalleSerie> detalles;
    }
}

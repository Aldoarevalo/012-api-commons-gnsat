package py.com.nsa.api.commons.components.cfg.muelles.model;

import jakarta.persistence.*;
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
@Table(name = "WMS_MUELLES", schema = "GNSAT")
public class WmsMuelle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_wms_muelles")
    @SequenceGenerator(name = "seq_wms_muelles", sequenceName = "SEQ_WMS_MUELLES", schema = "GNSAT", allocationSize = 1)
    @Column(name = "WMS_ID")
    private Long wmsId;

    @Column(name = "WMS_CIUDAD", length = 50, nullable = false)
    private String wmsCiudad;

    @Column(name = "WMS_DEPARTAMENTO", length = 50, nullable = false)
    private String wmsDepartamento;

    @Column(name = "WMS_LATITUD", nullable = false)
    private Double wmsLatitud;

    @Column(name = "WMS_LONGITUD", nullable = false)
    private Double wmsLongitud;

    @Column(name = "WMS_COORDENADAS", nullable = false)
    private Double wmsCoordenadas;

    @Column(name = "WMS_MUELLE", nullable = false)
    private Long wmsMuelle;

    @Column(name = "WMS_UB_ZONA_ESPERA", length = 50, nullable = false)
    private String wmsUbZonaEspera;

    @Column(name = "WMS_ZONA_PACK", length = 50, nullable = false)
    private String wmsZonaPack;

    @Column(name = "WMS_UB_PACK", length = 50, nullable = false)
    private String wmsUbPack;

    // Información adicional para la respuesta
    @Transient
    private Double distancia;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<WmsMuelle> muelles;
    }
}
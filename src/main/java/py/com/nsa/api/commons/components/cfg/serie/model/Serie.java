package py.com.nsa.api.commons.components.cfg.serie.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_SERIE", schema = "GNSAT")
public class Serie {
    @Id
    @NotBlank(message = "El código de serie no puede estar vacío")
    @Column(name = "S_COD")
    @JsonProperty("sCod")
    private String sCod;

    @Column(name="S_PREF")
    @JsonProperty("sPref")
    private String sPref;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PA_COD")
    @NotNull(message = "El país no puede ser nulo")
    @JsonProperty("pais")
    private Pais pais;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Serie> series;
    }
}
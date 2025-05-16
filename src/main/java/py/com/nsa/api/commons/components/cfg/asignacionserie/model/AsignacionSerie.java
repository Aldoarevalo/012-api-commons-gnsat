package py.com.nsa.api.commons.components.cfg.asignacionserie.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.asignacionserie.model.clavecompuesta.AsignacionSerieId;
import py.com.nsa.api.commons.components.cfg.serie.model.Serie;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_ASIGNACION_SERIE", schema = "GNSAT")
public class AsignacionSerie {

    @EmbeddedId
    private AsignacionSerieId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AG_COD", referencedColumnName = "AG_COD", insertable = false, updatable = false)
    private Agencia agencia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "S_COD", referencedColumnName = "S_COD", insertable = false, updatable = false)
    private Serie serie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AS_USUARIO", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AS_T_DOC", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor tipoDocumento;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        @JsonProperty("status")
        private Long status;

        @JsonProperty("mensaje")
        private String mensaje;

        @JsonProperty("asignaciones")
        private List<AsignacionSerie> asignaciones;
    }
}
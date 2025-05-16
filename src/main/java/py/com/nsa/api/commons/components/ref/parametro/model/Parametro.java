package py.com.nsa.api.commons.components.ref.parametro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.parametro.model.pk.ParametroPK;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PAR_PARAMETRO", schema = "GNSAT")
@IdClass(ParametroPK.class)
public class Parametro {

    @Id
    @Column(name = "PM_COD", nullable = false)
    private Long pmCod;

    @Id
    @Column(name = "PM_NOMBRE", length = 20, nullable = false, unique = true)
    private String pmNombre;


    @PrePersist
    @PreUpdate
    private void validatePmNombre() {
        if (pmNombre == null || pmNombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del parámetro no puede estar vacío.");
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Parametro> parametros;
    }
}

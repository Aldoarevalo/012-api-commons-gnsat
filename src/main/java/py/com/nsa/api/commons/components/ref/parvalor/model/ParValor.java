package py.com.nsa.api.commons.components.ref.parvalor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.parametro.model.Parametro;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PAR_VALOR", schema = "GNSAT")
public class ParValor {

    @Id
    @Column(name = "PAR_VALOR", length = 4, nullable = false)
    private String parValor;

    @Column(name = "PAR_DESCRIPCION", length = 30, nullable = false)
    private String parDescripcion;

    @Column(name = "PAR_COMENTARIO", length = 50)
    private String parComentario;



    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PM_COD", referencedColumnName = "PM_COD", nullable = false),
        @JoinColumn(name = "PM_NOMBRE", referencedColumnName = "PM_NOMBRE", nullable = false)
    })
    private Parametro parametro;

    @PrePersist
    @PreUpdate
    private void validateParValor() {
        if (parValor == null || parValor.trim().isEmpty()) {
            throw new IllegalArgumentException("El valor del parámetro no puede estar vacío.");
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<ParValor> valores;
    }
}
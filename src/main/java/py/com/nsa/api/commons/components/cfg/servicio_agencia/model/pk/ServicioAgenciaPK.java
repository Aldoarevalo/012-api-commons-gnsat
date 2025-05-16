package py.com.nsa.api.commons.components.cfg.servicio_agencia.model.pk;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioAgenciaPK implements Serializable {

    @Column(name = "PAR_SERVICIO")
    private String parServicio;

    @Column(name = "AG_COD")
    private Long agCod;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicioAgenciaPK that = (ServicioAgenciaPK) o;
        return Objects.equals(parServicio, that.parServicio) &&
                Objects.equals(agCod, that.agCod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parServicio, agCod);
    }
}

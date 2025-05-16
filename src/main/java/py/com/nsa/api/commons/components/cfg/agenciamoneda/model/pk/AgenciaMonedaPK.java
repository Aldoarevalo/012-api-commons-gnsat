package py.com.nsa.api.commons.components.cfg.agenciamoneda.model.pk;

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
public class AgenciaMonedaPK implements Serializable {

    @Column(name = "AG_COD")
    private Long agCod;

    @Column(name = "PAR_MONEDA")
    private String parMoneda;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgenciaMonedaPK that = (AgenciaMonedaPK) o;
        return Objects.equals(agCod, that.agCod) &&
                Objects.equals(parMoneda, that.parMoneda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agCod, parMoneda);
    }
}
package py.com.nsa.api.commons.components.ref.tarifaruta.model.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarifaRutaPK implements Serializable {

    @Column(name = "RUC_COD")
    private Long rucCod;

    @Column(name = "RUD_SECUENCIA")
    private Long rudSecuencia;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarifaRutaPK that = (TarifaRutaPK) o;
        return Objects.equals(rucCod, that.rucCod) && Objects.equals(rudSecuencia, that.rudSecuencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rucCod, rudSecuencia);
    }
}
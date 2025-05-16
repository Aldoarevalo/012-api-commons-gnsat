package py.com.nsa.api.commons.components.cfg.cobrador.model.pk;

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
public class CobradorPK implements Serializable {

    @Column(name = "COBR_CODIGO")
    private Long cobrCodigo;

    @Column(name = "CAR_CODIGO")
    private Long carCodigo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CobradorPK that = (CobradorPK) o;
        return Objects.equals(cobrCodigo, that.cobrCodigo) &&
                Objects.equals(carCodigo, that.carCodigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cobrCodigo, carCodigo);
    }
}

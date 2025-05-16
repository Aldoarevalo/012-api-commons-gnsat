package py.com.nsa.api.commons.components.cfg.vendedor.model.pk;

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
public class VendedorPK implements Serializable {


    @Column(name = "VEND_CODIGO")
    private Long vendCodigo;

    @Column(name = "CAR_CODIGO")
    private Long carCodigo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendedorPK that = (VendedorPK) o;
        return Objects.equals(vendCodigo, that.vendCodigo) &&
                Objects.equals(carCodigo, that.carCodigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendCodigo, carCodigo);
    }
}

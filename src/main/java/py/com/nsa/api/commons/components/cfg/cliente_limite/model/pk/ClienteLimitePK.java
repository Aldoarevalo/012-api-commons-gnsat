package py.com.nsa.api.commons.components.cfg.cliente_limite.model.pk;

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
public class ClienteLimitePK implements Serializable {

    @Column(name = "C_CODCLIENTE")
    private Long cCodCliente;

    @Column(name = "TL_COD")
    private Long tlCod;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ClienteLimitePK that = (ClienteLimitePK) o;
        return Objects.equals(cCodCliente, that.tlCod) &&
                Objects.equals(cCodCliente, that.cCodCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cCodCliente, tlCod);
    }
}

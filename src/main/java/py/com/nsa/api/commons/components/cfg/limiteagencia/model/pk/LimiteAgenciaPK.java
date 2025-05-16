package py.com.nsa.api.commons.components.cfg.limiteagencia.model.pk;

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
public class LimiteAgenciaPK implements Serializable {

    @Column(name = "AG_COD")
    private Long agCod;

    @Column(name = "TL_COD_LIMITE")
    private Long tlCodLimite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LimiteAgenciaPK that = (LimiteAgenciaPK) o;
        return Objects.equals(agCod, that.agCod) &&
                Objects.equals(tlCodLimite, that.tlCodLimite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agCod, tlCodLimite);
    }
}

package py.com.nsa.api.commons.components.ref.parametro.model.pk;

import jakarta.persistence.*;
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
public class ParametroPK implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAR_PARAMETRO")
    @SequenceGenerator(name = "SEQ_PAR_PARAMETRO", sequenceName = "GNSAT.SEQ_PAR_PARAMETRO", allocationSize = 1)
    @Column(name = "PM_COD")
    private Long pmCod;
    @Id
    @Column(name = "PM_NOMBRE")
    private String pmNombre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParametroPK that = (ParametroPK) o;
        return Objects.equals(pmCod, that.pmCod) &&
                Objects.equals(pmNombre, that.pmNombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pmCod, pmNombre);
    }
}

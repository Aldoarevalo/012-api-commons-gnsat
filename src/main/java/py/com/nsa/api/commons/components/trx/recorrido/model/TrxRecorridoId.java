package py.com.nsa.api.commons.components.trx.recorrido.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TrxRecorridoId implements Serializable {

    @Column(name = "V_COD")
    @JsonProperty("vCod")
    private Integer vCod;

    @Column(name = "R_SECUENCIA")
    @JsonProperty("rSecuencia")
    private Integer rSecuencia;

    @Column(name = "PARA_COD")
    @JsonProperty("paraCod")
    private Integer paraCod;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrxRecorridoId that = (TrxRecorridoId) o;
        return vCod.equals(that.vCod) && rSecuencia.equals(that.rSecuencia) && paraCod.equals(that.paraCod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vCod, rSecuencia, paraCod);
    }
}
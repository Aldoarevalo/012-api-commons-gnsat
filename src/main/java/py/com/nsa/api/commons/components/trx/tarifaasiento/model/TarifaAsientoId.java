package py.com.nsa.api.commons.components.trx.tarifaasiento.model;

import java.io.Serializable;
import java.util.Objects;

public class TarifaAsientoId implements Serializable {

    private String parAsiento;
    private Integer vehiculoCod;

    // Constructor vacío requerido por JPA
    public TarifaAsientoId() {
    }

    public TarifaAsientoId(String parAsiento, Integer vehiculoCod) {
        this.parAsiento = parAsiento;
        this.vehiculoCod = vehiculoCod;
    }

    // Getters y setters
    public String getParAsiento() {
        return parAsiento;
    }

    public void setParAsiento(String parAsiento) {
        this.parAsiento = parAsiento;
    }

    public Integer getVehiculoCod() {
        return vehiculoCod;
    }

    public void setVehiculoCod(Integer vehiculoCod) {
        this.vehiculoCod = vehiculoCod;
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarifaAsientoId that = (TarifaAsientoId) o;
        return Objects.equals(parAsiento, that.parAsiento) &&
                Objects.equals(vehiculoCod, that.vehiculoCod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parAsiento, vehiculoCod);
    }
}
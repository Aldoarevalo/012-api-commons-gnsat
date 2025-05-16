package py.com.nsa.api.commons.components.trx.tarifaasiento.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import py.com.nsa.api.commons.components.trx.viaje.model.Viaje;
import py.com.nsa.api.commons.components.ref.producto.model.Producto;
import java.util.List;
@Entity
@Table(name = "TRX_TARIFA_ASIENTO", schema = "GNSAT")
@IdClass(TarifaAsientoId.class)
public class TarifaAsiento {

    @Id
    @Column(name = "PAR_ASIENTO")
    private String parAsiento;

    @Id
    @Column(name = "V_COD")
    private Integer vehiculoCod;

    @Column(name = "PRO_COD")
    private String proCod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PAR_ASIENTO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    @JsonProperty("asiento")
    private ParValor asiento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "V_COD", referencedColumnName = "V_COD", insertable = false, updatable = false)
    @JsonProperty("viaje")
    private Viaje viaje;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRO_COD", referencedColumnName = "PRO_COD", insertable = false, updatable = false)
    @JsonProperty("producto")
    private Producto producto;

    public TarifaAsiento() {
    }

    public TarifaAsiento(String parAsiento, Integer vehiculoCod, String proCod) {
        this.parAsiento = parAsiento;
        this.vehiculoCod = vehiculoCod;
        this.proCod = proCod;
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

    public String getProCod() {
        return proCod;
    }

    public void setProCod(String proCod) {
        this.proCod = proCod;
    }

    public ParValor getAsiento() {
        return asiento;
    }

    public void setAsiento(ParValor asiento) {
        this.asiento = asiento;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<TarifaAsiento> tarifas;

        public MensajeRespuesta() {
        }

        public MensajeRespuesta(Long status, String mensaje, List<TarifaAsiento> tarifas) {
            this.status = status;
            this.mensaje = mensaje;
            this.tarifas = tarifas;
        }

        public Long getStatus() {
            return status;
        }

        public void setStatus(Long status) {
            this.status = status;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public List<TarifaAsiento> getTarifas() {
            return tarifas;
        }

        public void setTarifas(List<TarifaAsiento> tarifas) {
            this.tarifas = tarifas;
        }
    }
}
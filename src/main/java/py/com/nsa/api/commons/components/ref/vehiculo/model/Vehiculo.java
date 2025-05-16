package py.com.nsa.api.commons.components.ref.vehiculo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.asientos.model.Asiento;
import py.com.nsa.api.commons.components.ref.empresa.model.Empresa;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_VEHICULO", schema = "GNSAT")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehiculo_generator")
    @SequenceGenerator(name = "vehiculo_generator", sequenceName = "GNSAT.SEQ_REF_VEHICULO", allocationSize = 1)
    @Column(name = "VE_COD")
    private Long veCod;

    @Column(name = "PAR_TIPO")
    private String parTipo;

    @ManyToOne
    @JoinColumn(name = "PAR_TIPO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValor;

    @Column(name = "VE_CHAPA", length = 10)
    private String veChapa;

    @Column(name = "VE_EMPRESA")
    private Long veEmpresa;

    @Column(name = "VE_PISO")
    private Long vePiso;

    @Column(name = "VE_ULT_MODIF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date veUltModif;

    @Column(name = "VE_ESTADO", length = 1)
    private String veEstado;

    // Nueva columna agregada: VE_NUMERO
    @Column(name = "VE_NUMERO")
    private Long veNumero;

    @Transient
    @JsonManagedReference
    private List<Asiento> asientos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "PAR_TIPO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parvalor;

    @ManyToOne
    @JoinColumn(name = "VE_EMPRESA", referencedColumnName = "EM_COD", insertable = false, updatable = false)
    private Empresa empresa;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;  // "ok", "info", "error"
        private String mensaje; // Mensaje descriptivo
        private List<Vehiculo> vehiculos; // Lista de vehículos (opcional)
    }
}
package py.com.nsa.api.commons.components.cfg.nsakey_equipo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_NSAKEY_EQUIPO", schema = "GNSAT")
public class NsakeyEquipo {

    @Id
    @Column(name = "ID_EQUIPO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CFG_NSAKEY_EQUIPO")
    @SequenceGenerator(name = "SEQ_CFG_NSAKEY_EQUIPO", sequenceName = "GNSAT.SEQ_CFG_NSAKEY_EQUIPO", allocationSize = 1)
    private Long idEquipo;

    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @Column(name = "NOMBRE_EQUIPO", length = 255)
    private String nombreEquipo;

    @Column(name = "CPU_MODELO", length = 255)
    private String cpuModelo;

    @Column(name = "CPU_NUCLEOS")
    private Integer cpuNucleos;

    @Column(name = "CPU_VELOCIDAD", length = 200)
    private String cpuVelocidad;

    @Column(name = "SO_PLATAFORMA", length = 100)
    private String soPlataforma;

    @Column(name = "SO_DISTRIBUCION", length = 200)
    private String soDistribucion;

    @Column(name = "SO_VERSION", length = 100)
    private String soVersion;

    @Column(name = "SO_ARQUITECTURA", length = 50)
    private String soArquitectura;

    @Column(name = "MAC_ADDRESS_1", length = 50)
    private String macAddress1;

    @Column(name = "INTERFACE_1", length = 100)
    private String interface1;

    @Column(name = "MAC_ADDRESS_2", length = 20)
    private String macAddress2;

    @Column(name = "INTERFACE_2", length = 100)
    private String interface2;

    @Column(name = "MAC_ADDRESS_3", length = 20)
    private String macAddress3;

    @Column(name = "INTERFACE_3", length = 100)
    private String interface3;

    @Column(name = "MAC_ADDRESS_4", length = 20)
    private String macAddress4;

    @Column(name = "INTERFACE_4", length = 100)
    private String interface4;

    @Column(name = "MAC_ADDRESS_5", length = 20)
    private String macAddress5;

    @Column(name = "INTERFACE_5", length = 100)
    private String interface5;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<NsakeyEquipo> equipos;
    }
}
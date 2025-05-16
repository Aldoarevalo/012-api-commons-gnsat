package py.com.nsa.api.commons.components.cfg.equipo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;


import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_EQUIPO", schema = "GNSAT")
public class Equipo {

    @Id
    @Column(name = "EQU_CODIGO")
    private Long equCodigo;

    @Column(name = "EQU_DESCRIPCION")
    private String descripcion;

    @Column(name = "AGE_CODIGO")
    private Long agCod;

    @Column(name = "EQU_IMPRIMIR_COMPROBANTES")
    private String imprimirComprobantes;

    @Column(name = "EQU_NRO_IP")
    private String numeroIP;

    @Column(name = "EQU_COUNTER_ID")
    private String counterID;

    @Column(name = "EQU_COUNTER_AR")
    private String counterAR;

    @Column(name = "EQU_NRO_LOTE")
    private String numeroLote;

    @Column(name = "EQU_HABILITADO")
    private String habilitado;

    @Column(name = "EQU_COUNTER_OP")
    private String counterOP;


    @ManyToOne
    @JoinColumn(name = "AGE_CODIGO", referencedColumnName = "AG_COD", insertable = false, updatable = false)
    private Agencia agencia;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Equipo> equipos;
    }

}

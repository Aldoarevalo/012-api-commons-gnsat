package py.com.nsa.api.commons.components.ref.rutadet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.parada.model.Parada;
import py.com.nsa.api.commons.components.ref.rutacab.model.RutaCab;
import py.com.nsa.api.commons.components.ref.rutadet.model.pk.RutaDetPK;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_RUTA_DET", schema = "GNSAT")
@IdClass(value = RutaDetPK.class) // Clase que representa la clave primaria compuesta
public class RutaDet {

    @Id
    @Column(name = "RUC_COD")
    private Long rucCod;

    @Id
    @Column(name = "RUD_SECUENCIA")
    private Long rudSecuencia;

    @Column(name = "PARA_COD", nullable = false)
    private Long paraCod;

    @Column(name = "TR_TIEMPO", nullable = false)
    private String trTiempo;

    @ManyToOne
    @JoinColumn(name = "PARA_COD", referencedColumnName = "PARA_COD", insertable = false, updatable = false)
    private Parada parada;

    @ManyToOne
    @JoinColumn(name = "RUC_COD", referencedColumnName = "RUC_COD", insertable = false, updatable = false)
    private RutaCab rutaCab;

    @Transient
    private Long rucCodOld;

    @Transient
    private Long rudSecuenciaOld;

}
package py.com.nsa.api.commons.components.cfg.shipments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TMS_SHIPMENTS", schema = "GNSAT")
@IdClass(ShipmentId.class)
public class Shipment {
    @Id
    @Column(name = "TMS_WHSEID", length = 30, nullable = false)
    private String tmsWhseid;

    @Id
    @Column(name = "TMS_STORERKEY", length = 15, nullable = false)
    private String tmsStorerkey;

    @Id
    @Column(name = "TMS_EXTERNORDERKEY", length = 30, nullable = false)
    private String tmsExternorderkey;

    @Column(name = "TMS_EXTERNORDERKEY2", length = 50)
    private String tmsExternorderkey2;

    @Column(name = "TMS_CONSIGNEEKEY", length = 15, nullable = false)
    private String tmsConsigneekey;

    @Column(name = "TMS_COMPANY", length = 50, nullable = false)
    private String tmsCompany;

    @Column(name = "TMS_RAZON_SOCIAL", length = 80, nullable = false)
    private String tmsRazonSocial;

    @Column(name = "TMS_RUC", length = 20, nullable = false)
    private String tmsRuc;

    @Column(name = "TMS_ADDRESS1", length = 45)
    private String tmsAddress1;

    @Column(name = "TMS_CONTACT1", length = 30)
    private String tmsContact1;

    @Column(name = "TMS_PHONE1", length = 18)
    private String tmsPhone1;

    @Column(name = "TMS_EMAIL1", length = 60)
    private String tmsEmail1;

    @Column(name = "TMS_CITY", length = 45)
    private String tmsCity;

    @Column(name = "TMS_STATE", length = 30)
    private String tmsState;

    @Column(name = "TMS_COUNTRY", length = 25)
    private String tmsCountry;

    @Column(name = "TMS_LATITUD", nullable = false)
    private Double tmsLatitud;

    @Column(name = "TMS_LONGITUD", nullable = false)
    private Double tmsLongitud;

    @Column(name = "TMS_INVOICE_DATE")
    @Temporal(TemporalType.DATE)
    private Date tmsInvoiceDate;

    @Column(name = "TMS_ORDER_DATE")
    @Temporal(TemporalType.DATE)
    private Date tmsOrderDate;

    @Column(name = "TMS_ORDER_TRIP")
    private Integer tmsOrderTrip;

    @Column(name = "TMS_INVOICE_TYPE")
    private Integer tmsInvoiceType;

    @Column(name = "TMS_INVOICE_AMOUNT", precision = 22, scale = 2)
    private BigDecimal tmsInvoiceAmount;

    @Column(name = "TMS_CURRENCY")
    private Integer tmsCurrency;

    @Column(name = "TMS_EMAIL2", length = 60)
    private String tmsEmail2;

    @Column(name = "TMS_NOTES2", length = 2000)
    private String tmsNotes2;

    @Column(name = "TMS_PROCESADO", length = 1, nullable = false)
    private String tmsProcesado;

    @Column(name = "TMS_ADDDATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date tmsAdddate;

    @Column(name = "TMS_SHIPMENTORDERID", length = 50)
    private String tmsShipmentorderid;

    @Column(name = "TMS_DOOR", length = 50)
    private String tmsDoor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Shipment> shipments;
    }
}
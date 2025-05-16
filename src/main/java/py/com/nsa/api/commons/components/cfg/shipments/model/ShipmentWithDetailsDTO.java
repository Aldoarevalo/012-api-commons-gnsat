package py.com.nsa.api.commons.components.cfg.shipments.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import py.com.nsa.api.commons.components.cfg.shipmentsDet.model.ShipmentDet;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShipmentWithDetailsDTO extends Shipment {
    private List<DetalleOrdenDTO> orderdetails;

    public Shipment toShipment() {
        return Shipment.builder()
                .tmsWhseid(this.getTmsWhseid())
                .tmsStorerkey(this.getTmsStorerkey())
                .tmsExternorderkey(this.getTmsExternorderkey())
                .tmsExternorderkey2(this.getTmsExternorderkey2())
                .tmsConsigneekey(this.getTmsConsigneekey())
                .tmsCompany(this.getTmsCompany())
                .tmsRazonSocial(this.getTmsRazonSocial())
                .tmsRuc(this.getTmsRuc())
                .tmsAddress1(this.getTmsAddress1())
                .tmsContact1(this.getTmsContact1())
                .tmsPhone1(this.getTmsPhone1())
                .tmsEmail1(this.getTmsEmail1())
                .tmsCity(this.getTmsCity())
                .tmsState(this.getTmsState())
                .tmsCountry(this.getTmsCountry())
                .tmsLatitud(this.getTmsLatitud())
                .tmsLongitud(this.getTmsLongitud())
                .tmsInvoiceDate(this.getTmsInvoiceDate())
                .tmsOrderDate(this.getTmsOrderDate())
                .tmsOrderTrip(this.getTmsOrderTrip())
                .tmsInvoiceType(this.getTmsInvoiceType())
                .tmsInvoiceAmount(this.getTmsInvoiceAmount())
                .tmsCurrency(this.getTmsCurrency())
                .tmsEmail2(this.getTmsEmail2())
                .tmsNotes2(this.getTmsNotes2())
                .tmsAdddate(new Date())
                .tmsProcesado("N")
                .tmsDoor(this.getTmsDoor())
                .build();
    }

    @Data
    public static class DetalleOrdenDTO {
        private String tmsExternlineno;
        private String tmsSku;
        private String tmsDescsku;
        private String tmsUom;
        private Double tmsOriginalqty;
        private Double tmsWgt;
        private Double tmsVolumen;

        public ShipmentDet toShipmentDet(Shipment shipment) {
            return ShipmentDet.builder()
                    .tmsWhseid(shipment.getTmsWhseid())
                    .tmsStorerkey(shipment.getTmsStorerkey())
                    .tmsExternorderkey(shipment.getTmsExternorderkey())
                    .tmsExternlineno(this.tmsExternlineno)
                    .tmsSku(this.tmsSku)
                    .tmsDescsku(this.tmsDescsku)
                    .tmsUom(this.tmsUom)
                    .tmsOriginalqty(new java.math.BigDecimal(String.valueOf(this.tmsOriginalqty)))
                    .tmsWgt(new java.math.BigDecimal(String.valueOf(this.tmsWgt)))
                    .tmsVolumen(new java.math.BigDecimal(String.valueOf(this.tmsVolumen)))
                    .build();
        }
    }
}
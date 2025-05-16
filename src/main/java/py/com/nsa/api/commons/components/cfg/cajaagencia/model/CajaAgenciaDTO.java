package py.com.nsa.api.commons.components.cfg.cajaagencia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CajaAgenciaDTO {
    private String pais;
    private String ciudad;
    private String tipoAgencia;
    private String agencia;
    private String usuario;
    private String tesorero;
    private String caja;
    private String moneda;
    private String estado;
    private BigDecimal saldo;
    private String id;

    // Constructor para convertir Object[] a DTO
    public CajaAgenciaDTO(Object[] values) {

        System.out.println(values);
        if (values != null && values.length >= 10) {
            // Safe conversion for each field
            this.pais = values[0] != null ? String.valueOf(values[0]) : null;
            this.ciudad = values[1] != null ? String.valueOf(values[1]) : null;
            this.tipoAgencia = values[2] != null ? String.valueOf(values[2]) : null;
            this.agencia = values[3] != null ? String.valueOf(values[3]) : null;
            this.usuario = values[4] != null ? String.valueOf(values[4]) : null;
            this.tesorero = values[5] != null ? String.valueOf(values[5]) : null;
            this.caja = values[6] != null ? String.valueOf(values[6]) : null;
            this.moneda = values[7] != null ? String.valueOf(values[7]) : null;
            this.estado = values[10] != null ? String.valueOf(values[10]) : null;
            // Handle BigDecimal specifically
            this.saldo = values[11] != null ? (values[11] instanceof BigDecimal) ? 
                        (BigDecimal)values[11] : 
                        new BigDecimal(String.valueOf(values[11])) : null;
         this.id = values[12] != null ? String.valueOf(values[12]) : null;

           
        }
    }
}
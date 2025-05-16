package py.com.nsa.api.commons.components.cfg.vendedor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.vendedor.model.Vendedor;
import py.com.nsa.api.commons.components.cfg.vendedor.model.pk.VendedorPK;
import py.com.nsa.api.commons.components.cfg.vendedor.repository.VendedorRepository;

import java.util.List;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public Vendedor.MensajeRespuesta getVendedoresAll() {
        try {
            List<Vendedor> vendedores = vendedorRepository.findAll();
            if (vendedores.isEmpty()) {
                return new Vendedor.MensajeRespuesta(200L, "No se encontraron vendedores.", null);
            }
            return new Vendedor.MensajeRespuesta(200L, "Vendedores obtenidos exitosamente.", vendedores);
        } catch (Exception e) {
            return new Vendedor.MensajeRespuesta(500L, "Error al obtener los vendedores: " + e.getMessage(), null);
        }
    }

    public Vendedor.MensajeRespuesta insertarVendedor(Vendedor vendedor) {
        try {
            Long nextVendCodigo = vendedorRepository.getNextVendCodigoByCarCodigo(
                    vendedor.getCarCodigo());
            vendedor.setVendCodigo(nextVendCodigo);
            Vendedor nuevoVendedor = vendedorRepository.save(vendedor);
            return new Vendedor.MensajeRespuesta(200L, "Vendedor creado exitosamente.", List.of(nuevoVendedor));
        } catch (Exception e) {
            return new Vendedor.MensajeRespuesta(500L, "Error al insertar el vendedor: " + e.getMessage(), null);
        }
    }

    public Vendedor.MensajeRespuesta updateVendedor(Vendedor vendedor) {
        try {
            // Crear la clave primaria compuesta
            VendedorPK vendedorPK = new VendedorPK(vendedor.getVendCodigo(), vendedor.getCarCodigo());

            // Verificar si el vendedor con la clave primaria compuesta existe
            if (vendedorRepository.existsById(vendedorPK)) {
                Vendedor updatedVendedor = vendedorRepository.save(vendedor);
                return new Vendedor.MensajeRespuesta(200L, "Vendedor actualizado exitosamente.", List.of(updatedVendedor));
            } else {
                return new Vendedor.MensajeRespuesta(204L, "Vendedor no encontrado.", null);
            }
        } catch (Exception e) {
            return new Vendedor.MensajeRespuesta(500L, "Error al actualizar el vendedor: " + e.getMessage(), null);
        }
    }


    public Vendedor.MensajeRespuesta deleteVendedor(Long vendCodigo, Long carCodigo) {
        try {
            // Crear la clave primaria compuesta
            VendedorPK vendedorPK = new VendedorPK(vendCodigo, carCodigo);

            // Verificar si el vendedor con la clave primaria compuesta existe
            if (vendedorRepository.existsById(vendedorPK)) {
                vendedorRepository.deleteById(vendedorPK);
                return new Vendedor.MensajeRespuesta(200L, "Vendedor eliminado exitosamente.", null);
            } else {
                return new Vendedor.MensajeRespuesta(204L, "Vendedor no encontrado.", null);
            }
        } catch (Exception e) {
            return new Vendedor.MensajeRespuesta(500L, "Error al eliminar el vendedor: " + e.getMessage(), null);
        }
    }

}

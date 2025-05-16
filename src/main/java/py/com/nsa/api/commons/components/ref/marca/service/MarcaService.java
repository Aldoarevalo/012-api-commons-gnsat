package py.com.nsa.api.commons.components.ref.marca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.marca.model.Marca;
import py.com.nsa.api.commons.components.ref.marca.repository.MarcaRepository;

import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public Marca.MensajeRespuesta getMarcasall() {
        try {
            List<Marca> marcas = marcaRepository.findAll();
            if (marcas != null && !marcas.isEmpty()) {
                return new Marca.MensajeRespuesta("ok", "Marcas obtenidas exitosamente.", marcas);
            } else {
                return new Marca.MensajeRespuesta("info", "No se encontraron marcas.", null);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener las marcas: " + e.getMessage());
            e.printStackTrace();
            return new Marca.MensajeRespuesta("error", "Error al obtener las marcas: " + e.getMessage(), null);
        }
    }

    public Marca.MensajeRespuesta insertMarca(Marca marca) {
        // Validar si ya existe una marca con el mismo nombre
        if (marcaRepository.existsBymarDescripcionIgnoreCase(marca.getMarDescripcion())) {
            return new Marca.MensajeRespuesta("error", "Ya existe una marca con el mismo nombre.", null);
        }
        try {
            Marca insertedMarca = marcaRepository.save(marca);
            return new Marca.MensajeRespuesta("ok", "Marca insertada exitosamente.", List.of(insertedMarca));
        } catch (Exception e) {
            System.err.println("Error al insertar la marca: " + e.getMessage());
            e.printStackTrace();
            return new Marca.MensajeRespuesta("error", "Error al insertar la marca: " + e.getMessage(), null);
        }
    }

    public Marca.MensajeRespuesta updateMarca(Marca marca) {
        try {
            // Verificar si ya existe una marca con el mismo nombre
            if (marcaRepository.existsBymarDescripcionIgnoreCase(marca.getMarDescripcion())) {
                return new Marca.MensajeRespuesta("error", "Ya existe una marca con el mismo nombre.", null);
            }

            Marca updatedMarca = marcaRepository.save(marca);
            return new Marca.MensajeRespuesta("ok", "Marca actualizada exitosamente.", List.of(updatedMarca));
        } catch (Exception e) {
            System.err.println("Error al actualizar la marca: " + e.getMessage());
            e.printStackTrace();
            return new Marca.MensajeRespuesta("error", "Error al actualizar la marca: " + e.getMessage(), null);
        }
    }

    public Marca.MensajeRespuesta deleteMarca(Long marCodigo) {
        try {
            if (marcaRepository.existsById(marCodigo)) {
                marcaRepository.deleteById(marCodigo);
                return new Marca.MensajeRespuesta("ok", "Marca con código " + marCodigo + " eliminada exitosamente",
                        null);
            } else {
                return new Marca.MensajeRespuesta("error", "Marca con código " + marCodigo + " no encontrada", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar la Marca porque está referenciado por otros registros.";
            return new Marca.MensajeRespuesta("error", "Error al eliminar la Marca: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Marca.MensajeRespuesta("error",
                    "Error al eliminar la marca con código " + marCodigo + ": " + e.getMessage(), null);
        }
    }

}

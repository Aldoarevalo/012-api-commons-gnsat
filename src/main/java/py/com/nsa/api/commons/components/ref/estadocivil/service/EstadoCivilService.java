package py.com.nsa.api.commons.components.ref.estadocivil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.estadocivil.model.EstadoCivil;
import py.com.nsa.api.commons.components.ref.estadocivil.repository.EstadoCivilRepository;

import java.util.List;

@Service
public class EstadoCivilService {

    @Autowired
    private EstadoCivilRepository estadoCivilRepository;

    public EstadoCivil.MensajeRespuesta getEstadosCivilesAll(String keyword) {
        try {
            List<EstadoCivil> estadosCiviles;
            if (keyword == null || keyword.isEmpty()) {
                estadosCiviles = estadoCivilRepository.findAll();
            } else {
                estadosCiviles = estadoCivilRepository.findByEciDescripcionStartingWith(keyword);
            }
            if (estadosCiviles.isEmpty()) {
                return new EstadoCivil.MensajeRespuesta(204L, "No se encontraron estados civiles.", null);
            }
            return new EstadoCivil.MensajeRespuesta(200L, "Estados civiles obtenidos exitosamente.", estadosCiviles);
        } catch (Exception e) {
            System.err.println("Error al obtener los estados civiles: " + e.getMessage());
            e.printStackTrace();
            return new EstadoCivil.MensajeRespuesta(500L, "Error al obtener los estados civiles: " + e.getMessage(), null);
        }
    }

    public EstadoCivil.MensajeRespuesta insertarEstadoCivil(EstadoCivil estadoCivil) {
        try {
            if (estadoCivilRepository.existsByEciDescripcionIgnoreCase(estadoCivil.getEciDescripcion())) {
                return new EstadoCivil.MensajeRespuesta(204L,
                        "Ya existe un estado civil con la misma descripción.", null);
            }
            EstadoCivil nuevoEstadoCivil = estadoCivilRepository.save(estadoCivil);
            return new EstadoCivil.MensajeRespuesta(200L,
                    "Estado civil creado exitosamente.", List.of(nuevoEstadoCivil));
        } catch (Exception e) {
            System.err.println("Error al insertar el estado civil: " + e.getMessage());
            e.printStackTrace();
            return new EstadoCivil.MensajeRespuesta(500L, "Error al insertar el estado civil: " + e.getMessage(), null);
        }
    }

    public EstadoCivil.MensajeRespuesta updateEstadoCivil(EstadoCivil estadoCivil) {
        try {
            if (estadoCivilRepository.existsByEciDescripcionIgnoreCase(estadoCivil.getEciDescripcion())) {
                return new EstadoCivil.MensajeRespuesta(204L,
                        "Ya existe un estado civil con la misma descripción.", null);
            }
            if (estadoCivil.getEciCodigo() == null || !estadoCivilRepository.existsById(estadoCivil.getEciCodigo())) {
                return new EstadoCivil.MensajeRespuesta(204L, "Estado Civil no encontrado.", null);
            }
            EstadoCivil updatedEstadoCivil = estadoCivilRepository.save(estadoCivil);
            return new EstadoCivil.MensajeRespuesta(200L, "Estado civil actualizado exitosamente.", List.of(updatedEstadoCivil));
        } catch (Exception e) {
            System.err.println("Error al actualizar el estado civil: " + e.getMessage());
            e.printStackTrace();
            return new EstadoCivil.MensajeRespuesta(500L, "Error al actualizar el estado civil: " + e.getMessage(), null);
        }
    }

    public EstadoCivil.MensajeRespuesta deleteEstadoCivil(Long eciCodigo) {
        try {
            if (estadoCivilRepository.existsById(eciCodigo)) {
                estadoCivilRepository.deleteById(eciCodigo);
                return new EstadoCivil.MensajeRespuesta(200L, "Estado civil con código " + eciCodigo + " eliminado exitosamente", null);
            } else {
                return new EstadoCivil.MensajeRespuesta(204L, "Estado civil con código " + eciCodigo + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el Estado Civil porque está referenciado por otros registros.";
            return new EstadoCivil.MensajeRespuesta(204L, "Error al eliminar el Estado Civil: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoCivil.MensajeRespuesta(500L, "Error al eliminar el estado civil con código " + eciCodigo + ": " + e.getMessage(), null);
        }
    }
}

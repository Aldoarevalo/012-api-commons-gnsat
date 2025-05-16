package py.com.nsa.api.commons.components.ref.cargo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.cargo.model.Cargo;
import py.com.nsa.api.commons.components.ref.cargo.repository.CargoRepository;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public Cargo.MensajeRespuesta getCargosAll(String keyword) {
        try {
            List<Cargo> cargos;
            if (keyword == null || keyword.isEmpty()) {
                cargos = cargoRepository.findAll();
            } else {
                cargos = cargoRepository.findByCarDescripcionOrGrupoCargoDescripcionStartingWith(keyword);
            }
            if (cargos.isEmpty()) {
                return new Cargo.MensajeRespuesta(204L, "No se encontraron cargos.", null);
            }
            return new Cargo.MensajeRespuesta(200L, "Cargos obtenidos exitosamente.", cargos);
        } catch (Exception e) {
            return new Cargo.MensajeRespuesta(500L, "Error al obtener los cargos: " + e.getMessage(), null);
        }
    }

    public Cargo.MensajeRespuesta insertarCargo(Cargo cargo) {
        try {
            if (cargoRepository.existsByCarDescripcionIgnoreCase(cargo.getCarDescripcion())) {
                return new Cargo.MensajeRespuesta(204L, "Ya existe un cargo con la misma descripción.", null);
            }
            Cargo nuevoCargo = cargoRepository.save(cargo);
            return new Cargo.MensajeRespuesta(200L, "Cargo creado exitosamente.", List.of(nuevoCargo));
        } catch (Exception e) {
            return new Cargo.MensajeRespuesta(500L, "Error al insertar el cargo: " + e.getMessage(), null);
        }
    }

    public Cargo.MensajeRespuesta updateCargo(Cargo cargo) {
        try {
            if (cargoRepository.existsByCarDescripcionIgnoreCase(cargo.getCarDescripcion())) {
                return new Cargo.MensajeRespuesta(204L, "Ya existe un cargo con la misma descripción.", null);
            }
            if (cargo.getCarCodigo() == null || !cargoRepository.existsById(cargo.getCarCodigo())) {
                return new Cargo.MensajeRespuesta(204L, "Cargo no encontrado.", null);
            }
            Cargo updatedCargo = cargoRepository.save(cargo);
            return new Cargo.MensajeRespuesta(200L, "Cargo actualizado exitosamente.", List.of(updatedCargo));
        } catch (Exception e) {
            return new Cargo.MensajeRespuesta(500L, "Error al actualizar el cargo: " + e.getMessage(), null);
        }
    }

    public Cargo.MensajeRespuesta deleteCargo(Long carCodigo) {
        try {
            if (cargoRepository.existsById(carCodigo)) {
                cargoRepository.deleteById(carCodigo);
                return new Cargo.MensajeRespuesta(200L, "Cargo con código " + carCodigo + " eliminado exitosamente", null);
            } else {
                return new Cargo.MensajeRespuesta(204L, "Cargo con código " + carCodigo + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el Cargo porque está referenciado por otros registros.";
            return new Cargo.MensajeRespuesta(204L, "Error al eliminar el Cargo: " + mensaje, null);
        } catch (Exception e) {
            // Otros errores generales
            return new Cargo.MensajeRespuesta(500L, "Error al eliminar el Cargo con código " + carCodigo + ": " + e.getMessage(), null);
        }
    }
}

package py.com.nsa.api.commons.components.cfg.departamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.departamento.model.Departamento;
import py.com.nsa.api.commons.components.cfg.departamento.repository.DepartamentoRepository;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;

import java.util.List;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Departamento.MensajeRespuesta getDepartamentosAll() {
        try {
            List<Departamento> departamentos = departamentoRepository.findAll();
            if (departamentos.isEmpty()) {
                return new Departamento.MensajeRespuesta(204L, "No se encontraron departamentos.", null);
            }
            return new Departamento.MensajeRespuesta(200L, "Departamentos obtenidos exitosamente.", departamentos);
        } catch (Exception e) {
            return new Departamento.MensajeRespuesta(500L, "Error al obtener los departamentos: " + e.getMessage(), null);
        }
    }

    public Departamento.MensajeRespuesta insertarDepartamento(Departamento departamento) {
        try {
            if (departamentoRepository. existsByDpDescripcionIgnoreCaseAndPaCod(departamento.getDpDescripcion(), departamento.getPaCod())) {
                return new Departamento.MensajeRespuesta(204L, "Ya existe un departamento con la misma descripción y mismo pais.", null);
            }
            Departamento nuevoDepartamento = departamentoRepository.save(departamento);
            return new Departamento.MensajeRespuesta(200L, "Departamento creado exitosamente.", List.of(nuevoDepartamento));
        } catch (Exception e) {
            return new Departamento.MensajeRespuesta(500L, "Error al insertar el departamento: " + e.getMessage(), null);
        }
    }

    public Departamento.MensajeRespuesta updateDepartamento(Departamento departamento) {
        try {
            if (departamentoRepository. existsByDpDescripcionIgnoreCaseAndPaCod(departamento.getDpDescripcion(), departamento.getPaCod())) {
                return new Departamento.MensajeRespuesta(204L, "Ya existe un departamento con la misma descripción y mismo pais.", null);
            }
            if (departamento.getDpCod() == null || !departamentoRepository.existsById(departamento.getDpCod())) {
                return new Departamento.MensajeRespuesta(204L, "Departamento no encontrado.", null);
            }
            Departamento updatedDepartamento = departamentoRepository.save(departamento);
            return new Departamento.MensajeRespuesta(200L, "Departamento actualizado exitosamente.", List.of(updatedDepartamento));
        } catch (Exception e) {
            return new Departamento.MensajeRespuesta(500L, "Error al actualizar el departamento: " + e.getMessage(), null);
        }
    }

    public Departamento.MensajeRespuesta deleteDepartamento(Long dpCod) {
        try {
            if (departamentoRepository.existsById(dpCod)) {
                departamentoRepository.deleteById(dpCod);
                return new Departamento.MensajeRespuesta(200L, "Departamento eliminado exitosamente", null);
            } else {
                return new Departamento.MensajeRespuesta(204L, "Departamento con código " + dpCod + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error
            String mensaje = "No se puede eliminar el Departamento porque está referenciado por otros registros"; // Mensaje
            // personalizado
            return new Departamento.MensajeRespuesta(204L,
                    "Error al eliminar el Departamento: " + mensaje, null);
        } catch (Exception e) {
            return new Departamento.MensajeRespuesta(500L, "Error al eliminar el departamento con código " + dpCod + ": " + e.getMessage(), null);
        }
    }
}

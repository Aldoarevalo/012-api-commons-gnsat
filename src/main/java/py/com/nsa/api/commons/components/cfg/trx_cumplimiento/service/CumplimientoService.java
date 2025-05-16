package py.com.nsa.api.commons.components.cfg.trx_cumplimiento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.trx_cumplimiento.model.Cumplimiento;
import py.com.nsa.api.commons.components.cfg.trx_cumplimiento.repository.CumplimientoRepository;
import py.com.nsa.api.commons.components.ref.moneda.model.Moneda;

import java.util.List;

@Service
public class CumplimientoService {

    @Autowired
    private CumplimientoRepository repository;

    public Cumplimiento.MensajeRespuesta getCumplimientosAll() {
        try {
            List<Cumplimiento> cumplimientos = repository.findAll();
            if (cumplimientos.isEmpty()) {
                return new Cumplimiento.MensajeRespuesta(204L, "No se encontraron Cumplimientos.", null);
            }
            return new Cumplimiento.MensajeRespuesta(200L, "Cumplimientos obtenidos exitosamente.", cumplimientos);
        } catch (Exception e) {
            return new Cumplimiento.MensajeRespuesta(500L, "Error al obtener los Cumplimientos: " + e.getMessage(), null);
        }
    }


    public Cumplimiento.MensajeRespuesta getCumplimientosFiltered(Cumplimiento filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("cuCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("ageCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("cuAuditor", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("cuTipo", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());

            Example<Cumplimiento> example = Example.of(filtro, matcher);
            List<Cumplimiento> cumplimientos = repository.findAll(example);

            if (!cumplimientos.isEmpty()) {
                return new Cumplimiento.MensajeRespuesta(200L, "Cumplimientos encontrados", cumplimientos);
            } else {
                return new Cumplimiento.MensajeRespuesta(204L, "No se encontraron cumplimientos", null);
            }
        } catch (Exception e) {
            return new Cumplimiento.MensajeRespuesta(500L, "Error al obtener los cumplimientos: " + e.getMessage(), null);
        }
    }

    public Cumplimiento.MensajeRespuesta insert(Cumplimiento cumplimiento) {
        try {
            Cumplimiento insertedCumplimiento = repository.save(cumplimiento);
            return new Cumplimiento.MensajeRespuesta(200L, "Cumplimiento insertado exitosamente.", List.of(insertedCumplimiento));
        } catch (Exception e) {
            return new Cumplimiento.MensajeRespuesta(500L, "Error al insertar el Cumplimiento: " + e.getMessage(), null);
        }
    }

    public Cumplimiento.MensajeRespuesta update(Cumplimiento cumplimiento) {
        try {
            Cumplimiento updatedCumplimiento = repository.save(cumplimiento);
            return new Cumplimiento.MensajeRespuesta(200L, "Cumplimiento actualizado exitosamente.", List.of(updatedCumplimiento));
        } catch (Exception e) {
            return new Cumplimiento.MensajeRespuesta(500L, "Error al actualizar el Cumplimiento: " + e.getMessage(), null);
        }
    }


    public Cumplimiento.MensajeRespuesta deletecumplimiento(Long cuCod) {
        try {
            if (repository.existsById(cuCod)) {
                repository.deleteById(cuCod);
                return new Cumplimiento.MensajeRespuesta(200L, "Cumplimiento eliminado exitosamente", null);

            } else {
                return new Cumplimiento.MensajeRespuesta(204L, "Cumplimiento " + cuCod + " no encontrada",
                        null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el cumplimiento de la agencia porque está referenciada por otros registros.";
            return new Cumplimiento.MensajeRespuesta(204L, "Error al eliminar el Cumplimiento: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Cumplimiento.MensajeRespuesta(500L,
                    "Error al eliminar el cumplimiento con código " + cuCod + ": " + e.getMessage(), null);
        }
    }


}

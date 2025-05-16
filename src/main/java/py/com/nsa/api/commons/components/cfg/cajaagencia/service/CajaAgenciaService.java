package py.com.nsa.api.commons.components.cfg.cajaagencia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import py.com.nsa.api.commons.components.cfg.cajaagencia.model.CajaAgencia;
import py.com.nsa.api.commons.components.cfg.cajaagencia.model.CajaAgenciaDTO;
import py.com.nsa.api.commons.components.cfg.cajaagencia.model.request.UpdateOperacionRequest;
import py.com.nsa.api.commons.components.cfg.cajaagencia.repository.CajaAgenciaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CajaAgenciaService {

    @Autowired
    private CajaAgenciaRepository repository;

    public CajaAgencia.MensajeRespuesta getAllCajas() {
        try {
            List<CajaAgencia> cajas = repository.findAll();
            if (cajas.isEmpty()) {
                return new CajaAgencia.MensajeRespuesta(204L, "No se encontraron cajas de agencia.", null);
            }
            return new CajaAgencia.MensajeRespuesta(200L, "Cajas de agencia obtenidas exitosamente.", cajas);
        } catch (Exception e) {
            return new CajaAgencia.MensajeRespuesta(500L, "Error al obtener las cajas de agencia: " + e.getMessage(), null);
        }
    }

    public CajaAgencia.MensajeRespuesta insert(CajaAgencia caja) {
        try {
            CajaAgencia insertedCaja = repository.save(caja);
            return new CajaAgencia.MensajeRespuesta(200L, "Caja de agencia insertada exitosamente.", List.of(insertedCaja));
        } catch (Exception e) {
            return new CajaAgencia.MensajeRespuesta(500L, "Error al insertar la caja de agencia: " + e.getMessage(), null);
        }
    }

    public CajaAgencia.MensajeRespuesta update(CajaAgencia caja) {
        try {
            CajaAgencia updatedCaja = repository.save(caja);
            return new CajaAgencia.MensajeRespuesta(200L, "Caja de agencia actualizada exitosamente.", List.of(updatedCaja));
        } catch (Exception e) {
            return new CajaAgencia.MensajeRespuesta(500L, "Error al actualizar la caja de agencia: " + e.getMessage(), null);
        }
    }

    public boolean deleteById(Long codigo) {
        try {
            repository.deleteById(codigo);
            return true;
        } catch (EmptyResultDataAccessException | DataIntegrityViolationException e) {
            return false;
        }
    }

    public List<CajaAgenciaDTO> buscarCajasAgenciasPorFiltros(
            Long paisCod,
            Long ciudadCod,
            String tipoAgencia,
            Long agenciaCod,
            Long usuarioCod) {

        // Llamada al repositorio con los filtros (pueden ser nulos)
        List<Object[]> results = repository.findCajasAgenciasByFiltros(
                paisCod, ciudadCod, tipoAgencia, agenciaCod, usuarioCod);

        // Convertir los resultados a DTOs
        return results.stream()
                .map(CajaAgenciaDTO::new)
                .collect(Collectors.toList());
    }


    public List<CajaAgenciaDTO> buscarCajasAgenciasPorUsuarioyAgencia(
            Long agenciaCod,
            Long usuarioCod) {

        // Llamada al repositorio con los filtros (pueden ser nulos)
        List<Object[]> results = repository.findCajasAgenciasByUser(
               agenciaCod, usuarioCod);

        System.out.println(results);

        // Convertir los resultados a DTOs
        return results.stream()
                .map(CajaAgenciaDTO::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public CajaAgencia.MensajeRespuesta updateOperacionCajas(UpdateOperacionRequest request) {
        try {
            if (request.getCajaIds() == null || request.getCajaIds().isEmpty()) {
                return new CajaAgencia.MensajeRespuesta(400L, "La lista de cajas no puede estar vacía", null);
            }

            if (!"A".equals(request.getOperacion()) && !"C".equals(request.getOperacion())) {
                return new CajaAgencia.MensajeRespuesta(400L, "La operación debe ser 'A' o 'C'", null);
            }

            int updatedCount = repository.updateOperacionByCajaIds(request.getCajaIds(), request.getOperacion());
            
            if (updatedCount > 0) {
                List<CajaAgencia> updatedCajas = repository.findAllById(request.getCajaIds());
                return new CajaAgencia.MensajeRespuesta(200L, 
                    String.format("Se actualizaron %d cajas exitosamente", updatedCount), 
                    updatedCajas);
            } else {
                return new CajaAgencia.MensajeRespuesta(404L, "No se encontraron cajas para actualizar", null);
            }
        } catch (Exception e) {
            return new CajaAgencia.MensajeRespuesta(500L, 
                "Error al actualizar las cajas: " + e.getMessage(), null);
        }
    }
}

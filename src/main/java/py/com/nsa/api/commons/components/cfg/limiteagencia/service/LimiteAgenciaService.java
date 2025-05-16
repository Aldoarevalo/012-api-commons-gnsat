package py.com.nsa.api.commons.components.cfg.limiteagencia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.limiteagencia.model.LimiteAgencia;
import py.com.nsa.api.commons.components.cfg.limiteagencia.model.pk.LimiteAgenciaPK;
import py.com.nsa.api.commons.components.cfg.limiteagencia.repository.LimiteAgenciaRepository;
import py.com.nsa.api.commons.components.cfg.servicio_agencia.model.ServicioAgencia;
import py.com.nsa.api.commons.components.cfg.servicio_agencia.model.pk.ServicioAgenciaPK;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class LimiteAgenciaService {

    @Autowired
    private LimiteAgenciaRepository limiteAgenciaRepository;

    public LimiteAgencia.MensajeRespuesta getLimitesAgenciaAll() {
        try {
            List<LimiteAgencia> limitesAgencia = limiteAgenciaRepository.findAll();
            if (limitesAgencia.isEmpty()) {
                return new LimiteAgencia.MensajeRespuesta(204L, "No se encontraron límites de agencia.", null);
            }
            return new LimiteAgencia.MensajeRespuesta(200L, "Límites de agencia obtenidos exitosamente.",
                    limitesAgencia);
        } catch (Exception e) {
            e.printStackTrace();
            return new LimiteAgencia.MensajeRespuesta(500L, "Error al obtener límites de agencia: " + e.getMessage(),
                    null);
        }
    }

    public LimiteAgencia.MensajeRespuesta getLimitesAgenciaFiltered(LimiteAgencia filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                // Campos de identificación
                .withMatcher("agCod", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("tlCodLimite", ExampleMatcher.GenericPropertyMatchers.exact())
                // Campos de valores
                .withMatcher("laMonto", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("laCantidad", ExampleMatcher.GenericPropertyMatchers.exact())
                // Campos de usuario
                .withMatcher("usuCodCreador", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("usuCodMod", ExampleMatcher.GenericPropertyMatchers.exact())
                // Campos de fecha
                .withMatcher("laFechaCreacion", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("laFechaMod", ExampleMatcher.GenericPropertyMatchers.exact())
                // Configuración para relaciones
                .withMatcher("agencia.agCod", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("tipoLimite.tlCod", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("usuarioCreador.usuCod", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("usuarioModificador.usuCod", ExampleMatcher.GenericPropertyMatchers.exact());

        Example<LimiteAgencia> example = Example.of(filtro, matcher);
        List<LimiteAgencia> limitesAgencia = limiteAgenciaRepository.findAll(example);

        if (!limitesAgencia.isEmpty()) {
            return new LimiteAgencia.MensajeRespuesta(200L, "Límites de agencia encontrados", limitesAgencia);
        } else {
            return new LimiteAgencia.MensajeRespuesta(204L, "No se encontraron límites de agencia", null);
        }
    }

    public LimiteAgencia.MensajeRespuesta insertarLimiteAgencia(LimiteAgencia limiteAgencia) {
        try {

            // Validar datos requeridos
            if (limiteAgencia.getAgCod() == null || limiteAgencia.getTlCodLimite() == null) {
                return new LimiteAgencia.MensajeRespuesta(204L, "El código de agencia y código de limite son requeridos", null);
            }

            limiteAgencia.setLaFechaCreacion(new Date());
            limiteAgencia.setLaFechaMod(new Date());

            LimiteAgencia nuevoLimiteAgencia = limiteAgenciaRepository.save(limiteAgencia);
            return new LimiteAgencia.MensajeRespuesta(200L, "Límite de agencia creado exitosamente.",
                    List.of(nuevoLimiteAgencia));
        } catch (Exception e) {
            e.printStackTrace();
            return new LimiteAgencia.MensajeRespuesta(500L, "Error al insertar el límite de agencia: " + e.getMessage(),
                    null);
        }
    }

    /*public LimiteAgencia.MensajeRespuesta updateLimiteAgencia(LimiteAgencia limiteAgencia) {
        try {
            if (limiteAgencia.getAgCod() == null || limiteAgencia.getTlCodLimite() == null) {
                return new LimiteAgencia.MensajeRespuesta(204L,
                        "Códigos de agencia o tipo de límite no pueden ser nulos.", null);
            }

            // Crear la clave primaria compuesta
            ServicioAgenciaPK limiteAgenciaPK = new ServicioAgenciaPK(limiteAgencia.getAgCod(), limiteAgencia.getTlCodLimite());

            // Verificar si existe el registro con la clave primaria compuesta
            if (!limiteAgenciaRepository.existsById(limiteAgenciaPK)) {
                return new LimiteAgencia.MensajeRespuesta(204L, "Límite de agencia no encontrado.", null);
            }

            LimiteAgencia updatedLimiteAgencia = limiteAgenciaRepository.save(limiteAgencia);
            return new LimiteAgencia.MensajeRespuesta(200L, "Límite de agencia actualizado exitosamente.",
                    List.of(updatedLimiteAgencia));
        } catch (Exception e) {
            e.printStackTrace();
            return new LimiteAgencia.MensajeRespuesta(500L,
                    "Error al actualizar el límite de agencia: " + e.getMessage(), null);
        }
    }*/


    public LimiteAgencia.MensajeRespuesta updateLimiteAgencia(LimiteAgencia limiteAgencia) {
        try {
            // Validación de valores nulos
            if (limiteAgencia.getAgCod() == null || limiteAgencia.getTlCodLimite() == null) {
                return new LimiteAgencia.MensajeRespuesta(204L,
                        "Códigos de agencia o tipo de límite no pueden ser nulos.", null);
            }

            // Crear el objeto PK para verificar existencia
            LimiteAgenciaPK pk = new LimiteAgenciaPK(limiteAgencia.getAgCod(), limiteAgencia.getTlCodLimite());

            // Verificar si existe
            if (!limiteAgenciaRepository.existsById(pk)) {
                return new LimiteAgencia.MensajeRespuesta(204L, "No se encontró el limite de agencia especificado", null);
            }

            // Establecer la fecha de modificación actual
            Date fechaModificacion = new Date();
            System.out.println("Fecha de Modificación: " + fechaModificacion);
            limiteAgencia.setLaFechaMod(fechaModificacion);

            // Obtener el servicio existente
            LimiteAgencia existingAgencialimit = limiteAgenciaRepository.findById(pk)
                    .orElseThrow(() -> new RuntimeException("Servicio de agencia no encontrado."));


            // Llamada directa al repositorio
            int registrosActualizados = limiteAgenciaRepository.actualizarLimiteAgencia(
                    limiteAgencia.getLaMonto(),
                    limiteAgencia.getLaCantidad(),
                    limiteAgencia.getAgCod(),
                    limiteAgencia.getTlCodLimite(),
                    limiteAgencia.getUsuCodMod(),
                    fechaModificacion,  // Usar directamente la fecha creada
                    limiteAgencia.getAgCodold(),
                    limiteAgencia.getTlCodold()
            );
            System.out.println("LimiteAgencia después de la actualización de la fecha: " + limiteAgencia);
            // Comprobar si se actualizaron registros
            if (registrosActualizados > 0) {
                LimiteAgenciaPK limiteAgenciaPK = new LimiteAgenciaPK(limiteAgencia.getAgCod(), limiteAgencia.getTlCodLimite());
                LimiteAgencia updatedLimiteAgencia = limiteAgenciaRepository.findById(limiteAgenciaPK).orElse(null);

                if (updatedLimiteAgencia != null) {
                    return new LimiteAgencia.MensajeRespuesta(200L,
                            "Límite de agencia actualizado exitosamente.",
                            List.of(updatedLimiteAgencia));
                } else {
                    return new LimiteAgencia.MensajeRespuesta(500L,
                            "Límite de agencia actualizado, pero no se pudieron recuperar los datos.",
                            null);
                }
            } else {
                return new LimiteAgencia.MensajeRespuesta(500L,
                        "No se pudo actualizar el límite de agencia.",
                        null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new LimiteAgencia.MensajeRespuesta(500L,
                    "Error al actualizar el límite de agencia: " + e.getMessage(), null);
        }
    }
    public LimiteAgencia.MensajeRespuesta deleteLimiteAgencia(Long agCod, Long tlCod) {
        try {
            // Crear la clave primaria compuesta
            LimiteAgenciaPK limiteAgenciaPK = new LimiteAgenciaPK(agCod, tlCod);

            // Verificar si el registro con la clave primaria compuesta existe
            if (limiteAgenciaRepository.existsById(limiteAgenciaPK)) {
                // Eliminar el registro si existe
                limiteAgenciaRepository.deleteById(limiteAgenciaPK);
                return new LimiteAgencia.MensajeRespuesta(200L, "Límite de agencia eliminado exitosamente.", null);
            } else {
                // Si no existe, devolver respuesta con código 204
                return new LimiteAgencia.MensajeRespuesta(204L, "Límite de Agencia no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el límite de agencia está referenciado por otros registros
            String mensaje = "No se puede eliminar el Límite de Agencia porque está referenciado por otros registros.";
            return new LimiteAgencia.MensajeRespuesta(204L, "Error al eliminar el Límite de Agencia: " + mensaje, null);
        } catch (Exception e) {
            // Otros errores generales
            return new LimiteAgencia.MensajeRespuesta(500L, "Error al eliminar el Límite de Agencia: " + e.getMessage(),
                    null);
        }
    }

}

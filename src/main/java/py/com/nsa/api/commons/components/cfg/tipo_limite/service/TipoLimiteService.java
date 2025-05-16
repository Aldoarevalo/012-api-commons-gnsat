package py.com.nsa.api.commons.components.cfg.tipo_limite.service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import py.com.nsa.api.commons.components.cfg.cliente.model.Cliente;
import py.com.nsa.api.commons.components.cfg.tipo_limite.model.TipoLimite;
import py.com.nsa.api.commons.components.cfg.tipo_limite.repository.TipoLimiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoLimiteService {

    @Autowired
    private TipoLimiteRepository repository;

    public TipoLimite.MensajeRespuesta getAgenciasAll() {
        try {
            List<TipoLimite> agencias = repository.findAll();

            if (agencias.isEmpty()) {
                return new TipoLimite.MensajeRespuesta(204L, "No se encontraron tipo limite.", null);
            }
            return new TipoLimite.MensajeRespuesta(200L, "Tipo limite obtenidas exitosamente.", agencias);
        } catch (Exception e) {
            System.err.println("Error al obtener tipo limite: " + e.getMessage());
            e.printStackTrace();
            return new TipoLimite.MensajeRespuesta(500L, "Error al obtener los Tipos limite: " + e.getMessage(), null);
        }
    }

    public TipoLimite.MensajeRespuesta getTipoLimitesFiltrados(TipoLimite filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("tlCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("tlDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("tlEsObligatorio", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("parMoneda", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("parServicio", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("paCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("tlControlTiempo", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("tlNotificar", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("tlTipocontrol", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("tlAccion", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("tlEstado", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("parTiempoControl", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("tlTope", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("pais.paDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("parMonedaValor.parDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("parServicioValor.parDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            Example<TipoLimite> example = Example.of(filtro, matcher);
            List<TipoLimite> tipoLimites = repository.findAll(example);

            if (!tipoLimites.isEmpty()) {
                return new TipoLimite.MensajeRespuesta(200L, "Tipos de límite encontrados", tipoLimites);
            } else {
                return new TipoLimite.MensajeRespuesta(204L, "No se encontraron tipos de límite", null);
            }
        } catch (Exception e) {
            return new TipoLimite.MensajeRespuesta(500L, "Error al filtrar tipos de límite: " + e.getMessage(), null);
        }
    }

    public TipoLimite.MensajeRespuesta insert(TipoLimite limite) {
        try {

            if (limite.getTlFechacreacion() == null) {
                limite.setTlFechacreacion(new Date());
            }

            TipoLimite insertedAgencia = repository.save(limite);
            return new TipoLimite.MensajeRespuesta(200L, "Tipo Limite insertada exitosamente.", List.of(insertedAgencia));
        } catch (Exception e) {
            System.err.println("Error al insertar el tipo de limite: " + e.getMessage());
            e.printStackTrace();
            return new TipoLimite.MensajeRespuesta(500L, "Error al insertar el tipo de limite: " + e.getMessage(), null);
        }
    }

    public TipoLimite.MensajeRespuesta update(TipoLimite limite) {
        try {

            TipoLimite existinglimite = repository.findById(limite.getTlCod())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado."));

            limite.setTlFechacreacion(existinglimite.getTlFechacreacion());
            limite.setTlFechamod(new Date());
            limite.setUsuCreador(existinglimite.getUsuCreador());

            TipoLimite updatedAgencia = repository.save(limite);
            return new TipoLimite.MensajeRespuesta(200L, "Tipo Limite actualizada exitosamente. ", List.of(updatedAgencia));
        } catch (Exception e) {
            System.err.println("Error al actualizar el tipo de limite: " + e.getMessage());
            e.printStackTrace();
            return new TipoLimite.MensajeRespuesta(500L, "Error al actualizar el tipo de limite: " + e.getMessage(), null);
        }
    }

    public TipoLimite.MensajeRespuesta deleteById(Long tlCod) {
        try {
            if (repository.existsById(tlCod)) {
                repository.deleteById(tlCod);
                return new TipoLimite.MensajeRespuesta(200L, "Tipo de Limite con código " + tlCod + " eliminado exitosamente", null);
            } else {
                return new TipoLimite.MensajeRespuesta(204L, "Tipo de Limite con código " + tlCod + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error
            String mensaje = "No se puede eliminar el Tipo Limite porque está referenciada por otros registros"; // Mensaje
            // personalizado
            return new TipoLimite.MensajeRespuesta(204L,
                    "Error al eliminar el Tipo Limite: " + mensaje, null);
        } catch (Exception e) {
            return new TipoLimite.MensajeRespuesta(500L, "Error al eliminar el Tipo Limite con código " + tlCod + ": " + e.getMessage(), null);
        }
    }
}

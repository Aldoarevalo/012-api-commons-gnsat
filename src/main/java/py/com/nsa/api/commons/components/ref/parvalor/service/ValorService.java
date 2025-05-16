package py.com.nsa.api.commons.components.ref.parvalor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import py.com.nsa.api.commons.components.ref.parvalor.repository.ValorRepository;

import java.util.List;
import java.util.*;

@Service
public class ValorService {

    @Autowired
    private ValorRepository valorRepository;

    public ParValor.MensajeRespuesta getValoresAll() {
        try {
            List<ParValor> valores = valorRepository.findAll();
            if (valores.isEmpty()) {
                return new ParValor.MensajeRespuesta(204L, "No se encontraron valores.", null);
            }
            return new ParValor.MensajeRespuesta(200L, "Valores obtenidos exitosamente.", valores);
        } catch (Exception e) {
            e.printStackTrace();
            return new ParValor.MensajeRespuesta(500L, "Error al obtener valores: " + e.getMessage(), null);
        }
    }

    public ParValor.MensajeRespuesta getValoresFiltered(ParValor filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("parValor", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("parDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("parComentario", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("parametro.pmCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("parametro.pmNombre", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            Example<ParValor> example = Example.of(filtro, matcher);
            List<ParValor> valores = valorRepository.findAll(example);

            if (!valores.isEmpty()) {
                return new ParValor.MensajeRespuesta(200L, "Valores encontrados", valores);
            } else {
                return new ParValor.MensajeRespuesta(204L, "No se encontraron valores", null);
            }
        } catch (Exception e) {
            return new ParValor.MensajeRespuesta(500L, "Error al filtrar valores: " + e.getMessage(), null);
        }
    }

    public ParValor.MensajeRespuesta insertarValor(ParValor valor) {
        try {
            if (valorRepository.existsByParValorIgnoreCase(valor.getParValor())) {
                return new ParValor.MensajeRespuesta(204L, "Ya existe un Valor con la misma clave.", null);
            }

            ParValor nuevoValor = valorRepository.save(valor);
            return new ParValor.MensajeRespuesta(200L, "Valor creado exitosamente.", List.of(nuevoValor));
        } catch (Exception e) {
            e.printStackTrace();
            return new ParValor.MensajeRespuesta(500L, "Error al insertar el valor: " + e.getMessage(), null);
        }
    }


    public ParValor.MensajeRespuesta updateValor(ParValor valor) {
        try {
            if (!valorRepository.existsById(valor.getParValor())) {
                return new ParValor.MensajeRespuesta(204L, "Valor no encontrado.", null);
            }

            ParValor updatedValor = valorRepository.save(valor);
            return new ParValor.MensajeRespuesta(200L, "Valor actualizado exitosamente.", List.of(updatedValor));
        } catch (Exception e) {
            e.printStackTrace();
            return new ParValor.MensajeRespuesta(500L, "Error al actualizar el Valor: " + e.getMessage(), null);
        }
    }


    public ParValor.MensajeRespuesta deleteValor(String parValor) {
        try {
            if (valorRepository.existsById(parValor)) {
                valorRepository.deleteById(parValor);
                return new ParValor.MensajeRespuesta(200L, "Valor eliminado exitosamente.", null);
            } else {
                return new ParValor.MensajeRespuesta(204L, "Valor no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el parámetro está referenciado por otros registros
            String mensaje = "No se puede eliminar el Valor porque está referenciado por otros registros.";
            return new ParValor.MensajeRespuesta(204L, "Error al eliminar el Valor: " + mensaje, null);
        }  catch (Exception e) {
            return new ParValor.MensajeRespuesta(500L, "Error al eliminar el Valor: " + e.getMessage(), null);
        }
    }

    public Map<String, Object> getByDescripcion(String pmNombre) {
        try {
            List<Object[]> results = valorRepository.findByDescripcion(pmNombre);
            Map<String, Object> response = new HashMap<>();
            List<Map<String, Object>> valores = new ArrayList<>();

            for (Object[] result : results) {
                Map<String, Object> detalle = new HashMap<>();
                detalle.put("parValor", result[0]);
                detalle.put("parDescripcion", result[1]);
                detalle.put("parComentario", result[2]);

                // Datos del parámetro relacionado
                detalle.put("pmCod", result[3]);
                detalle.put("pmNombre", result[4]);

                valores.add(detalle);
            }

            if (valores.isEmpty()) {
                response.put("status", 204);
                response.put("mensaje", "No se encontraron valores con la descripción proporcionada");
                response.put("detalles", null);
            } else {
                response.put("status", 200);
                response.put("mensaje", "Valores obtenidos exitosamente");
                response.put("detalles", valores);
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("mensaje", "Error al obtener los detalles del valor: " + e.getMessage());
            errorResponse.put("detalles", null);
            return errorResponse;
        }
    }
}
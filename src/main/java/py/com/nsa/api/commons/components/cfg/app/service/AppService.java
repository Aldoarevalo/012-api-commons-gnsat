package py.com.nsa.api.commons.components.cfg.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.app.model.App;
import py.com.nsa.api.commons.components.cfg.app.repository.AppRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppService {
    @Autowired
    private AppRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(AppService.class);

    public App.MensajeRespuesta getAll() {
        try {
            List<App> agencias = repository.findAll();

            if (agencias.isEmpty()) {
                return new App.MensajeRespuesta(204L, "No se encontraron Apps.", null);
            }
            return new App.MensajeRespuesta(200L, "Apps obtenidas exitosamente.", agencias);
        } catch (Exception e) {
            System.err.println("Error al obtener los documentos: " + e.getMessage());
            e.printStackTrace();
            return new App.MensajeRespuesta(500L, "Error al obtener los Tipos de Documentos: " + e.getMessage(),
                    null);
        }
    }

}
package py.com.nsa.api.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

@SpringBootApplication
public class ApicommonsApplication {
	private static final Logger logger = LoggerFactory.getLogger(ApicommonsApplication.class);

	public static void main(String[] args) {
		logger.info("<=== Iniciando {} ... ===>", ApicommonsApplication.class.getName());

		SpringApplication app = new SpringApplication(ApicommonsApplication.class);
		app.addListeners(new PropertySourceLogger());
		app.run(args);

		logger.info("<=== {} Iniciado correctamente ===>", ApicommonsApplication.class.getName());
	}

	static class PropertySourceLogger implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
		private static final Logger propLogger = LoggerFactory.getLogger("PropertySourceLogger");

		@Override
		public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
			ConfigurableEnvironment environment = event.getEnvironment();
			propLogger.info("<=== ARCHIVOS DE PROPIEDADES CARGADOS ===>");
			for (PropertySource<?> propertySource : environment.getPropertySources()) {
				propLogger.info("Cargando: {}", propertySource.getName());
			}
			propLogger.info("<======================================>");
		}
	}
}
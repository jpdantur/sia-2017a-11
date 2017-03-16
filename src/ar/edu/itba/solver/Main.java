
	package ar.edu.itba.solver;

	import javax.xml.bind.JAXBException;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	import com.google.inject.Guice;
	import com.google.inject.Injector;
	import com.google.inject.Stage;

	import ar.edu.itba.solver.config.ConfigurationLoader;
	import ar.edu.itba.solver.engine.Solver;
	import ar.edu.itba.solver.support.InjectionModule;
	import ar.edu.itba.solver.support.Message;

		/**
		* <p>La clase principal de la aplicación, se encarga de instalar los
		* parámetros de operación y de resolver el juego. Representa el
		* punto de entrada del <i>master-thread</i>.</p>
		*/

	public final class Main {

		// Logger:
		private static final Logger logger
			= LoggerFactory.getLogger(Main.class);

		// Inyector de dependencias:
		private static final Injector injector
			= Guice.createInjector(Stage.PRODUCTION, new InjectionModule());

		// Configuraciones:
		private static final ConfigurationLoader configurator
			= injector.getInstance(ConfigurationLoader.class);

		// Archivo de configuración para el GPS:
		private static final String SOLVER_CONFIGURATION_FILENAME
			= "solver.xml";

		/**
		* <p>Punto de entrada principal de la aplicación. Se encarga de
		* manipular algunos errores globales.</p>
		*
		* @param arguments
		*	Actualmente, este parámetro no se usa.
		*/

		public static void main(final String [] arguments) {

			try {

				// Instalar la configuración:
				configurator.loadSolverConfig(SOLVER_CONFIGURATION_FILENAME);

				// Ejecutar el sistema GPS:
				injector.getInstance(Solver.class).resolve();
			}
			catch (final JAXBException exception) {

				logger.error(
						Message.INVALID_CONFIGURATION.getMessage());
			}
			catch (final Exception exception) {

				logger.error(
						Message.UNKNOWN.getMessage(),
						Main.class.getSimpleName());
			}
		}
	}

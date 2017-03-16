
	package ar.edu.itba.solver.engine;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	import com.google.inject.Inject;

	import ar.edu.itba.solver.support.Message;
	import ar.edu.itba.solver.config.ConfigurationLoader;
	import ar.edu.itba.solver.config.SolverConfiguration;

		/**
		* <p>Esta clase se encarga de resolver el juego provisto mediante la
		* configuración y estrategia seleccionada, generando una solución en
		* caso de que esta exista bajo las restricciones especificadas.</p>
		*/

	public final class Solver {

		// Logger:
		private static final Logger logger
			= LoggerFactory.getLogger(Solver.class);

		// Componente de configuración:
		private final ConfigurationLoader configurator;

		@Inject
		public Solver(final ConfigurationLoader configurator) {

			this.configurator = configurator;
		}

		/**
		* <p>Utilizando el archivo de configuración, extrae la estrategia y el
		* tablero de juego, y lo resuelve utilizando el sistem GPS.</p>
		*/

		public void resolve() {

			final SolverConfiguration config = configurator.getSolverConfig();

			logger.info(
					"Utilizando la estrategia: {}",
					config.getStrategy());

			// ...

			logger.info(
					Message.SHUTDOWN.getMessage());
		}
	}

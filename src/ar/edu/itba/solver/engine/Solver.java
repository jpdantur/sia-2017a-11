
	package ar.edu.itba.solver.engine;

	import java.util.Optional;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	import com.google.inject.Inject;

	import ar.edu.itba.solver.support.Message;
	import ar.edu.itba.solver.support.SIAReader;
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

		// Componentes de especificación:
		private final ConfigurationLoader configurator;
		private final SIAReader reader;

		@Inject
		public Solver(
				final ConfigurationLoader configurator,
				final SIAReader reader) {

			this.configurator = configurator;
			this.reader = reader;
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

			// Obtener la especificación del juego:
			final Optional<Object> game = reader.loadGame(config.getBoard());

			if (game.isPresent()) {

				// Continuará...
			}
			else logger.info("No hay juego!");

			logger.info(
					Message.SHUTDOWN.getMessage());
		}
	}

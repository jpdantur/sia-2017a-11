
	package ar.edu.itba.solver.engine;

	import java.io.IOException;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	import com.google.inject.Inject;

	import ar.edu.itba.solver.support.Message;
	import ar.edu.itba.solver.support.SIAReader;
	import ar.edu.itba.solver.config.ConfigurationLoader;
	import ar.edu.itba.solver.config.SolverConfiguration;
	import ar.edu.itba.solver.engine.gps.GPSEngine;
	import ar.edu.itba.solver.engine.gps.GPSNode;
	import ar.edu.itba.solver.engine.gps.SearchStrategy;
	import ar.edu.itba.solver.engine.gps.api.GPSProblem;

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
		* tablero de juego, y lo resuelve utilizando el sistema GPS.</p>
		*/

		public void solve() {

			// Leer la configuración:
			final SolverConfiguration config
				= configurator.getSolverConfig();

			logger.info(
					"Utilizando la estrategia: {}.",
					config.getStrategy());

			logger.info(
					"Utilizando la heurística: {}.",
					config.getHeuristic());

			try {

				// Obtener la especificación del problema:
				final GPSProblem problem
					= reader.loadProblem(config.getProblem());

				// Obtener la estrategia:
				final SearchStrategy strategy
					= SearchStrategy.valueOf(config.getStrategy());

				// Ejecutar el motor de búsqueda:
				final GPSEngine engine
					= new GPSEngine(problem, strategy);

				final long startTime = System.nanoTime();
				engine.findSolution();

				logger.info(
						"Tiempo de procesamiento: {} seg.",
						(System.nanoTime() - startTime) / 1.0E9);

				if (!engine.isFailed()) {

					final GPSNode solution = engine.getSolutionNode();

					if (true == config.getPrint())
						logger.info("Solución:\n\n{}", solution.getSolution());

					logger.info("Profundidad: {}", depth(solution));
					logger.info("Costo: {}", solution.getCost());
				}
				else logger.info("No se encontró solución!");

				logger.info(
						"Nodos explotados: {}",
						engine.getExplosionCounter());

				logger.info(
						"Nodos frontera: {}",
						engine.getOpen().size());
			}
			catch (final IOException exception) {

				logger.error(
						Message.CANNOT_READ_SPECIFICATION.getMessage(),
						config.getProblem());
			}
			catch (final IllegalArgumentException exception) {

				logger.error(
						Message.NON_EXISTENT_STRATEGY.getMessage(),
						config.getStrategy());
			}

			logger.info(Message.SHUTDOWN.getMessage());
		}

		private int depth(final GPSNode solution) {

			final GPSNode parent = solution.getParent();
			if (parent == null) return 0;
			return 1 + depth(parent);
		}
	}


	package ar.edu.itba.solver.problem;

	import java.util.Optional;

	import ar.edu.itba.solver.engine.gps.api.GPSRule;
	import ar.edu.itba.solver.engine.gps.api.GPSState;

		/**
		* <p>Representa una acción o regla disponible para expandir un nodo en
		* el grafo de búsqueda. En este caso, la acción especifica la
		* aplicación de un nuevo color, sobre la celda distinguida, y sobre
		* sus adyacentes similares.</p>
		*/

	public final class ChangeColour implements GPSRule {

		// El color que aplica esta acción:
		final int colour;

		public ChangeColour(final int colour) {

			this.colour = colour;
		}

		@Override
		public Integer getCost() {

			return 1;
		}

		@Override
		public String getName() {

			return "Change to colour " + colour;
		}

		@Override
		public Optional<GPSState> evalRule(GPSState state) {

			final State fzState = (State) state;

			if (colour == fzState.getDistinguished())
				return Optional.empty();

			return Optional.of(fzState
					.deepCopy()
					.paint(colour));
		}
	}


	package ar.edu.itba.solver.problem;

	import ar.edu.itba.solver.engine.gps.api.GPSRule;
	import ar.edu.itba.solver.engine.gps.api.GPSState;
	import ar.edu.itba.solver.engine.gps.exception.NotAppliableException;

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
		public GPSState evalRule(GPSState state)
				throws NotAppliableException {

			final State fzState = (State) state;

			if (colour == fzState.getDistinguished())
				throw new NotAppliableException();

			// TODO: falta evaluar la regla y devolver el nuevo estado...
			final int rows = fzState.getRows();
			final int columns = fzState.getColumns();

			final int [][] oldBoard = fzState.getBoard();
			final int [][] newBoard = new int[rows][columns];

			for (int x = 0; x < rows; ++x)
				for (int y = 0; y < columns; ++y)
					newBoard[x][y] = oldBoard[x][y];

			return new State(null);
		}
	}

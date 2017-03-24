
	package ar.edu.itba.solver.problem;

	import java.util.ArrayList;
	import java.util.List;
	import java.util.stream.IntStream;

	import ar.edu.itba.solver.engine.gps.api.GPSProblem;
	import ar.edu.itba.solver.engine.gps.api.GPSRule;
	import ar.edu.itba.solver.engine.gps.api.GPSState;

		/**
		* <p>La clase que representa el problema a resolver, en este caso,
		* el juego <b>Fill Zone</b>. Se encarga de establecer el estado
		* inicial y el conjunto de reglas disponibles.</p>
		*/

	public final class FillZone implements GPSProblem {

		private final State initialState;
		private final List<GPSRule> rules;

		private final int rows;
		private final int columns;
		private final int colours;
		
		private final String heuristic;

		public FillZone(final int [] header, final int [][] board) {

			this.initialState = new State(board);
			this.rules = new ArrayList<>();

			this.rows = header[0];
			this.columns = header[1];
			this.colours = header[2];
			
			//TODO Setear desde la configuración
			this.heuristic = "distinct";

			// Generar conjunto de reglas:
			initRules(colours);
		}

		@Override
		public GPSState getInitState() {

			return initialState;
		}

		@Override
		public boolean isGoal(GPSState state) {

			final State fzState = (State) state;
			return fzState.isUniform();
		}

		@Override
		public List<GPSRule> getRules() {

			return rules;
		}

		@Override
		public Integer getHValue(GPSState state) {
			
				if (heuristic.equals("distinct"))				
					return ((State)state).getDistinct();
			else
				//TODO: Definir segunda heurística
				return 0;
					
		}

		public int getRows() {

			return rows;
		}

		public int getColumns() {

			return columns;
		}

		public int getColours() {

			return colours;
		}

		private void initRules(final int colours) {

			IntStream.range(0, colours)
				.mapToObj(ChangeColour::new)
				.forEachOrdered(rule -> rules.add(rule));
		}
	}

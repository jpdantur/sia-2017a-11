
	package ar.edu.itba.solver.fillzone;

		/**
		* ¿Debe implementar alguna interfaz?
		*/

	public final class Game {

		private final int rows;
		private final int columns;
		private final int colours;

		public Game(
				final int rows,
				final int columns,
				final int colours) {

			this.rows = rows;
			this.columns = columns;
			this.colours = colours;
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
	}


	package ar.edu.itba.solver.problem;

	import java.util.Arrays;

	import ar.edu.itba.solver.engine.gps.api.GPSState;

		/**
		* <p>Representa el estado del juego <i>Fill Zone</i>, es decir, una
		* matriz coloreada, y una paleta de colores.<p>
		*/

	public final class State implements GPSState {

		private final int [][] board;

		public State(final int [][] board) {

			this.board = board;
		}

		public int getDistinguished() {

			return board[0][0];
		}

		public int get(final int row, final int column) {

			return board[row][column];
		}

		public void set(final int row, final int column, final int colour) {

			board[row][column] = colour;
		}

		public boolean isUniform() {

			final int distinguished = getDistinguished();

			for (int [] row : board)
				for (int tile : row)
					if (tile != distinguished)
						return false;

			return true;
		}

		@Override
		public boolean equals(Object obj) {

			if (obj instanceof State) {

				final State state = (State) obj;
				return Arrays.deepEquals(board, state.board);
			}
			return false;
		}
	}

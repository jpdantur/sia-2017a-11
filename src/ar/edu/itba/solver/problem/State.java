
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

		public int getRows() {

			return board.length;
		}

		public int getColumns() {

			return board[0].length;
		}

		public int getDistinguished() {

			return board[0][0];
		}

		public boolean isUniform() {

			final int distinguished = getDistinguished();

			for (int [] row : board)
				for (int tile : row)
					if (tile != distinguished)
						return false;

			return true;
		}

		public State deepCopy() {

			final int rows = getRows();
			final int columns = getColumns();

			final int [][] newBoard = new int[rows][columns];

			for (int x = 0; x < rows; ++x)
				for (int y = 0; y < columns; ++y)
					newBoard[x][y] = board[x][y];

			return new State(newBoard);
		}

		public State paint(final int colour) {

			// Se define recursivamente, por simplicidad:
			final int distinguished = getDistinguished();
			paint(0, 0, distinguished, colour);
			return this;
		}

		private void paint(final int x, final int y,
				final int distinguished, final int colour) {

			if (0 <= x && x < getRows())
				if (0 <= y && y < getColumns())
					if (board[x][y] == distinguished) {

						board[x][y] = colour;
						paint(x - 1, y, distinguished, colour);		// Up
						paint(x + 1, y, distinguished, colour);		// Down
						paint(x, y - 1, distinguished, colour);		// Left
						paint(x, y + 1, distinguished, colour);		// Right
					}
		}

		@Override
		public boolean equals(Object obj) {

			if (obj instanceof State) {

				final State state = (State) obj;
				return Arrays.deepEquals(board, state.board);
			}
			return false;
		}

		@Override
		public int hashCode() {

			return Arrays.deepHashCode(board);
		}

		@Override
		public String toString() {

			// TODO: hay que cambiarlo para que muestre los tableros...
			return "Distinguished: " + getDistinguished() + "\n";
		}
	}

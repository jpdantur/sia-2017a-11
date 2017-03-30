
	package ar.edu.itba.solver.problem;

	import java.awt.Point;
	import java.util.Arrays;
	import java.util.HashSet;
	import java.util.IntSummaryStatistics;
	import java.util.Set;

		/**
		* <p>Representa el estado del juego <i>Fill Zone</i>, es decir, una
		* matriz coloreada, y una paleta de colores.<p>
		*/

	public final class MatrixState implements State {

		private final int [][] board;

		public MatrixState(final int [][] board) {

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

		public int getDistinct() {

			final Set<Integer> distinct = new HashSet<>();

			for (int [] row : board)
				for (int tile : row)
					distinct.add(tile);

			// Debe ser igual a 0 en los estados solución:
			return distinct.size() - 1;
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

			return new MatrixState(newBoard);
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
		
		private Set<Point> adjacent;
		private Set<Point> visited;
		private int[] colors;
		
		public Integer getFrontier(int colours){
			
			int distinguished = getDistinguished();
			colors = new int[colours];			
			adjacent= new HashSet<Point>();
			visited = new HashSet<Point>();
			
			frontier(0,0,distinguished);
			
			int frontierSize = adjacent.size();
			
			IntSummaryStatistics stat = Arrays.stream(colors).summaryStatistics();
			
			int max=stat.getMax();
			
			return (max == 0 ? 0 : 1 + frontierSize - max );
		}
		
		private void frontier(final int x, final int y, final int distinguished){
			
			final Point point = new Point(x, y);
			if (0 <= x && x < getRows()){
				if (0 <= y && y < getColumns()){
					if (!visited.contains(point)){
						
						visited.add(point);
						
						if (board[x][y] == distinguished) {
							
							frontier(x - 1, y, distinguished);		// Up
							frontier(x + 1, y, distinguished);		// Down
							frontier(x, y - 1, distinguished);		// Left
							frontier(x, y + 1, distinguished);		// Right
							
							}
						else{
							adjacent.add(point);
							colors[board[x][y]]++;							
						}
					}
				}
			}
			
		}

		@Override
		public boolean equals(Object obj) {

			if (obj instanceof MatrixState) {

				final MatrixState state = (MatrixState) obj;
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

			final StringBuilder result = new StringBuilder();

			result.append("> Color: ")
				.append(getDistinguished())
				.append("\n");

			// Mostrar heurística DISTINCT:
//			 result.append("> H Value: ")
//				.append(getDistinct())
//				.append("\n");
			

			for (int i = 0; i < getRows(); ++i) {

				for (int j = 0; j < getColumns(); j++)
					result.append(board[i][j] + " ");

				result.append("\n");
			}

			return result.append("\n").toString();
		}
	}

package ar.edu.itba.solver.problem;

import java.util.*;

import ar.edu.itba.solver.engine.gps.api.GPSState;
import ar.edu.itba.util.Graph;

public class GraphState implements GPSState {
	
	private IslandGraph board;
	private Island distinguished;
	
	/*public static void main(String[] args) {
		int [][] board = {{0,3,4,3,1},
							{5,5,5,4,2},
							{2,2,1,0,0},
							{3,2,3,4,4}};
		GraphState s = new GraphState(board);
		System.out.println(s.board);
		System.out.println(s.board.getDistinguished());
		s.board.paint(5);
		System.out.println(s.board);

		System.out.println(s.board.getDistinguished());
		s.board.paint(1);
		System.out.println(s.board);
		System.out.println(s.board.getDistinguished());
		s.board.paint(0);
		System.out.println(s.board);
		System.out.println(s.board.getDistinguished());
		s.board.paint(2);
		System.out.println(s.board);
		System.out.println(s.board.getDistinguished());
		s.board.paint(1);
		System.out.println(s.board);
		System.out.println(s.board.getDistinguished());
		s.board.paint(3);
		System.out.println(s.board);
		System.out.println(s.board.getDistinguished());
		s.board.paint(4);
		System.out.println("---------");
		System.out.println(s.board);
		System.out.println(s.board.getDistinguished());
	}*/
	
	public GraphState(int [][] board) {
		this.board = getIslandGraph(board);
	}
	
	public boolean isUniform() {
		return board.getElements().size()==1;
	}


	
	private class Tuple {
		int x;
		int y;
		
		public Tuple(int x, int y) {
			this.x=x;
			this.y=y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Tuple other = (Tuple) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		private GraphState getOuterType() {
			return GraphState.this;
		}
	}
	

	private class Node {
		Island island;
		Tuple position;

		Node(Island island, Tuple position) {
			this.island = island;
			this.position = position;
		}
	}
	
	private IslandGraph getIslandGraph(int [][] board) {
		Island[][] usedCells = new Island[board.length][board[0].length];
		Queue<Node> queue = new LinkedList<Node>();
		IslandGraph graph = new IslandGraph();
		queue.offer(new Node(null, new Tuple(0,0)));
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			Island neighbourIsland = node.island;
			Tuple position = node.position;
			int x = position.x;
			int y = position.y;
			if (usedCells[x][y] == null) {
				Island island = new Island(board[x][y]);
				if (x==0 && y==0) {
					graph.addDistinguished(island);
				}
				else {
					graph.putNode(island);
				}
				getIslandsGraph(x, y, usedCells, board[x][y], queue, island, board);
			} 
			if (neighbourIsland != null) {
				graph.setNeighbour(usedCells[x][y], neighbourIsland);
			}
		}
		return graph;
	}

	private void getIslandsGraph(int x, int y, Island[][] usedCells,
			int color, Queue<Node> queue, Island island, int[][] board) {
		if (x < 0 || y < 0 || x >= board.length
				|| y >= board[0].length || usedCells[x][y] != null) {
			return;
		}
		if (board[x][y] != color) {
			queue.offer(new Node(island, new Tuple(x,y)));
			return;
		}
		usedCells[x][y] = island;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (Math.abs(i) != Math.abs(j)) {
					getIslandsGraph(x + i, y + j, usedCells, color, queue,
							island,board);
				}
			}
		}
		return;
	}
}

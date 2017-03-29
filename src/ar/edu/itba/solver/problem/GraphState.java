package ar.edu.itba.solver.problem;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

import ar.edu.itba.solver.engine.gps.api.GPSState;

public class GraphState implements GPSState, State {

	private IslandGraph board;

	public GraphState(int [][] board) {
		this.board = getIslandGraph(board);
	}

	public boolean isUniform() {
		return board.getElements().size() == 1;
	}	

	@Override
	public int getDistinguished() {

		return board.getDistinguished().color;
	}

	@Override
	public State deepCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State paint(final int colour) {

		board.paint(colour);
		return this;
	}

	public int getFurthestDistance() {

		// TODO: heurística 'graph'...
		return 0;
	}

	private class Node {
		Island island;
		Point position;

		Node(Island island, Point position) {
			this.island = island;
			this.position = position;
		}
	}

	private IslandGraph getIslandGraph(int [][] board) {
		Island[][] usedCells = new Island[board.length][board[0].length];
		Queue<Node> queue = new LinkedList<Node>();
		IslandGraph graph = new IslandGraph();
		queue.offer(new Node(null, new Point(0,0)));
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			Island neighbourIsland = node.island;
			Point position = node.position;
			int x = position.x;
			int y = position.y;
			if (usedCells[x][y] == null) {
				Island island = new Island(board[x][y]);
				if (x == 0 && y == 0) {
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
			queue.offer(new Node(island, new Point(x,y)));
			return;
		}
		usedCells[x][y] = island;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (Math.abs(i) != Math.abs(j)) {
					getIslandsGraph(x + i, y + j, usedCells, color, queue,
							island, board);
				}
			}
		}
		return;
	}
}

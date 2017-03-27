package ar.edu.itba.solver.problem;

import java.util.*;

import ar.edu.itba.solver.engine.gps.api.GPSState;

public class GraphState implements GPSState {
	

	private Island distinguished;
	
	public GraphState(final int[][] board) {
		//TODO: convert board to graph
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((distinguished == null) ? 0 : distinguished.hashCode());
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
		GraphState other = (GraphState) obj;
		if (distinguished == null) {
			if (other.distinguished != null)
				return false;
		} else if (!distinguished.equals(other.distinguished))
			return false;
		return true;
	}

	private class Island {

		int color;
		List<Island> neighbors = new ArrayList<>();
		
		public Island (int color) {
			this.color = color;
		}
		
		public void merge () {
			List<Island> newNeighbors = new ArrayList<>();
			for (Island a: neighbors) {
				if (a.color!=this.color) {
					newNeighbors.add(a);
				}
				else {
					for (Island n:a.neighbors) {
						if (n!=this)
							newNeighbors.add(n);
					}
				}
			}
			this.neighbors = newNeighbors;
		}
		
		public boolean equals (Island other) {
			if (color != other.color)
				return false;
			if (neighbors.size()!=other.neighbors.size())
				return false;
			for (int i=0;i<neighbors.size();i++) {
				if (!neighbors.get(i).equals(other.neighbors.get(i)))
					return false;
			}
			return true;
		}
		
		
	}
	
	public GraphState deepCopy() {
		//TODO
		
		return this;
		
	}
	
	public boolean isUniform() {
		return distinguished.neighbors.isEmpty();
	}
	
	public int getDistinguished() {
		return distinguished.color;
	}
	
	public GraphState paint(final int color) {
		distinguished.color=color;
		distinguished.merge();
		return this;
	}
}

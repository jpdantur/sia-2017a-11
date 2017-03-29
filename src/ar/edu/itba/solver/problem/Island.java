package ar.edu.itba.solver.problem;

import java.awt.Point;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Island implements Serializable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + color;
		result = prime * result + ((positions == null) ? 0 : positions.hashCode());
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
		Island other = (Island) obj;
		if (color != other.color)
			return false;
		if (positions == null) {
			if (other.positions != null)
				return false;
		} else if (!positions.equals(other.positions))
			return false;
		return true;
	}

	int color;
	Set<Point> positions = new HashSet<>();
	public Island(int color, Point initial) {
		this.color=color;
		positions.add(initial);
	}
	
	public Island(int color, Set<Point> positions) {
		this.color=color;
		this.positions=positions;
	}

	public void addPoint(Point p) {
		positions.add(p);
	}

	public String toString() {
		String res = ""+color+": ";
		for (Point p:positions) {
			res+=" ("+p.x+","+p.y+") ";
		}
		return res;
	}
	
	public void addPoints(Set<Point> ps) {
		positions.addAll(ps);
	}
}

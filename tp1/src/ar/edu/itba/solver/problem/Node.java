package ar.edu.itba.solver.problem;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


class Node {
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (elem == null) {
			if (other.elem != null)
				return false;
		} else if (!elem.equals(other.elem))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elem == null) ? 0 : elem.hashCode());
		return result;
	}

	Island elem;
	boolean isVisited;
	Set<Node> neighbours;
	boolean isDistinguished = false;

	Node(Island elem, boolean isDistinguished) {
		this.elem = elem;
		neighbours = new HashSet<Node>();
		this.isDistinguished=isDistinguished;
	}

	public void addNeighbour(Node node) {
		neighbours.add(node);
	}

	public void visit() {
		isVisited = true;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public Set<Node> getNeighbours() {
		return neighbours;
	}

	public boolean setNeighbour(Node n){
		return this.neighbours.add(n);
	}

	public Island getElement() {
		return elem;
	}

	public void reset(){
		isVisited = false;
	}

	@Override
	public String toString() {
		return elem.toString();
	}

	public Node deepCopy(Map<Node, Node> isomorphism) {
	    Node copy = isomorphism.get(this);
	    if (copy == null) {
	        copy = new Node(new Island(elem.color, elem.positions),isDistinguished);
	        isomorphism.put(this, copy);
	        for (Node connection: neighbours) {
	            copy.neighbours.add(connection.deepCopy(isomorphism));
	        }
	    }
	    return copy;
	}
	
}

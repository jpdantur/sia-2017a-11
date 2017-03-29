package ar.edu.itba.solver.problem;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.soap.SOAPBinding;

import com.cedarsoftware.*;
import com.cedarsoftware.util.DeepEquals;

public class IslandGraph{

	private Set<Node> nodes;
	private Node distinguished;

	public IslandGraph() {
		nodes = new HashSet<>();

	}

	public Island getDistinguished() {
		return distinguished.elem;
	}

	public void addDistinguished(Island distinguished) {
		Node dist = new Node(distinguished,true);
		nodes.add(dist);
		this.distinguished=dist;
	}

	public Set<Island> getElements(){
		Set<Island> elements = new HashSet<>();
		for(Node n : nodes){
			elements.add(n.getElement());
		}
		return elements;
	}

	private Node getNode(Island elem) {
		Node node = null;
		for (Node n : nodes) {
			if (n.getElement().equals(elem)) {
				node = n;
				break;
			}
		}
		return node;
	}

	public void paint (int color) {
		Set <Node> newNeighbors = new HashSet<>();
		Set <Point> newPoints = new HashSet<>();
		for (Node node:distinguished.neighbours) {
				if (node.getElement().color==color) {
					for (Node newNeighbor:node.neighbours) {
						if (newNeighbor!=distinguished) {
							newNeighbors.add(newNeighbor);
							newNeighbor.neighbours.remove(node);
							newNeighbor.neighbours.add(distinguished);
						}
					}
					newPoints.addAll(node.elem.positions);
					nodes.remove(node);
				} else {
				newNeighbors.add(node);
				}
		}
		distinguished.elem.color=color;
		distinguished.elem.addPoints(newPoints);
		distinguished.neighbours=newNeighbors;
	}

	public boolean putNode(Island elem) {
		return nodes.add(new Node(elem,false));
	}

	public boolean isUniform() {
		return distinguished.neighbours.size()==0;
	}

	public boolean setNeighbour(Island elem1, Island elem2) {
		Node n1 = getNode(elem1);
		Node n2 = getNode(elem2);
		if(n1 == null || n2 == null){
			return false;
		}
		return n1.setNeighbour(n2) && n2.setNeighbour(n1);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Node node : nodes){
			sb.append(node + " -> ");
			for(Node neighbour : node.getNeighbours()){
				sb.append(neighbour + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

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
			if (!getOuterType().equals(other.getOuterType()))
				return false;
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

		private IslandGraph getOuterType() {
			return IslandGraph.this;
		}
		
	}

	public IslandGraph deepCopy() {
		  IslandGraph g = new IslandGraph();
		  g.nodes = new HashSet<>();
		  Map<Node, Node> isomorphism = new IdentityHashMap<>();
		  for (Node n : nodes) { 
			Node newNode=n.deepCopy(isomorphism);
		    g.nodes.add(newNode);
		    if (n.isDistinguished) {
		    	g.distinguished=newNode;
		    }
		  }
		  System.out.println("-----");
		  System.out.println(this);
		  System.out.println(g);
		return g;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((distinguished == null) ? 0 : distinguished.hashCode());
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
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
		IslandGraph other = (IslandGraph) obj;
		if (distinguished == null) {
			if (other.distinguished != null)
				return false;
		} else if (!distinguished.equals(other.distinguished))
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		return true;
	}

	private void clearMarks() {
		for (Node n:nodes) {
			n.isVisited=false;
		}
		
	}
}

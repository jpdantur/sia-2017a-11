package ar.edu.itba.solver.problem;

import java.util.HashSet;
import java.util.Set;

public class IslandGraph {

	private Set<Node<Island>> nodes;
	private Node<Island> distinguished;

	public IslandGraph() {
		nodes = new HashSet<>();

	}

	public Island getDistinguished() {
		return distinguished.elem;
	}

	public void addDistinguished(Island distinguished) {
		Node<Island> dist = new Node<>(distinguished);
		nodes.add(dist);
		this.distinguished=dist;
	}

	public Set<Island> getElements(){
		Set<Island> elements = new HashSet<>();
		for(Node<Island> n : nodes){
			elements.add(n.getElement());
		}
		return elements;
	}

	private Node<Island> getNode(Island elem) {
		Node<Island> node = null;
		for (Node<Island> n : nodes) {
			if (n.getElement().equals(elem)) {
				node = n;
				break;
			}
		}
		return node;
	}

	public void paint (int color) {
		distinguished.mark=1;
		Set <Node<Island>> newNeighbors = new HashSet<>();
		for (Node<Island> node:distinguished.neighbours) {
				if (node.getElement().color==color) {
					for (Node<Island> newNeighbor:node.neighbours) {
						if (newNeighbor!=distinguished) {
							newNeighbors.add(newNeighbor);
							newNeighbor.neighbours.remove(node);
							newNeighbor.neighbours.add(distinguished);
						}
					}

					nodes.remove(node);
				} else {
				newNeighbors.add(node);
				}
		}
		distinguished.elem.color=color;
		distinguished.neighbours=newNeighbors;
		distinguished.mark=0;
	}

	public boolean putNode(Island elem) {
		return nodes.add(new Node<Island>(elem));
	}

	public boolean isUniform() {
		return distinguished.neighbours.size()==0;
	}

	public boolean setNeighbour(Island elem1, Island elem2) {
		Node<Island> n1 = getNode(elem1);
		Node<Island> n2 = getNode(elem2);
		if(n1 == null || n2 == null){
			return false;
		}
		return n1.setNeighbour(n2) && n2.setNeighbour(n1);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Node<Island> node : nodes){
			sb.append(node + " -> ");
			for(Node<Island> neighbour : node.getNeighbours()){
				sb.append(neighbour + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	class Node<E> {
		E elem;
		boolean isVisited;
		Set<Node<E>> neighbours;
		int mark;

		Node(E elem) {
			mark = 0;
			this.elem = elem;
			neighbours = new HashSet<Node<E>>();
		}

		public void addNeighbour(Node<E> node) {
			neighbours.add(node);
		}

		public void visit() {
			isVisited = true;
		}

		public boolean isVisited() {
			return isVisited;
		}

		public Set<Node<E>> getNeighbours() {
			return neighbours;
		}

		public boolean setNeighbour(Node<E> n){
			return this.neighbours.add(n);
		}

		public E getElement() {
			return elem;
		}

		public void reset(){
			isVisited = false;
			mark = 0;
		}

		public void mark(int mark){
			this.mark = mark;
		}

		public int getMark(){
			return mark;
		}

		@Override
		public String toString() {
			return elem.toString();
		}

		@Override
		public int hashCode() {
			return elem.hashCode();
		}
	}
}

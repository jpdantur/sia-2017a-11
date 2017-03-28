package ar.edu.itba.util;

import java.util.*;

public class Graph<T> {

	Set<Node<T>> nodes;

	public Graph() {
		nodes = new HashSet<Node<T>>();
	}
	
	public Set<T> getElements(){
		Set<T> elements = new HashSet<T>();
		for(Node<T> n : nodes){
			elements.add(n.getElement());
		}
		return elements;
	}

	private Node<T> getNode(T elem) {
		Node<T> node = null;
		for (Node<T> n : nodes) {
			if (n.getElement().equals(elem)) {
				node = n;
				break;
			}
		}
		return node;
	}

	public boolean putNode(T elem) {
		return nodes.add(new Node<T>(elem));
	}

	public boolean setNeighbour(T elem1, T elem2) {
		Node<T> n1 = getNode(elem1);
		Node<T> n2 = getNode(elem2);
		if(n1 == null || n2 == null){
			return false;
		}
		return n1.setNeighbour(n2) && n2.setNeighbour(n1);
	}
	
	/***
	 * Returns a list of the T that are at K distance of elem
	 * If elem does not exists returns null
	 * @return
	 */
	public List<T> getKNeighbours(T elem, int k){
		List<T> ans = new LinkedList<T>();
		resetNodes();
		Node<T> root = getNode(elem);
		if(root == null){
			return null;
		}
		Queue<Node<T>> queue = new LinkedList<Node<T>>();
		queue.add(root);
		root.mark(0);
		root.visit();
		while( ! queue.isEmpty() ){
			Node<T> node = queue.poll();
			if(node == null){
				/* Should never happend, already checked */
				return ans;
			}
			if( node.getMark() == k ){
				ans.add(node.getElement());
			}else{
				for(Node<T> neighbour : node.getNeighbours()){
					if(!neighbour.isVisited()){
						neighbour.visit();
						neighbour.mark(node.getMark() + 1);
						queue.add(neighbour);
					}
				}
			}
			
		}
		return ans;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Node<T> node : nodes){
			sb.append(node.getElement().toString() + " -> ");
			for(Node<T> neighbour : node.getNeighbours()){
				sb.append(neighbour + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private void resetNodes() {
		for(Node<T> node : nodes){
			node.reset();
		}
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

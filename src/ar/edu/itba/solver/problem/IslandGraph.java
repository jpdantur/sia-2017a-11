package ar.edu.itba.solver.problem;

import java.awt.Point;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
		nodes.remove(distinguished);
		distinguished.elem.color=color;
		distinguished.elem.addPoints(newPoints);
		distinguished.neighbours=newNeighbors;
		nodes.add(distinguished);
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


    public int getFurthestDistance() {
    	Deque<Node> queue = new LinkedList<>();
    	Map<Node,Integer> distanceMap = new HashMap<>();
        queue.clear();
        distanceMap.clear();

        queue.addLast(distinguished);
        distanceMap.put(distinguished, 0);

        int maximumDistance = 0;

        while (!queue.isEmpty()) {
            Node current = queue.removeFirst();

            for (Node child : current.neighbours) {
                if (!distanceMap.containsKey(child)) {
                    int distance = distanceMap.get(current) + 1;
                    distanceMap.put(child, distance);
                    queue.addLast(child);

                    if (maximumDistance < distance) {
                        maximumDistance = distance;
                    }
                }
            }
        }

        return maximumDistance;
    }

	private void clearMarks() {
		for (Node n:nodes) {
			n.isVisited=false;
		}
		
	}
}

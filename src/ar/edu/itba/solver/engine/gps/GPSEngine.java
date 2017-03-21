package ar.edu.itba.solver.engine.gps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

import ar.edu.itba.solver.engine.gps.api.GPSProblem;
import ar.edu.itba.solver.engine.gps.api.GPSRule;
import ar.edu.itba.solver.engine.gps.api.GPSState;

public class GPSEngine {

	Deque<GPSNode> open;
	Map<GPSState, Integer> bestCosts;
	GPSProblem problem;
	long explosionCounter;
	boolean finished;
	boolean failed;
	GPSNode solutionNode;

	// Use this variable in open set order.
	protected SearchStrategy strategy;

	public GPSEngine(GPSProblem myProblem, SearchStrategy myStrategy) {
		// TODO: open = *Su queue favorito, TENIENDO EN CUENTA EL ORDEN DE LOS NODOS*
		open = new LinkedList<>();

		bestCosts = new HashMap<GPSState, Integer>();
		problem = myProblem;
		strategy = myStrategy;
		explosionCounter = 0;
		finished = false;
		failed = false;
	}

	public void findSolution() {
		if (strategy == SearchStrategy.IDDFS) {
			int counter = 0;
			while (!finished){
				explore(counter);
				counter++;
			}
		}
		else {
			explore(0);
		}
	}
	
	private void explore(int counter) {
		GPSNode rootNode = new GPSNode(problem.getInitState(), 0);
		open.add(rootNode);
		int depth=0;
		while (open.size() >= 0 &&(!(strategy == SearchStrategy.IDDFS) || depth<=counter)) {
			GPSNode currentNode = open.remove();
			if (problem.isGoal(currentNode.getState())) {
				finished = true;
				solutionNode = currentNode;
				return;
			} else {
				explode(currentNode);
			}
			depth++;
		}
		if (open.size()==0){
			failed = true;
			finished = true;
		}
	}

	private void explode(GPSNode node) {
		Collection<GPSNode> newCandidates;
		switch (strategy) {
		case BFS:
			if (bestCosts.containsKey(node.getState())) {
				return;
			}
			newCandidates = new ArrayList<>();
			addCandidates(node, newCandidates);
			for (GPSNode n:newCandidates) {
				open.add(n);
			}
			break;
		case DFS:
		case IDDFS:
			if (bestCosts.containsKey(node.getState())) {
				return;
			}
			newCandidates = new ArrayList<>();
			addCandidates(node, newCandidates);
			for (GPSNode n:newCandidates) {
				open.addFirst(n);
			}
			break;
		case GREEDY:
			newCandidates = new PriorityQueue<>(/* TODO: Comparator! */);
			addCandidates(node, newCandidates);
			// TODO: ¿Cómo se agregan los nodos a open en GREEDY?
			break;
		case ASTAR:
			if (!isBest(node.getState(), node.getCost())) {
				return;
			}
			newCandidates = new ArrayList<>();
			addCandidates(node, newCandidates);
			// TODO: ¿Cómo se agregan los nodos a open en A*?
			break;
		}
	}

	private void addCandidates(GPSNode node, Collection<GPSNode> candidates) {
		explosionCounter++;
		updateBest(node);
		for (GPSRule rule : problem.getRules()) {
			Optional<GPSState> newState = rule.evalRule(node.getState());
			if (newState.isPresent()) {
				GPSNode newNode = new GPSNode(newState.get(), node.getCost() + rule.getCost());
				newNode.setParent(node);
				candidates.add(newNode);
			}
		}
	}

	private boolean isBest(GPSState state, Integer cost) {
		return !bestCosts.containsKey(state) || cost < bestCosts.get(state);
	}

	private void updateBest(GPSNode node) {
		bestCosts.put(node.getState(), node.getCost());
	}

	// GETTERS FOR THE PEOPLE!

	public Queue<GPSNode> getOpen() {
		return open;
	}

	public Map<GPSState, Integer> getBestCosts() {
		return bestCosts;
	}

	public GPSProblem getProblem() {
		return problem;
	}

	public long getExplosionCounter() {
		return explosionCounter;
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean isFailed() {
		return failed;
	}

	public GPSNode getSolutionNode() {
		return solutionNode;
	}

	public SearchStrategy getStrategy() {
		return strategy;
	}
}

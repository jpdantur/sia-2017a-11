
	package ar.edu.itba.solver.engine.gps;

	import ar.edu.itba.solver.engine.gps.api.GPSRule;
	import ar.edu.itba.solver.engine.gps.api.GPSState;

	public class GPSNode {

		private GPSState state;
		private GPSNode parent;
		private Integer cost;
		private GPSRule rule;
		private int depth;

		public GPSNode(GPSState state, Integer cost, GPSRule generationRule) {

			this.state = state;
			this.cost = cost;
			this.rule = generationRule;
			this.depth = 0;
		}

		public int getDepth() {

			return depth;
		}

		public GPSNode getParent() {

			return parent;
		}

		public GPSState getState() {

			return state;
		}

		public Integer getCost() {

			return cost;
		}

		public void setParent(GPSNode parent) {

			this.parent = parent;
			if (parent == null) depth = 0;
			else depth = parent.depth + 1;
		}

		@Override
		public String toString() {

			return state.toString();
		}

		public String getSolution() {

			if (this.parent == null)
				return state.toString();

			return parent.getSolution() + state.toString();
		}

		public GPSRule getGenerationRule() {

			return rule;
		}

		public void setGenerationRule(GPSRule generationRule) {

			this.rule = generationRule;
		}

		@Override
		public boolean equals(Object obj) {

			if (obj instanceof GPSNode) {

				final GPSNode node = (GPSNode) obj;

				if (state != null)
					if (node.state != null)
						return state.equals(node.state);
			}
			return false;
		}
	}

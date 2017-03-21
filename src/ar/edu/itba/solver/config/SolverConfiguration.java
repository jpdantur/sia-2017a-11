
	package ar.edu.itba.solver.config;

	import javax.xml.bind.annotation.XmlElement;
	import javax.xml.bind.annotation.XmlRootElement;

		/**
		* <p>Contiene todos los parámetros configurables por el usuario que
		* administra el sistema. Para cada parámetro existe un valor por
		* defecto. Esta clase es serializable, en formato <b>XML</b> (usando
		* la extensión <i>JAXB</i>)</p>
		*/

	@XmlRootElement
	public final class SolverConfiguration {

		private String strategy /*= "IDDFS"*/;
		private String heuristic/* = "none"*/;
 
		private String problem /*= "./res/benchmarks/board4x5_6.sia"*/;

		/*
		** Getter's
		*/

		public String getStrategy() {

			return strategy;
		}

		public String getHeuristic() {

			return heuristic;
		}

		public String getProblem() {

			return problem;
		}

		/*
		** Setter's
		*/

		@XmlElement
		public void setStrategy(final String strategy) {

			this.strategy = strategy;
		}

		@XmlElement
		public void setHeuristic(final String heuristic) {

			this.heuristic = heuristic;
		}

		@XmlElement
		public void setProblem(final String problem) {

			this.problem = problem;
		}
	}

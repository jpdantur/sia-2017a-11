
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

		private String strategy = "ar.edu.itba.solver.strategy.DFS";

		/*
		** Getter's
		*/

		public String getStrategy() {

			return strategy;
		}

		/*
		** Setter's
		*/

		@XmlElement
		public void setStrategy(final String strategy) {

			this.strategy = strategy;
		}
	}

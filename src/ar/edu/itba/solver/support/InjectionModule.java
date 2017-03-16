
	package ar.edu.itba.solver.support;

	import com.google.inject.AbstractModule;

	import ar.edu.itba.solver.config.ConfigurationLoader;
	import ar.edu.itba.solver.engine.Solver;

		/**
		* <p>Este módulo contiene todos los componentes utilizados en el
		* sistema, y permite injectar los mismos utilizando <i>Google
		* Guice</i>. Esto desacopla por completo la instanciación de cada
		* subsistema.</p>
		*/

	public final class InjectionModule extends AbstractModule {

		@Override
		protected void configure() {

			// System-wide bindings:
			bind(ConfigurationLoader.class);
			requestStaticInjection(ConfigurationLoader.class);

			// Specific bindings:
			bind(Solver.class);
		}
	}

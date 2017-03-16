
	package ar.edu.itba.solver.support;

	import java.util.Optional;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	import com.google.inject.Singleton;

		/**
		* <p>Esta clase permite leer y validar una especificación del juego
		* <i>Fill Zone</i> en formato <b>*.sia</b>, y generar un estado que lo
		* represente.</p>
		*/

	@Singleton
	public final class SIAReader {

		// Logger:
		private static final Logger logger
			= LoggerFactory.getLogger(SIAReader.class);

		/**
		* <p> ... FALTA COMPLETAR! ... </p>
		*
		* @param filename
		*	La ruta hacia el archivo en formato <i>*.sia</i> que posee la
		*	especificación del juego.
		*
		* @return Opcionalmente el estado que representa el juego a resolver,
		*	o un objeto vacío si hubo un error durante la apertura o
		*	validación del mismo.
		*/

		public Optional<Object> loadGame(final String filename) {

			logger.info("Cargando el juego...");
			return Optional.empty();
		}
	}

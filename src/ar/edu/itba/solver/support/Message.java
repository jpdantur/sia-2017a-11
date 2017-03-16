
	package ar.edu.itba.solver.support;

		/**
		* <p>Se definen todos los mensajes que el sistema puede emitir
		* durante su ejecución, o durante la presencia de un estado de
		* error.</p>
		*/

	public enum Message {

		SHUTDOWN
			("Adiós!"),
		INVALID_CONFIGURATION
			("No se pudo instalar la configuración del sistema GPS."),
		UNKNOWN
			("Error desconocido (módulo {}). Contacte a un Ingeniero.");

		// El texto contenido en el mensaje:
		private final String message;

		private Message(final String message) {

			this.message = message;
		}

		/**
		* <p>Devuelve la cadena asociada a este mensaje.</p>
		*
		* @return El mensaje asociado, como <i>String</i>.
		*/

		public String getMessage() {

			return message;
		}

		/**
		* <p>Este método solo se incluye por completitud, pero se comporta de
		* forma equivalente a <i>getMessage()</i>.</p>
		*
		* @return La cadena asociada a este mensaje.
		*/

		@Override
		public String toString() {

			return getMessage();
		}
	}

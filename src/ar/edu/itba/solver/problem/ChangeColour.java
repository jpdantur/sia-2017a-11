
	package ar.edu.itba.solver.problem;

	import ar.edu.itba.solver.engine.gps.api.GPSRule;
	import ar.edu.itba.solver.engine.gps.api.GPSState;
	import ar.edu.itba.solver.engine.gps.exception.NotAppliableException;

		/**
		* <p></p>
		*/

	public final class ChangeColour implements GPSRule {

		// El color que aplica esta acción:
		final int colour;

		public ChangeColour(final int colour) {

			this.colour = colour;
		}

		@Override
		public Integer getCost() {

			return 1;
		}

		@Override
		public String getName() {

			return "Change to colour " + colour;
		}

		@Override
		public GPSState evalRule(GPSState state)
				throws NotAppliableException {

			final State fzState = (State) state;

			if (colour == fzState.getDistinguished())
				throw new NotAppliableException();

			// TODO: falta evaluar la regla y devolver el nuevo estado...
			return null;
		}
	}

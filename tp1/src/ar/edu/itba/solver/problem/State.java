
	package ar.edu.itba.solver.problem;

	import ar.edu.itba.solver.engine.gps.api.GPSState;

	public interface State extends GPSState {

		public int getDistinguished();

		public boolean isUniform();

		public State deepCopy();

		public State paint(final int colour);
	}

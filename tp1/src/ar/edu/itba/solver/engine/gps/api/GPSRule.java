package ar.edu.itba.solver.engine.gps.api;

import java.util.Optional;

/**
 * GPSRule interface.
 */
public interface GPSRule {

	/**
	 * Provides the cost of the rule.
	 * 
	 * @return The cost of the rule.
	 */
	Integer getCost();

	/**
	 * Provides the name of the rule so it can be clearly identified by an human being.
	 * 
	 * @return The name of the rule.
	 */
	String getName();

	/**
	 * Computes the next state based on the previous one. The next state is the result of applying the rule to the previous state.
	 * 
	 * @param state
	 *            The previous state of the problem.
	 * @return The next state of the problem.
	 */
	Optional<GPSState> evalRule(GPSState state);
}
package gfads.cin.ufpe.maverick.ltl.core.checker

import gfads.cin.ufpe.maverick.events.symtoms.IMaverickSymptom

/**
 * The implmentation of this interface must be thread-safe!
 * @author adalrjr1
 *
 */
interface TransitionChecker {
	/**
	 * Must be thread-safe.
	 * 
	 * Should implement rules to match a transition formula with a symptom
	 * 
	 * If symptom match with formula return true, otherwise return false and don't update automata.
	 * 
	 * @param transitionFormula
	 * @param symptom
	 * @return
	 */
	boolean check(String transitionFormula, IMaverickSymptom symptom)
}

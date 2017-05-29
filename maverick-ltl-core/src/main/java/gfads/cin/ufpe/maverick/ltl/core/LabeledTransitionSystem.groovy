package gfads.cin.ufpe.maverick.ltl.core

import gfads.cin.ufpe.maverick.events.symtoms.IMaverickSymptom
import gfads.cin.ufpe.maverick.ltl.core.checker.TransitionChecker
import jhoafparser.ast.BooleanExpression
import jhoafparser.storage.StoredAutomaton
import jhoafparser.storage.StoredState

interface LabeledTransitionSystem {
	StoredState getInitialState()
	void init(StoredAutomaton storedAutomaton, TransitionChecker cheker)
	
	/**
	 * Return a Violation event if reach an non-acceptance state
	 * implementation based on spot monitors: https://spot.lrde.epita.fr/tut11.html
	 * @param state Automata State
	 * @param symptom Symptom to be evaluated
	 * @return
	 */
	LabeledTransitionSystemEvent next(LabeledTransitionSystemState state, IMaverickSymptom symptom)
	
	Iterable getTransitions(StoredState state)
	
	Iterable getStates()
	
	StoredState getState(int label)
	
	String getTransitionLabel(BooleanExpression booleanExpression)
}

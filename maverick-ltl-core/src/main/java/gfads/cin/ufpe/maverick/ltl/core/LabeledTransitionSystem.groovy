package gfads.cin.ufpe.maverick.ltl.core

import gfads.cin.ufpe.maverick.events.MaverickSymptom
import jhoafparser.storage.StoredAutomaton
import jhoafparser.storage.StoredState

interface LabeledTransitionSystem {
	StoredState getInitialState()
	void init(StoredAutomaton storedAutomaton, TransitionChecker cheker)
	
	/**
	 * Return TRUE if reach an acceptance state
	 * @param state Automata State
	 * @param symptom Symptom to be evaluated
	 * @return
	 */
	LabeledTransitionSystemEvent next(LabeledTransitionSystemState state, MaverickSymptom symptom)
}

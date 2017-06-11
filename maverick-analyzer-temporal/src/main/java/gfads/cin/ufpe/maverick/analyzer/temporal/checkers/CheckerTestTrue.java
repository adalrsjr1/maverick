package gfads.cin.ufpe.maverick.analyzer.temporal.checkers;

import gfads.cin.ufpe.maverick.events.symtoms.IMaverickSymptom;
import gfads.cin.ufpe.maverick.ltl.core.checker.TransitionChecker;

public class CheckerTestTrue implements TransitionChecker {

	@Override
	public boolean check(String transitionFormula, IMaverickSymptom symptom) {
		return true;
	}

}

package gfads.cin.ufpe.maverick.ltl.core.checker

import gfads.cin.ufpe.maverick.events.IMaverickSymptom
import gfads.cin.ufpe.maverick.events.MaverickSymptom
import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystem
import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystemFactory
import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystemState

class ExampleSpotTransitionChecker implements TransitionChecker {

	@Override
	public boolean check(String transitionFormula, IMaverickSymptom symptom) {
		print "formula: $transitionFormula  Symptom: ${symptom.getContainerName()} "
		println  symptom.getContainerName() ==~ transitionFormula
		return symptom.getContainerName() ==~ transitionFormula
	}

	public static void main(String[] args) {
		String json = "{\"log\":\"{\\\"timeMillis\\\":1489252411395,\\\"thread\\\":\\\"main\\\",\\\"level\\\":\\\"INFO\\\",\\\"loggerName\\\":\\\"gfads.cin.ufpe.maverick.sandbox.App\\\",\\\"message\\\":\\\"QWE\\\",\\\"endOfBatch\\\":false,\\\"loggerFqcn\\\":\\\"org.apache.logging.slf4j.Log4jLogger\\\"}\r\r\",\"container_id\":\"37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78\",\"container_name\":\"/java\",\"source\":\"stdout\"}";
		MaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json);
		//container_name:.java(.)*

		TransitionChecker checker = new ExampleSpotTransitionChecker()
		LabeledTransitionSystem lts = LabeledTransitionSystemFactory.create("G(((client:(.)*) && (.client(.)*)) ->F((client:(.)*) && (.java(.)*)))",checker)
//		LabeledTransitionSystem lts = LabeledTransitionSystemFactory.create("!F(red & X(yellow))",checker)
		LabeledTransitionSystemState state = new LabeledTransitionSystemState("");
		state.setInitialState(lts.getInitialState())
		println lts.next(state, symptom)
	}
}

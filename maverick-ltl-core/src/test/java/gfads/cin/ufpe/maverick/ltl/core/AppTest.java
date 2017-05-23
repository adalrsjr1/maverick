package gfads.cin.ufpe.maverick.ltl.core;

import gfads.cin.ufpe.maverick.events.IMaverickSymptom;
import gfads.cin.ufpe.maverick.events.MaverickSymptom;
import gfads.cin.ufpe.maverick.ltl.core.checker.TransitionChecker;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	
	static class PropertyChecker implements TransitionChecker {

		@Override
		public boolean check(String transitionFormula, IMaverickSymptom symptom) {
			return transitionFormula.equals(symptom.getContainerName());
		}
		
	}
	
	// it checks that yellow never occurs immediately after red.
	static final String PROPERTY_1 = "!F(red & X(yellow))";
	
	// it checks that 'a' is the second action and an eventual 'b' or a global 'c' always happen or
	// 'c' always happen
	static final String PROPERTY_2 = "(Xa & Fb) | Gc";
	
	static final String PROPERTY_3 = "Fa->Gb";

	static final NonViolationEvent nv = NonViolationEvent.getInstance();
	static final ViolationEvent v = ViolationEvent.getInstance();
	
	public void testGetInitialStateProperty3() {
		LabeledTransitionSystem lts = LabeledTransitionSystemFactory.create(PROPERTY_3, new PropertyChecker());
		assertEquals(lts.getInitialState().getStateId(), 2);
	}
	
	public void testViolationEvent() {
		assertTrue(ViolationEvent.getInstance() instanceof ViolationEvent);
	}
	
	public void testNonViolationEvent() {
		assertTrue(NonViolationEvent.getInstance() instanceof NonViolationEvent);
	}
	
	public void testProperty1ViolationRedYellow() {
		LabeledTransitionSystem lts = LabeledTransitionSystemFactory.create(PROPERTY_1, new PropertyChecker());
		LabeledTransitionSystemState state = new LabeledTransitionSystemState("");
		state.setInitialState(lts.getInitialState());
		
		assertEquals(lts.next(state, MaverickSymptom.newMaverickSymptom("", "red", "", "")), nv);
		assertEquals(state.isInAcceptanceState(), true);
		assertEquals(lts.next(state, MaverickSymptom.newMaverickSymptom("", "yellow", "", "")), v);
		assertEquals(state.isInAcceptanceState(), false);
		assertEquals(lts.next(state, MaverickSymptom.newMaverickSymptom("", "yellow", "", "")), v);
		assertEquals(state.isInAcceptanceState(), false);
		assertEquals(lts.next(state, MaverickSymptom.newMaverickSymptom("", "green", "", "")), v);
		assertEquals(state.isInAcceptanceState(), false);
		assertEquals(lts.next(state, MaverickSymptom.newMaverickSymptom("", "red", "", "")), v);
		assertEquals(state.isInAcceptanceState(), false);
	}
	
	public void testProperty1NonViolationStartingWithYellow() {
		LabeledTransitionSystem lts = LabeledTransitionSystemFactory.create(PROPERTY_1, new PropertyChecker());
		LabeledTransitionSystemState state = new LabeledTransitionSystemState("");
		state.setInitialState(lts.getInitialState());
		
		assertEquals(lts.next(state, MaverickSymptom.newMaverickSymptom("", "yellow", "", "")), nv);
		assertEquals(state.isInAcceptanceState(), true);
		assertEquals(lts.next(state, MaverickSymptom.newMaverickSymptom("", "yellow", "", "")), nv);
		assertEquals(state.isInAcceptanceState(), true);
		assertEquals(lts.next(state, MaverickSymptom.newMaverickSymptom("", "green", "", "")), nv);
		assertEquals(state.isInAcceptanceState(), true);
		assertEquals(lts.next(state, MaverickSymptom.newMaverickSymptom("", "red", "", "")), nv);
		assertEquals(state.isInAcceptanceState(), true);
	}
	
	public void testProperty1Violation() {
		LabeledTransitionSystem lts = LabeledTransitionSystemFactory.create(PROPERTY_1, new PropertyChecker());
		LabeledTransitionSystemState state = new LabeledTransitionSystemState("");
		state.setInitialState(lts.getInitialState());
		
		LabeledTransitionSystemEvent event;
		
		event = lts.next(state, MaverickSymptom.newMaverickSymptom("", "yellow", "", ""));
		event = lts.next(state, MaverickSymptom.newMaverickSymptom("", "yellow", "", ""));
		event = lts.next(state, MaverickSymptom.newMaverickSymptom("", "green", "", ""));
		event = lts.next(state, MaverickSymptom.newMaverickSymptom("", "red", "", ""));
		event = lts.next(state, MaverickSymptom.newMaverickSymptom("", "yellow", "", ""));
		
		assertSame(event, v);
	}
	
	public void testProperty1NonViolation() {
		LabeledTransitionSystem lts = LabeledTransitionSystemFactory.create(PROPERTY_1, new PropertyChecker());
		LabeledTransitionSystemState state = new LabeledTransitionSystemState("");
		state.setInitialState(lts.getInitialState());
		
		LabeledTransitionSystemEvent event;
		
		event = lts.next(state, MaverickSymptom.newMaverickSymptom("", "yellow", "", ""));
		event = lts.next(state, MaverickSymptom.newMaverickSymptom("", "yellow", "", ""));
		event = lts.next(state, MaverickSymptom.newMaverickSymptom("", "green", "", ""));
		event = lts.next(state, MaverickSymptom.newMaverickSymptom("", "red", "", ""));
		
		assertSame(event, nv);
	}
	
	public void testProperty1MultipleStates() {
		LabeledTransitionSystem lts = LabeledTransitionSystemFactory.create(PROPERTY_1, new PropertyChecker());
		
		LabeledTransitionSystemState state1 = new LabeledTransitionSystemState("1");
		state1.setInitialState(lts.getInitialState());
		
		LabeledTransitionSystemState state2 = new LabeledTransitionSystemState("2");
		state2.setInitialState(lts.getInitialState());
		
		LabeledTransitionSystemState state3 = new LabeledTransitionSystemState("3");
		state3.setInitialState(lts.getInitialState());
		
		LabeledTransitionSystemEvent event1;
		LabeledTransitionSystemEvent event2;
		LabeledTransitionSystemEvent event3;
		
		event1 = lts.next(state1, MaverickSymptom.newMaverickSymptom("", "yellow", "", ""));
		event2 = lts.next(state2, MaverickSymptom.newMaverickSymptom("", "yellow", "", ""));
		event3 = lts.next(state3, MaverickSymptom.newMaverickSymptom("", "green", "", ""));
		event1 = lts.next(state1, MaverickSymptom.newMaverickSymptom("", "red", "", ""));
		event2 = lts.next(state2, MaverickSymptom.newMaverickSymptom("", "yellow", "", ""));
		event3 = lts.next(state3, MaverickSymptom.newMaverickSymptom("", "red", "", ""));
		event1 = lts.next(state1, MaverickSymptom.newMaverickSymptom("", "yellow", "", ""));
		event2 = lts.next(state2, MaverickSymptom.newMaverickSymptom("", "yellow", "", ""));
		event3 = lts.next(state3, MaverickSymptom.newMaverickSymptom("", "green", "", ""));

		assertSame(event1, v);
		assertSame(event2, nv);
		assertSame(event3, nv);
	}
}

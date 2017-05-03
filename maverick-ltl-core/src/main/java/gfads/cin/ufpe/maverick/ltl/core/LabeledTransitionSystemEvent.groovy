package gfads.cin.ufpe.maverick.ltl.core

import groovy.transform.CompileStatic
import groovy.transform.Memoized
import groovy.transform.ToString

trait LabeledTransitionSystemEvent {
	public String toString() {
		this.getClass().getSimpleName()
	}
}

@CompileStatic
class LabeledTransitionSystemEventFactory {
	@Memoized
	static LabeledTransitionSystemEvent newEvent(boolean result) {
		return !result ? ViolationEvent.getInstance() : NonViolationEvent.getInstance()
	}
}

@CompileStatic
class ViolationEvent implements LabeledTransitionSystemEvent {

	static final ViolationEvent instance = new ViolationEvent();
	
	private ViolationEvent() { }  
		
}

@CompileStatic
@ToString()
class NonViolationEvent implements LabeledTransitionSystemEvent {
	static final NonViolationEvent instance = new NonViolationEvent();
	
	private ViolationEvent() { }
}

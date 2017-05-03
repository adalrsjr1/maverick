package gfads.cin.ufpe.maverick.ltl.core

import java.util.concurrent.TimeUnit

import com.google.common.base.Stopwatch

import jhoafparser.storage.StoredAutomaton
import jhoafparser.storage.StoredState

class LabeledTransitionSystemState {

	private final String correlationIdMessage
	private final Stopwatch watch

	private boolean inAcceptanceState
	private StoredState initialState
	
	StoredState automataState

	public LabeledTransitionSystemState(String correlationIdMessage) {
		this.correlationIdMessage = correlationIdMessage
		watch = Stopwatch.createUnstarted()
	}
	
	public void reset() {
		setInitialState(getInitialState())
	}
	
	public boolean isInAcceptanceState() {
		return automataState.getAccSignature() != null
	}

	public void setInitialState(StoredState state) {
		initialState = state
		setAutomataState(initialState);
	}
	
	public StoredState getInitialState() {
		return initialState
	}
		
	public void setAutomataState(StoredState state) {
		watch.reset()
		automataState = state
		watch.start()
	}
	
	public long getElapsedTimeInCurrentState(TimeUnit timeUnit) {
		return watch.elapsed(timeUnit)
	}
}

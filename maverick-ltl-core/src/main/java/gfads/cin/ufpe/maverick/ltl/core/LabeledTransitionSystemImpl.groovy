package gfads.cin.ufpe.maverick.ltl.core

import java.util.concurrent.locks.ReentrantReadWriteLock

import gfads.cin.ufpe.maverick.events.MaverickSymptom
import groovy.transform.CompileStatic
import jhoafparser.ast.BooleanExpression
import jhoafparser.storage.StoredAutomaton
import jhoafparser.storage.StoredState

@CompileStatic
@Singleton
class LabeledTransitionSystemImpl implements LabeledTransitionSystem {

	// this lock must be fair to avoid waiting forever to update the automaton 
	private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock(true)

	private StoredAutomaton storedAutomaton
	private TransitionChecker checker

	@Override
	public void init(StoredAutomaton storedAutomaton, TransitionChecker cheker) {
		try{
			LOCK.writeLock().lock()
			this.storedAutomaton = storedAutomaton
			this.checker = cheker
		}
		finally {
			LOCK.writeLock().unlock()
		}
	}

	@Override
	public boolean next(LabeledTransitionSystemState state, MaverickSymptom symptom) {
		
		try {
			LOCK.readLock().lock()
			return transition(state, symptom)
		}
		finally {
			LOCK.readLock().unlock()
		}
	}
	
	@Override
	public StoredState getInitialState() {
		try {
			LOCK.readLock().lock()
			return storedAutomaton.getStoredState(0)
		}
		finally {
			LOCK.readLock().unlock()
		}
		
	}

	private boolean evaluate(BooleanExpression expression, MaverickSymptom symptom) {
		BooleanExpression root = expression
		boolean leftResult = null, rightResult = null, result = true
		if(expression.left != null)
			leftResult = evaluate(expression.left, symptom)

		if(expression.right != null)
			rightResult = evaluate(expression.right, symptom)

		if(expression.left == null && expression.right == null && root != null) {
			if(root.type == BooleanExpression.Type.EXP_ATOM) {
				int nRoot = Integer.parseInt(root.toString())

				String token = storedAutomaton.storedHeader.APs[nRoot]
				return checker.check(token, symptom)
			}
			if(BooleanExpression.Type.EXP_TRUE == expression.type) {
				return true
			}
			if(BooleanExpression.Type.EXP_FALSE == expression.type) {
				return false
			}
		}

		if(BooleanExpression.Type.EXP_NOT == expression.type) {
			result = leftResult == null ? !rightResult : !leftResult
		}
		else if(BooleanExpression.Type.EXP_OR == expression.type) {
			result = leftResult || rightResult
		}
		else if(BooleanExpression.Type.EXP_AND == expression.type) {
			result = leftResult && rightResult
		}
		return result
	}

	private boolean transition(LabeledTransitionSystemState state, MaverickSymptom symptom) {
		StoredState currentState = state.getAutomataState()
		def transitions = storedAutomaton.getEdgesWithLabel(currentState.getStateId())

		for(t in transitions) {
			boolean result = evaluate(t.labelExpr, symptom)
			if(result) {
				int automataStateId = t.getConjSuccessors().first()
				StoredState automataState = storedAutomaton.getStoredState(automataStateId)
				state.setAutomataState(automataState)
				return state.isInAcceptanceState()
			}
		}
		return state.isInAcceptanceState()
	}
}

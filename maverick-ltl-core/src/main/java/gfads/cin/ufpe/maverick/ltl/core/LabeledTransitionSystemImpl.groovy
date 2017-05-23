package gfads.cin.ufpe.maverick.ltl.core

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantReadWriteLock

import gfads.cin.ufpe.maverick.events.IMaverickSymptom
import gfads.cin.ufpe.maverick.ltl.core.checker.TransitionChecker
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
	public void init(StoredAutomaton storedAutomaton, TransitionChecker checker) {
		try{
			LOCK.writeLock().lock()
			this.storedAutomaton = storedAutomaton
			this.checker = checker
		}
		finally {
			LOCK.writeLock().unlock()
		}
	}

	@Override
	public LabeledTransitionSystemEvent next(LabeledTransitionSystemState state, IMaverickSymptom symptom) {
		
		try {
			LOCK.readLock().lock()
			return LabeledTransitionSystemEventFactory.newEvent(transition(state, symptom))
		}
		finally {
			LOCK.readLock().unlock()
		}
	}
	
	@Override
	public StoredState getState(int label) {
		try {
			LOCK.readLock().lock()
			return storedAutomaton.getStoredState(label)
		}
		finally {
			LOCK.readLock().unlock()
		}
	}
	
	@Override
	public StoredState getInitialState() {
		Integer label = 0;
		try {
			LOCK.readLock().lock()
			label = (Integer) storedAutomaton.getStoredHeader().getStartStates().flatten().first()
			getState(label)
		}
		finally {
			LOCK.readLock().unlock()
		}
		
	}
	
	@Override
	public Iterable getStates() {
		storedAutomaton.getStoredStates()	
	}
	
	@Override
	public Iterable getTransitions(StoredState state) {
		storedAutomaton.getEdgesWithLabel(state.getStateId())
	}

	@Override
	public String getTransitionLabel(BooleanExpression booleanExpression) {
		Map transitionLabels = [:]
		transverseBooleanExpression(booleanExpression, storedAutomaton, transitionLabels)
		String str = booleanExpression.toString()
		
		StringBuilder sb = new StringBuilder()
		for(int i = 0; i < str.length(); i++) {
			String idx = "${str.charAt(i)}"
			if(transitionLabels.containsKey(idx)) {
				sb.append(transitionLabels[idx])
			}
			else {
				sb.append(idx)
			}
		}
		
		return sb.toString()
	}
	
	private void transverseBooleanExpression(BooleanExpression exp, StoredAutomaton lts, Map map) {
		BooleanExpression root = exp
		if(root.left)
			transverseBooleanExpression(root.left, lts, map)
		if(root.right)
			transverseBooleanExpression(root.right, lts, map)
		if(!root.left && !root.right) {
			if(!root.toString().equals("t")) {
				int idx = Integer.parseInt(root.toString())
				map[root.toString()] = storedAutomaton.storedHeader.APs[idx]
			}
		}
	}

	private boolean evaluate(BooleanExpression expression, IMaverickSymptom symptom) {
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

	private boolean transition(LabeledTransitionSystemState state, IMaverickSymptom symptom) {
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

package gfads.cin.ufpe.maverick.ltl.core.model

import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystem
import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystemFactory
import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystemState
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import jhoafparser.storage.StoredEdgeWithLabel
import jhoafparser.storage.StoredState

class LabeledTransitionSystemJsonWrapper {

	private LabeledTransitionSystem lts
	
	LabeledTransitionSystemJsonWrapper(LabeledTransitionSystem lts) {
		this.lts = lts
	}
	
	Map ltsMap() {
		def nodes = nodes()
		
		
		def transitions = nodes.inject([]) { result, node ->
			result << transitions(node.getId())
			result
		}
		
		[nodes: nodes,
		 edges: transitions]
	}
	
	String ltsJson() {
		JsonOutput.toJson(ltsMap())
	}
	
	String ltsJsonPretty() {
		new JsonBuilder(ltsMap()).toPrettyString()
	}
	
	List nodes() {
		List list = []
		Iterable iterableStates = lts.getStates()
		iterableStates.each { StoredState state ->
			list << new LabeledTransitionSystemNodeModel(state)
		}
		
		return list
	}
	
	private StoredState getState(int id) {
		lts.getStates().find {
			it.getStateId() == id
		}
	}
	
	List transitions(int state) {
		List list = []
		Iterable iterableTransitions = lts.getTransitions(getState(state))
		StringBuilder sb = new StringBuilder()
		iterableTransitions.each { StoredEdgeWithLabel transition ->
			list << new LabeledTransitionSystemTransitionModel(transition, lts, state)
		}
		
		return list
	}
	
	public static void main(String[] args) {
		String property = "[](Q -> (!(S & (!A) & ()(!A U (T & !A))) U (A | P) | [](!(S & ()<>T))))"
		
		LabeledTransitionSystem lts = LabeledTransitionSystemFactory.create(property, null);
		LabeledTransitionSystemState state = new LabeledTransitionSystemState("");
		
		LabeledTransitionSystemJsonWrapper wrapper = new LabeledTransitionSystemJsonWrapper(lts);
		
		printf wrapper.ltsJson()
		
	}
}

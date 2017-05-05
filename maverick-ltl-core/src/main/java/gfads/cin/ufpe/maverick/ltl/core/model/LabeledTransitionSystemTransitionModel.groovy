package gfads.cin.ufpe.maverick.ltl.core.model

import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystem
import groovy.json.JsonBuilder
import groovy.transform.ToString
import jhoafparser.storage.StoredEdgeWithLabel

@ToString
class LabeledTransitionSystemTransitionModel {

	int source
	final int target
	final String caption

	public LabeledTransitionSystemTransitionModel(StoredEdgeWithLabel transition, LabeledTransitionSystem lts, int source) {
		target = transition.getConjSuccessors().first()
		caption = lts.getTransitionLabel(transition.labelExpr)
		this.source = source
	}
	
	public String toJson() {
		new JsonBuilder(this).toString()
	}

}

package gfads.cin.ufpe.maverick.ltl.specpatterns

import groovy.util.logging.Slf4j

@Slf4j
class And {
	ExpressionBuilder expressionBuilder
	List<ExpressionVariable> vars
	ExpressionPattern pattern
	TemporalOccurrence occurrence
	
	And(ExpressionBuilder expressionBuilder, TemporalOccurrence occurrence, ExpressionPattern pattern, List<ExpressionVariable> vars) {
		this.expressionBuilder = expressionBuilder
		this.occurrence = occurrence
		this.pattern = pattern
		this.vars = vars
	}
	
	ExpressionBuilder and(ExpressionVariable r) {
		expressionBuilder.instance = new PropertyInstance(occurrence, pattern, vars << r)
		return expressionBuilder
	}
}

@Slf4j
class Until {
	ExpressionBuilder expressionBuilder
	List<ExpressionVariable> vars
	ExpressionPattern pattern
	TemporalOccurrence occurrence
	
	Until(ExpressionBuilder expressionBuilder, TemporalOccurrence occurrence, ExpressionPattern pattern, List<ExpressionVariable> vars) {
		this.expressionBuilder = expressionBuilder
		this.occurrence = occurrence
		this.pattern = pattern
		this.vars = vars
	}
	
	ExpressionBuilder until(ExpressionVariable r) {
		expressionBuilder.instance = new PropertyInstance(occurrence, pattern, vars << r)
		return expressionBuilder
	}
	
}

@Slf4j
class ExpressionBuilder {
	List<ExpressionVariable> vars = []
	ExpressionPattern pattern 
	
	PropertyInstance instance
	
	ExpressionBuilder(ExpressionPattern pattern, ExpressionVariable p) {
		this.vars << p
		this.pattern = pattern
	}
	
	ExpressionBuilder(ExpressionPattern pattern, List<ExpressionVariable> ps) {
		vars.addAll(ps)
		this.pattern = pattern
	}
	
	ExpressionBuilder globally() {
		instance = new PropertyInstance(TemporalOccurrence.GLOBALLY, pattern, vars)
		return this
	}
	
	ExpressionBuilder before(ExpressionVariable r) {
		instance = new PropertyInstance(TemporalOccurrence.BEFORE_R, pattern, vars << r)
		return this
	}
	
	ExpressionBuilder justAfter(ExpressionVariable q) {
		instance = new PropertyInstance(TemporalOccurrence.AFTER_Q, pattern, vars << q)
		return this
	}
	
	Until after(ExpressionVariable q) {
		return new Until(this, TemporalOccurrence.AFTER_Q_UNTIL_R, pattern, vars << q)
	}
	
	And between(ExpressionVariable q) {
		return new And(this, TemporalOccurrence.BETWEEN_Q_AND_R, pattern, vars << q)
	}
	
	PropertyInstance build() {
		instance.build()
		return instance
	}
}
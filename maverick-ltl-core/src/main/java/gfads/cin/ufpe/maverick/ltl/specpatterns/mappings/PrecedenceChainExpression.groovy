package gfads.cin.ufpe.maverick.ltl.specpatterns.mappings

import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionBuilder
import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionPattern;
import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionVariable;

import groovy.util.logging.Slf4j

@Slf4j
class PrecedenceChainExpression {
	ExpressionVariable s
	ExpressionVariable t
	
	PrecedenceChainExpression(ExpressionVariable s) {
		this.s = s
	}
	
	PrecedenceChainExpression(ExpressionVariable s, ExpressionVariable t) {
		this.s = s
		this.t = t
	}
	
	// s, t preceds p
	ExpressionBuilder precedes(ExpressionVariable p) {
		new ExpressionBuilder(ExpressionPattern.PRECEDENCE_CHAIN_ONE, [s,t,p])
	}
	
	// s preceds p, t
	ExpressionBuilder precedes(ExpressionVariable p, ExpressionVariable t) {
		new ExpressionBuilder(ExpressionPattern.PRECEDENCE_CHAIN_TWO, [p,s,t])
	}
}

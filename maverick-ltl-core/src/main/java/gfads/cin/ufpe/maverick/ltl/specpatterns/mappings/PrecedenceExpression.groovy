package gfads.cin.ufpe.maverick.ltl.specpatterns.mappings

import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionBuilder
import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionPattern;
import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionVariable;

import groovy.util.logging.Slf4j

@Slf4j
class PrecedenceExpression {
	ExpressionVariable s
	
	PrecedenceExpression(ExpressionVariable s) {
		this.s = s
	}
	
	ExpressionBuilder precedes(ExpressionVariable p) {
		new ExpressionBuilder(ExpressionPattern.PRECEDENCE, [s,p])
	}
}

package gfads.cin.ufpe.maverick.ltl.specpatterns.mappings

import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionBuilder
import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionPattern;
import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionVariable;

import groovy.util.logging.Slf4j

@Slf4j
class ResponseExpression {
	ExpressionVariable s
	
	ResponseExpression(ExpressionVariable s) {
		this.s = s
	}
	
	ExpressionBuilder respondsTo(ExpressionVariable p) {
		new ExpressionBuilder(ExpressionPattern.RESPONSE, [s,p])
	}
}

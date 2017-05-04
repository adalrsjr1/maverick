package gfads.cin.ufpe.maverick.ltl.specpatterns.mappings

import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionBuilder
import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionPattern;
import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionVariable;

import groovy.util.logging.Slf4j

@Slf4j
class BoundedExpression {
	ExpressionVariable p
	
	BoundedExpression(ExpressionVariable p) {
		this.p = p
	}
	
	ExpressionBuilder occurs(int nTimes) {
		if(nTimes == 0) {
			throw new RuntimeException("Number of occurrence must be greater then 0")
		}
		List vars = []
		for(i in 1..nTimes) {
			vars << p
		}
		new ExpressionBuilder(ExpressionPattern.BOUNDED_EXISTENCE, vars)
	}
}

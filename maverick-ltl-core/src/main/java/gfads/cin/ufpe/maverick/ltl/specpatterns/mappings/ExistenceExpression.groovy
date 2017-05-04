package gfads.cin.ufpe.maverick.ltl.specpatterns.mappings

import groovy.util.logging.Slf4j

import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionBuilder
import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionPattern
import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionVariable

@Slf4j
class ExistenceExpression {
	ExpressionVariable p
	
	ExistenceExpression(ExpressionVariable p) {
		this.p = p
	}
	
	ExpressionBuilder becomesTrue() {
		new ExpressionBuilder(ExpressionPattern.EXISTENCE, p)
	}
}

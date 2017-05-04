package gfads.cin.ufpe.maverick.ltl.specpatterns.mappings

import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionBuilder;
import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionPattern;
import gfads.cin.ufpe.maverick.ltl.specpatterns.ExpressionVariable;

import groovy.util.logging.Slf4j

@Slf4j
class AbsenceExpression {
	ExpressionVariable p
	
	AbsenceExpression(ExpressionVariable p) {
		this.p = p
	}
	
	ExpressionBuilder isFalse() {
		new ExpressionBuilder(ExpressionPattern.ABSENCE, p)
	}
}

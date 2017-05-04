package gfads.cin.ufpe.maverick.ltl.specpatterns

import groovy.util.logging.Slf4j

@Slf4j
class ExpressionVariable {
	String var
	
	ExpressionVariable(String var) {
		this.var = var
	}
	
	String toString() {
		var
	}
}

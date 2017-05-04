package gfads.cin.ufpe.maverick.ltl.specpatterns

import java.util.Map;

enum ExpressionPattern {
	ABSENCE {
		String buildTemporalProperty(TemporalOccurrence temporalProperty) {
			switch(temporalProperty) {
				case TemporalOccurrence.GLOBALLY : return "[](!#1)"
				case TemporalOccurrence.BEFORE_R : return "<>#2 -> (!#1 U #2)"
				case TemporalOccurrence.AFTER_Q : return "[](#2 -> [](!#1))"
				case TemporalOccurrence.BETWEEN_Q_AND_R : return "[]((#2 & !#3 & <>#3) -> (!#1 U #3))"
				case TemporalOccurrence.AFTER_Q_UNTIL_R : return "[](#2 & !#3 -> (!#1 W #3))"
				default: return ""
			}
		}
	}, 

	EXISTENCE {
		String buildTemporalProperty(TemporalOccurrence temporalProperty) {
			switch(temporalProperty) {
				case TemporalOccurrence.GLOBALLY : return "<>(#1)"
				case TemporalOccurrence.BEFORE_R : return "!#2 W (#1 & !#2)"
				case TemporalOccurrence.AFTER_Q : return "[](!#2) | <>(#2 & <>#1))"
				case TemporalOccurrence.BETWEEN_Q_AND_R : return "[](#2 & !#3 -> (!#3 W (#1 & !#3)))"
				case TemporalOccurrence.AFTER_Q_UNTIL_R : return "[](#2 & !#3 -> (!#3 U (#1 & !#3)))"
				default: return ""
			}
		}
	},

	BOUNDED_EXISTENCE {
		String buildTemporalProperty(TemporalOccurrence temporalProperty) { }
		
		String buildTemporalProperty(TemporalOccurrence temporalProperty, int nOccurrence) {
			switch(temporalProperty) {
				case TemporalOccurrence.GLOBALLY : return this.globally(nOccurrence)
				
				case TemporalOccurrence.BEFORE_R :  String aux = beforeR(nOccurrence-1) // removing the var R
													return "<>#2 -> ${aux}"
													
				case TemporalOccurrence.AFTER_Q :   String aux = afterQ(nOccurrence-1) // removing the var Q
													return "<>#2 -> (!#2 U (#2 & ${aux}))"
													
				case TemporalOccurrence.BETWEEN_Q_AND_R :   String aux = betweenQandR(nOccurrence-2) // removing the vars Q and R
															return "[]((#2 & <>#3) -> ${aux})"
															
				case TemporalOccurrence.AFTER_Q_UNTIL_R : 	String aux = afterQuntilR(nOccurrence-2) // removing the vars Q and R
															return "[](#2 -> ${aux})"
				default: return ""
			}
		}
		
		private String globally(int n) {
			if (n == 0) {
				return "[]!#1"
			}
			return "(!#1 W (#1 W ${globally(n-1)}))" 
		}
		
		private String beforeR(int n) {
			if (n == 0) { 
				return "(!#1 U #2)"
			}
			return "((!#1 & !#2) U (#2 | ((#1 & !#2) U (#2 | ${beforeR(n-1)}))))"
		}
		
		private String afterQ(int n) {
			if(n == 0) {
				return "[]!#1"
			}
			return "(!#1 W (#1 W ${afterQ(n-1)}))"
		}
		
		private String betweenQandR(int n) {
			if(n == 0) {
				return "(!#1 U #3)"
			}
			return "((!#1 & !#3) U (#3 | ((#1 & !#3) U (#3 | ${betweenQandR(n-1)}))))"
		}
		
		private String afterQuntilR(int n) {
			if(n == 0) {
				return "(!#1 W #3) | []#1"
			}
			return "((!#1 & !#3) U (#3 | ((#1 & !#3) U (#3 | ${afterQuntilR(n-1)}))))"
		}
	},

	UNIVERSALITY {
		String buildTemporalProperty(TemporalOccurrence temporalProperty) {
			switch(temporalProperty) {
				case TemporalOccurrence.GLOBALLY : return "[](#1)"
				case TemporalOccurrence.BEFORE_R : return "<>#2 -> (#1 U #2)"
				case TemporalOccurrence.AFTER_Q : return "[](#2 -> [](#1))"
				case TemporalOccurrence.BETWEEN_Q_AND_R : return "[]((#2 & !#3 & <>#3) -> (#1 U #3))"
				case TemporalOccurrence.AFTER_Q_UNTIL_R : return "[](#2 & !#3 -> (#1 W #3))"
				default: return ""
			}
		}
	},

	RESPONSE {
		String buildTemporalProperty(TemporalOccurrence temporalProperty) {
			switch(temporalProperty) {
				case TemporalOccurrence.GLOBALLY : return "[](#2 -> <>#1)"
				case TemporalOccurrence.BEFORE_R : return "<>#3 -> (#2 -> (!#3 U (#1 & !#3))) U #3"
				case TemporalOccurrence.AFTER_Q : return "[](#3 -> [](#2 -> <>#1))"
				case TemporalOccurrence.BETWEEN_Q_AND_R : return "[]((#3 & !#4 & <>#4) -> (#2 -> (!#4 U (#1 & !#4))) U #4)"
				case TemporalOccurrence.AFTER_Q_UNTIL_R : return "[](#3 & !#4 -> ((#2 -> (!#4 U (#1 & !#4))) W #4))"
				default: return ""
			}
		}
	},

	PRECEDENCE {
		String buildTemporalProperty(TemporalOccurrence temporalProperty) {
			switch(temporalProperty) {
				case TemporalOccurrence.GLOBALLY : return "!#2 W #1"
				case TemporalOccurrence.BEFORE_R : return "<>#3 -> (!#2 U (#1 | #3))"
				case TemporalOccurrence.AFTER_Q : return "[]!#3 | <>(#3 & (!#2 W #1))"
				case TemporalOccurrence.BETWEEN_Q_AND_R : return "[]((#3 & !#4 & <>#4) -> (!#2 U (#1 | #4)))"
				case TemporalOccurrence.AFTER_Q_UNTIL_R : return "[](#3 & !#4 -> (!#2 W (#1 | #4)))"
				default: return ""
			}
		}
	},

	PRECEDENCE_CHAIN_ONE {
		String buildTemporalProperty(TemporalOccurrence temporalProperty) {
			switch(temporalProperty) {
				case TemporalOccurrence.GLOBALLY : return "<>#3 -> (!#3 U (#1 & !#3 & ()(!#3 U #2)))"
				case TemporalOccurrence.BEFORE_R : return "<>#4 -> (!#3 U (#4 | (#1 & !#3 & ()(!#3 U #2))))"
				case TemporalOccurrence.AFTER_Q : return "([]!#4) | (!#4 U (#4 & <>#3 -> (!#3 U (#1 & !#3 & ()(!#3 U #2)))))"
				case TemporalOccurrence.BETWEEN_Q_AND_R : return "[]((#4 & <>#5) -> (!#3 U (#5 | (#1 & !#3 & ()(!#3 U #2)))))"
				case TemporalOccurrence.AFTER_Q_UNTIL_R : return "[](#4 -> (<>#3 -> (!#3 U (#5 | (#1 & !#3 & ()(!#3 U #2))))))"
				default: return ""
			}
		}
	},

	PRECEDENCE_CHAIN_TWO {
		String buildTemporalProperty(TemporalOccurrence temporalProperty) {
			switch(temporalProperty) {
				case TemporalOccurrence.GLOBALLY : return "(<>(#1 & ()<>#3)) -> (((!#1) U #2))"
				case TemporalOccurrence.BEFORE_R : return "<>#4 -> ((!(#1 & (!#4) & ()(!#4 U (#3 & !#4)))) U (#4 | #2))"
				case TemporalOccurrence.AFTER_Q : return "([]!#4) | ((!#4) U (#4 & ((<>(#1 & ()<>#3)) -> ((!#1) U #2))))"
				case TemporalOccurrence.BETWEEN_Q_AND_R : return "[]((#4 & <>#5) -> ((!(#1 & (!#5) & ()(!#5 U (#3 & !#5)))) U (#5 | #2)))"
				case TemporalOccurrence.AFTER_Q_UNTIL_R : return "[](#4 -> (!(#1 & (!#5) & ()(!#5 U (#3 & !#5))) U (#5 | #2) | [](!(#1 & ()<>#3))))"
				default: return ""
			}
		}
	},

	RESPONSE_CHAIN_ONE {
		String buildTemporalProperty(TemporalOccurrence temporalProperty) {
			switch(temporalProperty) {
				case TemporalOccurrence.GLOBALLY : return "[] (#3 -> <>(#1 & ()<>#2))"
				case TemporalOccurrence.BEFORE_R : return "<>#4 -> (#3 -> (!#4 U (#1 & !#4 & ()(!#4 U #2)))) U #4"
				case TemporalOccurrence.AFTER_Q : return "[] (#4 -> [] (#3 -> (#1 & ()<> #2)))"
				case TemporalOccurrence.BETWEEN_Q_AND_R : return "[] ((#4 & <>#5) -> (#3 -> (!#5 U (#1 & !#5 & ()(!#5 U #2)))) U #5)"
				case TemporalOccurrence.AFTER_Q_UNTIL_R : return "[] (#4 -> (#3 -> (!#5 U (#1 & !#5 & ()(!#5 U #2)))) U (#5 | [] (#3 -> (#1 & ()<> #2))))"
				default: return ""
			}
		}
	},

	RESPONSE_CHAIN_TWO {
		String buildTemporalProperty(TemporalOccurrence temporalProperty) {
			switch(temporalProperty) {
				case TemporalOccurrence.GLOBALLY : return "[] (#2 & ()<> #3 -> ()(<>(#3 & <> #1)))"
				case TemporalOccurrence.BEFORE_R : return "<>#4 -> (#2 & ()(!#4 U #3) -> ()(!#4 U (#3 & <> #1))) U #4"
				case TemporalOccurrence.AFTER_Q : return "[] (#4 -> [] (#2 & ()<> #3 -> ()(!#3 U (#3 & <> #1))))"
				case TemporalOccurrence.BETWEEN_Q_AND_R : return "[] ((#4 & <>#5) -> (#2 & ()(!#5 U #3) -> ()(!#5 U (#3 & <> #1))) U #5)"
				case TemporalOccurrence.AFTER_Q_UNTIL_R : return "[] (#4 -> (#2 & ()(!#5 U #3) -> ()(!#5 U (#3 & <> #1))) U (#5 | [] (#2 & ()(!#5 U #3) -> ()(!#5 U (#3 & <> #1)))))"
				default: return ""
			}
		}
	},

	CONSTRAINED_CHAIN {
		String buildTemporalProperty(TemporalOccurrence temporalProperty) { 
			switch(temporalProperty) {
				case TemporalOccurrence.GLOBALLY : return "[] (#4 -> <>(#1 & !#3 & ()(!#3 U #2)))"
				case TemporalOccurrence.BEFORE_R : return "<>#5 -> (#4 -> (!#5 U (#1 & !#5 & !#3 & ()((!#5 & !#3) U #2)))) U #5"
				case TemporalOccurrence.AFTER_Q : return "[] (#5 -> [] (#4 -> (#1 & !#3 & ()(!#3 U #2))))"
				case TemporalOccurrence.BETWEEN_Q_AND_R : return "[] ((#5 & <>#6) -> (#4 -> (!#6 U (#1 & !#6 & !#3 & ()((!#6 & !#3) U #2)))) U #6)"
				case TemporalOccurrence.AFTER_Q_UNTIL_R : return "[] (#5 -> (#4 -> (!#6 U (#1 & !#6 & !#3 & ()((!#6 & !#3) U #2)))) U (#6 | [] (#4 -> (#1 & !#3 & ()(!#3 U #2)))))"
				default: return ""
			}
		}
	}
	
	abstract String buildTemporalProperty(TemporalOccurrence temporalProperty)
}

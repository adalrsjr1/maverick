package gfads.cin.ufpe.maverick.ltl.specpatterns;

import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystemEventFactory
import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystemFactory
import groovy.util.GroovyTestCase

class TestSpecpattern extends GroovyTestCase {

	static final ExpressionVariable p = new ExpressionVariable("P")
	static final ExpressionVariable q = new ExpressionVariable("Q")
	static final ExpressionVariable a = new ExpressionVariable("A")
	static final ExpressionVariable s = new ExpressionVariable("S")
	static final ExpressionVariable t = new ExpressionVariable("T")
	static final ExpressionVariable z = new ExpressionVariable("Z")

	static final PropertyPattern pp = Property.create()

	// http://patterns.projects.cs.ksu.edu/documentation/patterns/ltl.shtml
	// spot parser recognize A (capital A) as an operator

	void testAbsencePisFalseGlobally() {
		String ltl = pp.absence(p)
				.isFalse()
				.globally()
				.build()
				.toString()

		assert ltl == "[](!P)"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testAbsencePisFalseBeforeA() {
		String ltl = pp.absence(p)
				.isFalse()
				.before(a)
				.build()
				.toString()
		assert ltl == "<>A -> (!P U A)" 
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testAbsencePisFalseAfterQ() {
		String ltl = pp.absence(p)
				.isFalse()
				.justAfter(q)
				.build()
				.toString()
		assert ltl == "[](Q -> [](!P))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testAbsencePisFalseBetweenQandA() {
		String ltl = pp.absence(p)
				.isFalse()
				.between(q)
				.and(a)
				.build()
				.toString()
		assert ltl == "[]((Q & !A & <>A) -> (!P U A))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testAbsencePisFalseStarAfterQuntilA() {
		String ltl = pp.absence(p)
				.isFalse()
				.after(q)
				.until(a)
				.build()
				.toString()
		assert ltl == "[](Q & !A -> (!P W A))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testExistencePbecomesTrueGlobally() {
		String ltl =  pp.existence(p).becomesTrue().globally().build().toString()
		assert ltl == "<>(P)"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testExistencePbecomesTrueStarBeforeA() {
		String ltl =  pp.existence(p).becomesTrue().before(a).build().toString()
		assert ltl =="!A W (P & !A)"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testExistencePbecomesTrueAfterQ() {
		String ltl =  pp.existence(p).becomesTrue().after(q).until(a).build().toString()
		assert ltl == "[](Q & !A -> (!A U (P & !A)))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testExistencePbecomesTrueStarBetweenQandA() {
		String ltl =  pp.existence(p).becomesTrue().between(q).and(a).build().toString()
		assert ltl == "[](Q & !A -> (!A W (P & !A)))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testExistencePbecomesTrueStartAfterQuntilA() {
		String ltl =  pp.existence(p).becomesTrue().after(q).until(a).build().toString()
		assert ltl == "[](Q & !A -> (!A U (P & !A)))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testBoundedExistenceAtMost2Globally() {
		String ltl = pp.boundedExistence(p)
				.occurs(2)
				.globally()
				.build()
				.toString()

		assert ltl == "(!P W (P W (!P W (P W []!P))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testBoundedExistenceAtMost2BeforeA() {
		String ltl = pp.boundedExistence(p).occurs(2).before(a).build().toString()
		assert ltl == "<>A -> ((!P & !A) U (A | ((P & !A) U (A | ((!P & !A) U (A | ((P & !A) U (A | (!P U A)))))))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testBoundedExistenceAtMost2AfterQ() {
		String ltl = pp.boundedExistence(p).occurs(2).justAfter(q).build().toString()
		assert ltl == "<>Q -> (!Q U (Q & (!P W (P W (!P W (P W []!P))))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testBoundedExistenceAtMost2BetweenQandA() {
		String ltl = pp.boundedExistence(p).occurs(2).between(q).and(a).build().toString()
		assert ltl == "[]((Q & <>A) -> ((!P & !A) U (A | ((P & !A) U (A | ((!P & !A) U (A | ((P & !A) U (A | (!P U A))))))))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testBoundedExistenceAtMost2AfterQuntilA() {
		String ltl = pp.boundedExistence(p).occurs(2).after(q).until(a).build().toString()
		assert ltl == "[](Q -> ((!P & !A) U (A | ((P & !A) U (A | ((!P & !A) U (A | ((P & !A) U (A | (!P W A) | []P)))))))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testUniversalityPisTrueGlobally() {
		String ltl = pp.universality(p).isTrue().globally().build().toString()
		assert ltl == "[](P)"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testUniversalityPisTrueBeforeA() {
		String ltl = pp.universality(p).isTrue().before(a).build().toString()
		assert ltl == "<>A -> (P U A)"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testUniversalityPisTrueAfterQy() {
		String ltl = pp.universality(p).isTrue().justAfter(q).build().toString()
		assert ltl == "[](Q -> [](P))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testUniversalityPisTrueBetweenQandA() {
		String ltl = pp.universality(p).isTrue().between(q).and(a).build().toString()
		assert ltl == "[]((Q & !A & <>A) -> (P U A))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testUniversalityPisTrueAfterQuntilA() {
		String ltl = pp.universality(p).isTrue().after(q).until(a).build().toString()
		assert ltl == "[](Q & !A -> (P W A))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testSprecedesPGlobally() {
		String ltl = pp.precedence(s).precedes(p).globally().build().toString()
		assert ltl == "!P W S"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testSprecedesPBeforeA() {
		String ltl = pp.precedence(s).precedes(p).before(a).build().toString()
		assert ltl == "<>A -> (!P U (S | A))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testSprecedesPAfterQ() {
		String ltl = pp.precedence(s).precedes(p).justAfter(q).build().toString()
		assert ltl == "[]!Q | <>(Q & (!P W S))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testSprecedesPBetweenQandA() {
		String ltl = pp.precedence(s).precedes(p).between(q).and(a).build().toString()
		assert ltl == "[]((Q & !A & <>A) -> (!P U (S | A)))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testSprecedesPAfterQuntilA() {
		String ltl = pp.precedence(s).precedes(p).after(q).until(a).build().toString()
		assert ltl == "[](Q & !A -> (!P W (S | A)))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testSrespondsToPGlobally() {
		String ltl = pp.response(s).respondsTo(p).globally().build().toString()
		assert ltl == "[](P -> <>S)"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testSrespondsToPBeforeA() {
		String ltl = pp.response(s).respondsTo(p).before(a).build().toString()
		assert ltl == "<>A -> (P -> (!A U (S & !A))) U A"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testSrespondsToPAfterQ() {
		String ltl = pp.response(s).respondsTo(p).justAfter(q).build().toString()
		assert ltl == "[](Q -> [](P -> <>S))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testSrespondsToPBetweenQandA() {
		String ltl = pp.response(s).respondsTo(p).between(q).and(a).build().toString()
		assert ltl == "[]((Q & !A & <>A) -> (P -> (!A U (S & !A))) U A)"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testSrespondsToPAfterQuntilA() {
		String ltl = pp.response(s).respondsTo(p).after(q).until(a).build().toString()
		assert ltl == "[](Q & !A -> ((P -> (!A U (S & !A))) W A))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TprecedesPGlobally() {
		String ltl = pp.precedenceChain(s,t).precedes(p).globally().build().toString()
		assert ltl == "<>P -> (!P U (S & !P & ()(!P U T)))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TprecedesPBeforeA() {
		String ltl = pp.precedenceChain(s,t).precedes(p).before(a).build().toString()
		assert ltl == "<>A -> (!P U (A | (S & !P & ()(!P U T))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TprecedesPAfterQ() {
		String ltl = pp.precedenceChain(s,t).precedes(p).justAfter(q).build().toString()
		assert ltl == "([]!Q) | (!Q U (Q & <>P -> (!P U (S & !P & ()(!P U T)))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TprecedesPBetweenQandA() {
		String ltl = pp.precedenceChain(s,t).precedes(p).between(q).and(a).build().toString()
		assert ltl == "[]((Q & <>A) -> (!P U (A | (S & !P & ()(!P U T)))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true

	}

	void testS_TprecedesPAfterQuntilA() {
		String ltl = pp.precedenceChain(s,t).precedes(p).after(q).until(a).build().toString()
		assert ltl == "[](Q -> (<>P -> (!P U (A | (S & !P & ()(!P U T))))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testPprecedesS_TGlobally() {
		String ltl = pp.precedenceChain(p).precedes(s,t).globally().build().toString()
		assert ltl == "(<>(S & ()<>T)) -> (((!S) U P))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testPprecedesS_TBeforeA() {
		String ltl = pp.precedenceChain(p).precedes(s,t).before(a).build().toString() 
		assert ltl== "<>A -> ((!(S & (!A) & ()(!A U (T & !A)))) U (A | P))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testPprecedesS_TAfterQ() {
		String ltl = pp.precedenceChain(p).precedes(s,t).justAfter(q).build().toString()
		assert ltl == "([]!Q) | ((!Q) U (Q & ((<>(S & ()<>T)) -> ((!S) U P))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testPprecedesS_TBetweenQandA() {
		String ltl = pp.precedenceChain(p).precedes(s,t).between(q).and(a).build().toString()
		assert ltl == "[]((Q & <>A) -> ((!(S & (!A) & ()(!A U (T & !A)))) U (A | P)))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testPprecedesS_TAfterQuntilA() {
		String ltl = pp.precedenceChain(p).precedes(s,t).after(q).until(a).build().toString()
		assert ltl == "[](Q -> (!(S & (!A) & ()(!A U (T & !A))) U (A | P) | [](!(S & ()<>T))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TrespondsPGlobally() {
		String ltl = pp.responseChain(s,t).respondsTo(p).globally().build().toString() 
		assert ltl== "[] (P -> <>(S & ()<>T))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TrespondsPBeforeA() {
		String ltl = pp.responseChain(s,t).respondsTo(p).before(a).build().toString()
		assert ltl == "<>A -> (P -> (!A U (S & !A & ()(!A U T)))) U A"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TrespondsPAfterQ() {
		String ltl = pp.responseChain(s,t).respondsTo(p).justAfter(q).build().toString()
		assert ltl == "[] (Q -> [] (P -> (S & ()<> T)))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TrespondsPBetweenQandA() {
		String ltl = pp.responseChain(s,t).respondsTo(p).between(q).and(a).build().toString()
		assert ltl == "[] ((Q & <>A) -> (P -> (!A U (S & !A & ()(!A U T)))) U A)"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TrespondsPAfterQuntilA() {
		String ltl = pp.responseChain(s,t).respondsTo(p).after(q).until(a).build().toString()
		assert ltl == "[] (Q -> (P -> (!A U (S & !A & ()(!A U T)))) U (A | [] (P -> (S & ()<> T))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testPrespondsS_TGlobally() {
		String ltl = pp.responseChain(p).respondsTo(s,t).globally().build().toString()
		assert ltl == "[] (S & ()<> T -> ()(<>(T & <> P)))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testPrespondsS_TBeforeA() {
		String ltl = pp.responseChain(p).respondsTo(s,t).before(a).build().toString()
		assert ltl == "<>A -> (S & ()(!A U T) -> ()(!A U (T & <> P))) U A"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testPrespondsS_TAfterQ() {
		String ltl = pp.responseChain(p).respondsTo(s,t).justAfter(q).build().toString()
		assert ltl == "[] (Q -> [] (S & ()<> T -> ()(!T U (T & <> P))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testPrespondsS_TBetweenQandA() {
		String ltl = pp.responseChain(p).respondsTo(s,t).between(q).and(a).build().toString()
		assert ltl == "[] ((Q & <>A) -> (S & ()(!A U T) -> ()(!A U (T & <> P))) U A)"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testPrespondsS_TAfterQuntilA() {
		String ltl = pp.responseChain(p).respondsTo(s,t).after(q).until(a).build().toString()
		assert ltl == "[] (Q -> (S & ()(!A U T) -> ()(!A U (T & <> P))) U (A | [] (S & ()(!A U T) -> ()(!A U (T & <> P)))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TwithoutZrespondsToPGlobally() {
		String ltl = pp.constrainedChain(s,t).without(z).respondsTo(p).globally().build().toString()
		assert ltl == "[] (P -> <>(S & !Z & ()(!Z U T)))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TwithoutZrespondsToPBeforeA() {
		String ltl = pp.constrainedChain(s,t).without(z).respondsTo(p).before(a).build().toString()
		assert ltl == "<>A -> (P -> (!A U (S & !A & !Z & ()((!A & !Z) U T)))) U A"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TwithoutZrespondsToPAfterQ() {
		String ltl = pp.constrainedChain(s,t).without(z).respondsTo(p).justAfter(q).build().toString()
		assert ltl == "[] (Q -> [] (P -> (S & !Z & ()(!Z U T))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TwithoutZrespondsToPBetweenQandA() {
		String ltl = pp.constrainedChain(s,t).without(z).respondsTo(p).between(q).and(a).build().toString()
		assert ltl == "[] ((Q & <>A) -> (P -> (!A U (S & !A & !Z & ()((!A & !Z) U T)))) U A)"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

	void testS_TwithoutZrespondsToPAfterQuntilA() {
		String ltl = pp.constrainedChain(s,t).without(z).respondsTo(p).after(q).until(a).build().toString()
		assert ltl == "[] (Q -> (P -> (!A U (S & !A & !Z & ()((!A & !Z) U T)))) U (A | [] (P -> (S & !Z & ()(!Z U T)))))"
		assert LabeledTransitionSystemFactory.testProperty(ltl) == true
	}

}
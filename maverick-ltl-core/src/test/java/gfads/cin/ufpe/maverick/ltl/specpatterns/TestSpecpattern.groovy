package gfads.cin.ufpe.maverick.ltl.specpatterns;

import groovy.util.GroovyTestCase

class TestSpecpattern extends GroovyTestCase {

	static final ExpressionVariable p = new ExpressionVariable("P")
	static final ExpressionVariable q = new ExpressionVariable("Q")
	static final ExpressionVariable r = new ExpressionVariable("R")
	static final ExpressionVariable s = new ExpressionVariable("S")
	static final ExpressionVariable t = new ExpressionVariable("T")
	static final ExpressionVariable z = new ExpressionVariable("Z")

	static final PropertyPattern pp = Property.create()

	// http://patterns.projects.cs.ksu.edu/documentation/patterns/ltl.shtml
	// spot parser recognize R (capital R) as an operator

	void testAbsencePisFalseGlobally() {
		String ltl = pp.absence(p)
				.isFalse()
				.globally()
				.build()
				.toString()

		assert ltl == "[](!P)"
	}

	void testAbsencePisFalseBeforeR() {
		String ltl = pp.absence(p)
				.isFalse()
				.before(r)
				.build()
				.toString()
		assert ltl == "<>R -> (!P U R)"
	}

	void testAbsencePisFalseAfterQ() {
		String ltl = pp.absence(p)
				.isFalse()
				.justAfter(q)
				.build()
				.toString()
		assert ltl == "[](Q -> [](!P))"
	}

	void testAbsencePisFalseBetweenQandR() {
		String ltl = pp.absence(p)
				.isFalse()
				.between(q)
				.and(r)
				.build()
				.toString()
		assert ltl == "[]((Q & !R & <>R) -> (!P U R))"
	}

	void testAbsencePisFalseStarAfterQuntilR() {
		String ltl = pp.absence(p)
				.isFalse()
				.after(q)
				.until(r)
				.build()
				.toString()
		assert ltl == "[](Q & !R -> (!P W R))"
	}

	void testExistencePbecomesTrueGlobally() {
		assert pp.existence(p).becomesTrue().globally().build().toString() == "<>(P)"
	}

	void testExistencePbecomesTrueStarBeforeR() {
		assert pp.existence(p).becomesTrue().before(r).build().toString() == "!R W (P & !R)"
	}

	void testExistencePbecomesTrueAfterQ() {
		assert pp.existence(p).becomesTrue().after(q).until(r).build().toString() == "[](Q & !R -> (!R U (P & !R)))"
	}

	void testExistencePbecomesTrueStarBetweenQandR() {
		assert pp.existence(p).becomesTrue().between(q).and(r).build().toString() == "[](Q & !R -> (!R W (P & !R)))"
	}

	void testExistencePbecomesTrueStartAfterQuntilR() {
		assert pp.existence(p).becomesTrue().after(q).until(r).build().toString() == "[](Q & !R -> (!R U (P & !R)))"
	}

	void testBoundedExistenceAtMost2Globally() {
		String ltl = pp.boundedExistence(p)
				.occurs(2)
				.globally()
				.build()
				.toString()

		assert ltl == "(!P W (P W (!P W (P W []!P))))"
	}

	void testBoundedExistenceAtMost2BeforeR() {
		assert pp.boundedExistence(p).occurs(2).before(r).build().toString() == "<>R -> ((!P & !R) U (R | ((P & !R) U (R | ((!P & !R) U (R | ((P & !R) U (R | (!P U R)))))))))"
	}

	void testBoundedExistenceAtMost2AfterQ() {
		assert pp.boundedExistence(p).occurs(2).justAfter(q).build().toString() == "<>Q -> (!Q U (Q & (!P W (P W (!P W (P W []!P))))))"
	}

	void testBoundedExistenceAtMost2BetweenQandR() {
		assert pp.boundedExistence(p).occurs(2).between(q).and(r).build().toString() == "[]((Q & <>R) -> ((!P & !R) U (R | ((P & !R) U (R | ((!P & !R) U (R | ((P & !R) U (R | (!P U R))))))))))"
	}

	void testBoundedExistenceAtMost2AfterQuntilR() {
		assert pp.boundedExistence(p).occurs(2).after(q).until(r).build().toString() == "[](Q -> ((!P & !R) U (R | ((P & !R) U (R | ((!P & !R) U (R | ((P & !R) U (R | (!P W R) | []P)))))))))"
	}

	void testUniversalityPisTrueGlobally() {
		assert pp.universality(p).isTrue().globally().build().toString() == "[](P)"
	}

	void testUniversalityPisTrueBeforeR() {
		assert pp.universality(p).isTrue().before(r).build().toString() == "<>R -> (P U R)"
	}

	void testUniversalityPisTrueAfterQy() {
		assert pp.universality(p).isTrue().justAfter(q).build().toString() == "[](Q -> [](P))"
	}

	void testUniversalityPisTrueBetweenQandR() {
		assert pp.universality(p).isTrue().between(q).and(r).build().toString() == "[]((Q & !R & <>R) -> (P U R))"
	}

	void testUniversalityPisTrueAfterQuntilR() {
		assert pp.universality(p).isTrue().after(q).until(r).build().toString() == "[](Q & !R -> (P W R))"
	}

	void testSprecedesPGlobally() {
		assert pp.precedence(s).precedes(p).globally().build().toString() == "!P W S"
	}

	void testSprecedesPBeforeR() {
		assert pp.precedence(s).precedes(p).before(r).build().toString() == "<>R -> (!P U (S | R))"
	}

	void testSprecedesPAfterQ() {
		assert pp.precedence(s).precedes(p).justAfter(q).build().toString() == "[]!Q | <>(Q & (!P W S))"
	}

	void testSprecedesPBetweenQandR() {
		assert pp.precedence(s).precedes(p).between(q).and(r).build().toString() == "[]((Q & !R & <>R) -> (!P U (S | R)))"
	}

	void testSprecedesPAfterQuntilR() {
		assert pp.precedence(s).precedes(p).after(q).until(r).build().toString() == "[](Q & !R -> (!P W (S | R)))"
	}

	void testSrespondsToPGlobally() {
		assert pp.response(s).respondsTo(p).globally().build().toString() == "[](P -> <>S)"
	}

	void testSrespondsToPBeforeR() {
		assert pp.response(s).respondsTo(p).before(r).build().toString() == "<>R -> (P -> (!R U (S & !R))) U R"
	}

	void testSrespondsToPAfterQ() {
		assert pp.response(s).respondsTo(p).justAfter(q).build().toString() == "[](Q -> [](P -> <>S))"
	}

	void testSrespondsToPBetweenQandR() {
		assert pp.response(s).respondsTo(p).between(q).and(r).build().toString() == "[]((Q & !R & <>R) -> (P -> (!R U (S & !R))) U R)"
	}

	void testSrespondsToPAfterQuntilR() {
		assert pp.response(s).respondsTo(p).after(q).until(r).build().toString() == "[](Q & !R -> ((P -> (!R U (S & !R))) W R)"
	}

	void testS_TprecedesPGlobally() {
		assert pp.precedenceChain(s,t).precedes(p).globally().build().toString() == "<>P -> (!P U (S & !P & ()(!P U T)))"
	}

	void testS_TprecedesPBeforeR() {
		assert pp.precedenceChain(s,t).precedes(p).before(r).build().toString() == "<>R -> (!P U (R | (S & !P & ()(!P U T))))"
	}

	void testS_TprecedesPAfterQ() {
		assert pp.precedenceChain(s,t).precedes(p).justAfter(q).build().toString() == "([]!Q) | (!Q U (Q & <>P -> (!P U (S & !P & ()(!P U T))))"
	}

	void testS_TprecedesPBetweenQandR() {
		assert pp.precedenceChain(s,t).precedes(p).between(q).and(r).build().toString() == "[]((Q & <>R) -> (!P U (R | (S & !P & ()(!P U T)))))"

	}

	void testS_TprecedesPAfterQuntilR() {
		assert pp.precedenceChain(s,t).precedes(p).after(q).until(r).build().toString() == "[](Q -> (<>P -> (!P U (R | (S & !P & ()(!P U T))))))"
	}

	void testPprecedesS_TGlobally() {
		assert pp.precedenceChain(p).precedes(s,t).globally().build().toString() == "(<>(S & ()<>T)) -> ((!S) U P))"
	}

	void testPprecedesS_TBeforeR() {
		assert pp.precedenceChain(p).precedes(s,t).before(r).build().toString() == "<>R -> ((!(S & (!R) & ()(!R U (T & !R)))) U (R | P))"
	}

	void testPprecedesS_TAfterQ() {
		assert pp.precedenceChain(p).precedes(s,t).justAfter(q).build().toString() == "([]!Q) | ((!Q) U (Q & ((<>(S & ()<>T)) -> ((!S) U P)))"
	}

	void testPprecedesS_TBetweenQandR() {
		assert pp.precedenceChain(p).precedes(s,t).between(q).and(r).build().toString() == "[]((Q & <>R) -> ((!(S & (!R) & ()(!R U (T & !R)))) U (R | P)))"
	}

	void testPprecedesS_TAfterQuntilR() {
		assert pp.precedenceChain(p).precedes(s,t).after(q).until(r).build().toString() == "[](Q -> (!(S & (!R) & ()(!R U (T & !R))) U (R | P) | [](!(S & ()<>T))))"
	}

	void testS_TrespondsPGlobally() {
		assert pp.responseChain(s,t).respondsTo(p).globally().build().toString() == "[] (P -> <>(S & ()<>T))"
	}

	void testS_TrespondsPBeforeR() {
		assert pp.responseChain(s,t).respondsTo(p).before(r).build().toString() == "<>R -> (P -> (!R U (S & !R & ()(!R U T)))) U R"
	}

	void testS_TrespondsPAfterQ() {
		assert pp.responseChain(s,t).respondsTo(p).justAfter(q).build().toString() == "[] (Q -> [] (P -> (S & ()<> T)))"
	}

	void testS_TrespondsPBetweenQandR() {
		assert pp.responseChain(s,t).respondsTo(p).between(q).and(r).build().toString() == "[] ((Q & <>R) -> (P -> (!R U (S & !R & ()(!R U T)))) U R)"
	}

	void testS_TrespondsPAfterQuntilR() {
		assert pp.responseChain(s,t).respondsTo(p).after(q).until(r).build().toString() == "[] (Q -> (P -> (!R U (S & !R & ()(!R U T)))) U (R | [] (P -> (S & ()<> T))))"
	}

	void testPrespondsS_TGlobally() {
		assert pp.responseChain(p).respondsTo(s,t).globally().build().toString() == "[] (S & ()<> T -> ()(<>(T & <> P)))"
	}

	void testPrespondsS_TBeforeR() {
		assert pp.responseChain(p).respondsTo(s,t).before(r).build().toString() == "<>R -> (S & ()(!R U T) -> ()(!R U (T & <> P))) U R"
	}

	void testPrespondsS_TAfterQ() {
		assert pp.responseChain(p).respondsTo(s,t).justAfter(q).build().toString() == "[] (Q -> [] (S & ()<> T -> ()(!T U (T & <> P))))"
	}

	void testPrespondsS_TBetweenQandR() {
		assert pp.responseChain(p).respondsTo(s,t).between(q).and(r).build().toString() == "[] ((Q & <>R) -> (S & ()(!R U T) -> ()(!R U (T & <> P))) U R)"
	}

	void testPrespondsS_TAfterQuntilR() {
		assert pp.responseChain(p).respondsTo(s,t).after(q).until(r).build().toString() == "[] (Q -> (S & ()(!R U T) -> ()(!R U (T & <> P))) U (R | [] (S & ()(!R U T) -> ()(!R U (T & <> P)))))"
	}

	void testS_TwithoutZrespondsToPGlobally() {
		assert pp.constrainedChain(s,t).without(z).respondsTo(p).globally().build().toString() == "[] (P -> <>(S & !Z & ()(!Z U T)))"
	}

	void testS_TwithoutZrespondsToPBeforeR() {
		assert pp.constrainedChain(s,t).without(z).respondsTo(p).before(r).build().toString() == "<>R -> (P -> (!R U (S & !R & !Z & ()((!R & !Z) U T)))) U R"
	}

	void testS_TwithoutZrespondsToPAfterQ() {
		assert pp.constrainedChain(s,t).without(z).respondsTo(p).justAfter(q).build().toString() == "[] (Q -> [] (P -> (S & !Z & ()(!Z U T))))"
	}

	void testS_TwithoutZrespondsToPBetweenQandR() {
		assert pp.constrainedChain(s,t).without(z).respondsTo(p).between(q).and(r).build().toString() == "[] ((Q & <>R) -> (P -> (!R U (S & !R & !Z & ()((!R & !Z) U T)))) U R)"
	}

	void testS_TwithoutZrespondsToPAfterQuntilR() {
		assert pp.constrainedChain(s,t).without(z).respondsTo(p).after(q).until(r).build().toString() == "[] (Q -> (P -> (!R U (S & !R & !Z & ()((!R & !Z) U T)))) U (R | [] (P -> (S & !Z & ()(!Z U T)))))"
	}

}
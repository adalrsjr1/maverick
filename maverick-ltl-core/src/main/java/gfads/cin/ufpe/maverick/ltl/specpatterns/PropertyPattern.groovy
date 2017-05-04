package gfads.cin.ufpe.maverick.ltl.specpatterns

import gfads.cin.ufpe.maverick.ltl.specpatterns.mappings.AbsenceExpression
import gfads.cin.ufpe.maverick.ltl.specpatterns.mappings.BoundedExpression
import gfads.cin.ufpe.maverick.ltl.specpatterns.mappings.ConstrainedChainExpression;
import gfads.cin.ufpe.maverick.ltl.specpatterns.mappings.ExistenceExpression
import gfads.cin.ufpe.maverick.ltl.specpatterns.mappings.PrecedenceChainExpression;
import gfads.cin.ufpe.maverick.ltl.specpatterns.mappings.PrecedenceExpression
import gfads.cin.ufpe.maverick.ltl.specpatterns.mappings.ResponseChainExpression;
import gfads.cin.ufpe.maverick.ltl.specpatterns.mappings.ResponseExpression;
import gfads.cin.ufpe.maverick.ltl.specpatterns.mappings.UniversalityExpression;
import groovy.util.logging.Slf4j

@Slf4j
class PropertyPattern {
	/**
	 * <p>
	 * To describe a portion of a system's execution that is free of 
	 * certain events or states. Also known as <b>Never</b>.
	 * </p>
	 * <p>
	 * <b>Examples and Known Uses</b>
	 * </p>
	 * <p>
	 * The most common example is mutual exclusion. In a state-based model, 
	 * the scope would be global and P would be a state formula that is true 
	 * if more than one process is in its critical section. For an event-based model, 
	 * the scope would be a segment of the execution in which some process is in its 
	 * critical section (i.e., between an enter section event and a leave section event), 
	 * and P would be the event that some other process enters its critical section.
	 * </p>
	 * @param p a variable
	 * @return a builder to create an AbsenceExpression
	 */
	AbsenceExpression absence(ExpressionVariable p) {
		new AbsenceExpression(p)
	}

	/**
	 * <p>To describe a portion of a system's execution that contains an instance of 
	 * certain events or states. Also known as <b>Eventually</b>.</p>
	 * <p>
	 * <b>Examples and Known Uses</b>
	 * </p>
	 * <p>
	 * The classic example of existence is specifying termination, 
	 * e.g., on all executions do we eventually reach a terminal state.
	 * </p>
	 * @param p a variable
	 * @return a builder to create an ExistenceExpression
	 */
	ExistenceExpression existence(ExpressionVariable p) {
		new ExistenceExpression(p)
	}

	/**
	 * <p>
	 * To describe a portion of a system's execution that contains at most a specified 
	 * number of instances of a designated state transition or event.
	 * </p>
	 * </p>
	 * <b>Examples and Known Uses</b>
	 * </p>
	 * <p>
	 * Bounded overtaking properties can naturally be expressed using instances of this 
	 * pattern. For example, if we wish to say that process 1 can enter its critical region 
	 * at most twice while process 2 is waiting to enter its region we would use a between 
	 * scope (delimited by process 2 entering and exiting its waiting region) with 2-bounded 
	 * existence for process 1 entering its critical region.
	 * </p>
	 * <p>
	 * Mappings for bounds other than two can be constructed relatively simply from the given 
	 * mappings. For QREs one simply defines k appropriately. For LTL and CTL we simply add 
	 * additional copies of the nested until structures, for example in LTL 3-bounded global 
	 * existence is:
	 * </p>
	 * <p>
	 * (!P W (P W <b>(!P W (P W (!P W (P W []!P)))</b>)))
	 * </p>
	 * (where the nested 2-bounded version is in bold).
	 * 
	 * @param p
	 * @return
	 */
	BoundedExpression boundedExistence(ExpressionVariable p) {
		new BoundedExpression(p)
	}

	/**
	 * <p>
	 * To describe a portion of a system's execution which contains only 
	 * states that have a desired property. Also known as Henceforth and Always.
	 * </p>
	 * <p>
	 * <b>Examples and Known Uses</b>
	 * </p>
	 * <p>
	 * This pattern can be applied in most situations where the absence pattern 
	 * can be applied. This is especially true for state-based formalisms, e.g., 
	 * where mutual exclusion could be formulated as absence or universality 
	 * with a between scope.
	 * </p>
	 * @param p
	 * @return
	 */
	UniversalityExpression universality(ExpressionVariable p) {
		new UniversalityExpression(p)
	}

	/**
	 * <p>
	 * To describe relationships between a pair of events/states where the 
	 * occurrence of the first is a necessary pre-condition for an occurrence 
	 * of the second. We say that an occurrence of the second is enabled by 
	 * an occurrence of the first.
	 * </p>
	 * </p>
	 * <b>Examples and Known Uses</b>
	 * </p>
	 * <p>
	 * Precedence properties occur quite commonly in specifications of 
	 * concurrent systems. One common example is in describing a requirement 
	 * that a resource is only granted in response to a request. 
	 * </p>
	 * @param s
	 * @return
	 */
	PrecedenceExpression precedence(ExpressionVariable s) {
		new PrecedenceExpression(s)
	}


	/**
	 * <p>
	 * To describe cause-effect relationships between a pair of events/states. 
	 * An occurrence of the first, the cause, must be followed by an occurrence 
	 * of the second, the effect. Also known as Follows and Leads-to.
	 * </p>
	 * <p>
	 * <b>Examples and Known Uses</b>
	 * </p>
	 * <p>Response properties occur quite commonly in specifications of concurrent 
	 * systems. Perhaps the most common example is in describing a requirement that 
	 * a resource must be granted after it is requested.
	 * </p>
	 * @param s
	 * @return
	 */
	ResponseExpression response(ExpressionVariable s) {
		new ResponseExpression(s)
	}

	/**
	 * <p>
	 * This is a scalable pattern. We describe the 1 cause - 2 effect version here.
	 * To describe a relationship between an event/state P and a sequence of 
	 * events/states (S, T) in which the occurrence of S followed by T within 
	 * the scope must be preceded by an occurrence of the the sequence P within 
	 * the same scope. In state-based formalisms, the beginning of the enabled 
	 * sequence (S, T) may be satisfied by the same state as the enabling condition 
	 * (i.e., P and S may be true in the same state).
	 * </p>
	 * <p>
	 * <b>Examples and Known Uses</b>
	 * </p>
	 * <p>
	 * An example of this pattern, assuming reliable communication between client 
	 * and server, is that "If a client makes a request and there is no response, 
	 * then the server must have crashed." This would be expressed by parameterizing 
	 * the constrained variant of the 1-2 precedence chain pattern as:
	 * ServerCrash precedes ClientRequest, []!Response without Responsevin LTL.
	 * </p>
	 * @param s
	 * @return
	 */
	PrecedenceChainExpression precedenceChain(ExpressionVariable s) {
		new PrecedenceChainExpression(s)
	}

	/**
	 * <p>
	 * This is a scalable pattern. We describe the 1 cause - 2 effect version here.
	 * </p>
	 * To describe a relationship between an event/state P and a sequence of 
	 * events/states (S, T) in which the occurrence of S followed by T within 
	 * the scope must be preceded by an occurrence of the the sequence P within 
	 * the same scope. In state-based formalisms, the beginning of the enabled 
	 * sequence (S, T) may be satisfied by the same state as the enabling 
	 * condition (i.e., P and S may be true in the same state).
	 * <p>
	 * <b>Examples and Known Uses</b>
	 * </p>
	 *  An example of this pattern, assuming reliable communication between 
	 *  client and server, is that "If a client makes a request and there is 
	 *  no response, then the server must have crashed." This would be expressed 
	 *  by parameterizing the constrained variant of the 1-2 precedence chain pattern as:
	 *  </br>ServerCrash precedes ClientRequest, []!Response without Response</br>
	 *  in LTL. 
	 * @param s
	 * @param t
	 * @return
	 */
	PrecedenceChainExpression precedenceChain(ExpressionVariable s, ExpressionVariable t) {
		new PrecedenceChainExpression(s, t)
	}

	/**
	 * <p>
	 * This is a scalable pattern. We describe the intent of the 
	 * 1 stimulus - 2 response version here.
	 * </p>
	 * <p>
	 * To describe a relationship between a stimulus event (P) and 
	 * a sequence of two response events (S,T) in which the occurrence 
	 * of the stimulus event must be followed by an occurrence of the 
	 * sequence of response events within the scope. In state-based 
	 * formalisms, the states satisfying the response must be distinct 
	 * (i.e., S and T must be true in different states to count as a response), 
	 * but the response may be satisfied by the same state as the stimulus 
	 * (i.e., P and S may be true in the same state).
	 * </p>
	 * <p>
	 * If a resource allocator grants a process access to a resource (GrantRes), 
	 * the process will start using the resource (BeginRes) and finish using 
	 * the resource (EndRes).
	 * </p>
	 * @param p
	 * @return
	 */
	ResponseChainExpression responseChain(ExpressionVariable p) {
		new ResponseChainExpression(p)
	}

	/**
	 * <p>
	 * This is a scalable pattern. We describe the intent of the 
	 * 1 stimulus - 2 response version here.
	 * </p>
	 * <p>
	 * To describe a relationship between a stimulus event (P) and 
	 * a sequence of two response events (S,T) in which the occurrence 
	 * of the stimulus event must be followed by an occurrence of the 
	 * sequence of response events within the scope. In state-based 
	 * formalisms, the states satisfying the response must be distinct 
	 * (i.e., S and T must be true in different states to count as a response), 
	 * but the response may be satisfied by the same state as the stimulus 
	 * (i.e., P and S may be true in the same state).
	 * <p>
	 * If a resource allocator grants a process access to a resource (GrantRes), 
	 * the process will start using the resource (BeginRes) and 
	 * finish using the resource (EndRes).
	 * </p>
	 * @param s
	 * @param t
	 * @return
	 */
	ResponseChainExpression responseChain(ExpressionVariable s, ExpressionVariable t) {
		new ResponseChainExpression(s, t)
	}

	/**
	 * <p>
	 * To describe a variant of response and precedence chain patterns that 
	 * restrict user specified events from occurring between pairs of states/events 
	 * in the chain sequences.
	 * </p>
	 * <p>
	 * Consecutive pairs of states/events in chain sequences are refered to as links. 
	 * This pattern allows specification of the absence of states/events from individual 
	 * links.
	 * </p>
	 * </p>
	 * <b>Examples and Known Uses</b>
	 * </p>
	 * <p>
	 * Constrained chain patterns are surprisingly useful. Some of our recent work with 
	 * model checking of GUI software used CTL mappings for constrained 1-2 response 
	 * patterns with global scope (e.g., AG(P -> AF(S & !Z & AX(A[!Z U T])))). In the 
	 * following, user indicates that the user is allowed to interact with the GUI, 
	 * select, print, help, ok, ... are interactions that the user can perform, and 
	 * error, address are system responses.
	 * </p>
	 * <p>
	 * When a system error message is displayed the only allowable action is user 
	 * acknowledgement via the 'ok' button.
	 * </br>
	 *    AG(error -> AF(user & !(print | help | ...) & AX(A[!(print | help | ...) U ok])))
	 * </br>
	 * When the user selects a customer the address information is displayed before the 
	 * user is allowed another interaction .
	 * </br>
	 *   AG(select -> AF(!user & AX(A[!user U address])))
	 * </br>
	 * The latter example had !user filling the role of both S and !Z in the mapping and 
	 * it was simplified.
	 * </p>
	 * @param s
	 * @param t
	 * @return
	 */
	ConstrainedChainExpression constrainedChain(ExpressionVariable s, ExpressionVariable t) {
		new ConstrainedChainExpression(s, t)
	}
}

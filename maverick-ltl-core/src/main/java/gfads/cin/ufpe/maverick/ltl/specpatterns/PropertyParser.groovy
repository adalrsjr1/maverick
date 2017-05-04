package gfads.cin.ufpe.maverick.ltl.specpatterns


import groovy.util.logging.Slf4j


@Slf4j
public class PropertyParser {
	private def stack = [] as Stack 
	
	def create(Closure closure) {
		stack.push(Property.create())
		
		closure.delegate = this
		def result = closure().build()
		stack.clear()
		return result
	}
	
	/*
	 * Type verification. Integers only are allowed defining the number of occurrence of some token (string)
	 */
	private def convertArg(arg) {
		if(arg instanceof String) {
			return new ExpressionVariable(arg)
		}
		
		if(arg instanceof Integer) {
			return arg
		}
	}
	
	// convert array of args in single parameters
	
	private def args0(object, method, args) {
		object."${method}"()
	}
	
	private def args1(object, method, args) {
		object."${method}"(convertArg(args[0]))
	}
	
	private def args2(object, method, args) {
		object."${method}"(convertArg(args[0]), convertArg(args[1]))
	}
	
	def methodMissing(String name, args) {
		def result
		
		switch(args.size()) {
			case 0 : result = args0(stack.peek(), name, args);
					 break;
			case 1 : result = args1(stack.peek(), name, args);
					 break;
			case 2 : result = args2(stack.peek(), name, args);
			         break;
			default: throw new MissingMethodException(name, stack.peek().class, args)
		}
		stack.push(result)
	}
	
	/*
	 * Allow call no-args methods without brackets
	 */
	def propertyMissing(String name) {
		"$name"()
	}

	/**
	 * Works like {@link GroovyShell.evalute}
	 * @param script
	 * @return
	 */
	public static def evaluate(script, PropertyParser parser) {
		Binding binding = new Binding()
		binding.setVariable("property", parser.&create)
		GroovyShell shell = new GroovyShell(binding)
		shell.evaluate(script)
	}
}

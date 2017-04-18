package actions;

import gfads.cin.ufpe.maverick.executor.context.Context;

public class HelloWorld {
	
	public void execute(Context context) {
		System.out.println("HelloWorld!!! :: Context=" + context);
	}
}

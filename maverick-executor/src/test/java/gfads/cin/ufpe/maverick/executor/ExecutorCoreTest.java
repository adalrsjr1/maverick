package gfads.cin.ufpe.maverick.executor;

import java.lang.reflect.Method;

import gfads.cin.ufpe.maverick.events.MaverickChangePlan;
import gfads.cin.ufpe.maverick.events.MaverickPolicy;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class ExecutorCoreTest extends TestCase {
	
	public static String actionsFolder = "src/test/resources/actions";
	public static final ExecutorCore core = new ExecutorCore(actionsFolder);
	
	public void testLoadClass() throws Exception {
		Method method = core.getClass().getDeclaredMethod("loadClass", String.class);
		method.setAccessible(true);
		Class<?> cls = (Class<?>) method.invoke(core, "HelloWorld");
		
		assertEquals(cls.getSimpleName(), "HelloWorld");
	}
	
	public void testActionHelloWorld() throws Exception {
		ExecutorCore core = new ExecutorCore(actionsFolder);
		MaverickChangePlan changePlan = new MaverickChangePlan(new MaverickPolicy(null, "HelloWorld", 0), null);
		assertTrue(core.doWork(changePlan));
	}
}
	
package gfads.cin.ufpe.maverick.executor;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.events.MaverickChangePlan;
import gfads.cin.ufpe.maverick.events.MaverickPolicy;
import gfads.cin.ufpe.maverick.executor.context.Context;

@Component
public class ExecutorCore {

	private String actionsRepository;
	private ClassLoader classLoader;
	
	@Autowired
	private Context context;
	
	public ExecutorCore(@Value("${executor.actions.repository}") String actionsRepository) {
		this.actionsRepository = actionsRepository;
		URL url;
		try {
			url = new URL(actionsRepository);
			classLoader = new URLClassLoader (new URL[] {url}, this.getClass().getClassLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doWork(MaverickChangePlan changePlan) throws Exception {
		String action = changePlan.getAction(); // xxx.yyy.xxx.command
		Class<?> classToLoad = Class.forName(action, true, classLoader);
		Method method = classToLoad.getDeclaredMethod("execute", Context.class);
		Object instance = classToLoad.newInstance();
		method.invoke(instance, context);
	}
	
	public static void main(String[] args) throws Exception {
		ExecutorCore core = new ExecutorCore("file://src/main/resources/actions-repository");
		MaverickChangePlan changePlan = new MaverickChangePlan(new MaverickPolicy(null, "actions.HelloWorld", 0), null);
		core.doWork(changePlan);
	}
}

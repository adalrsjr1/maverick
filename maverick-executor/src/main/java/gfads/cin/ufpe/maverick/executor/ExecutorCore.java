package gfads.cin.ufpe.maverick.executor;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.events.MaverickChangePlan;
import gfads.cin.ufpe.maverick.executor.context.Context;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;

@Component
public class ExecutorCore {
	private static final Logger LOG = LoggerFactory.getLogger(ExecutorCore.class);
	
	@Autowired
	private Context context;
	
	private GroovyScriptEngine scriptEngine;
	private Map<String, Class<?>> classCache = new HashMap<>();
	
	public ExecutorCore(String actionsRepository) {
		URL[] urls;
		try {
			urls = new URL[] {new File(actionsRepository).toURI().toURL()};
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		scriptEngine = new GroovyScriptEngine(urls, Thread.currentThread().getContextClassLoader());
	}
	
	private Class<?> loadClass(String name) throws Exception {
		if(classCache.containsKey(name)) {
			return classCache.get(name);
		}
		Class<?> cls = scriptEngine.loadScriptByName(name+".groovy");
		classCache.put(name, cls);
		return cls;
	}
	
	public boolean doWork(MaverickChangePlan changePlan) throws Exception {
		String action = changePlan.getActionName();
		
		Class<?> classToLoad = null;
		try {
			classToLoad = loadClass(action);
			Method method = classToLoad.getDeclaredMethod("execute", Object.class, Object.class);
			Object instance = classToLoad.newInstance();
			return (Boolean) method.invoke(instance, context, changePlan.getAction());
		}
		catch(ResourceException e) {
			LOG.warn(e.getMessage());
		}
		
		return false;
	}
}

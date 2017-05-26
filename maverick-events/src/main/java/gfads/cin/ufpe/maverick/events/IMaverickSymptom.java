package gfads.cin.ufpe.maverick.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public interface IMaverickSymptom extends Log {

	default public Object get(String property) {
		Method methods[] = this.getClass().getMethods();
		Method method = Arrays.asList(methods).stream()
		                      .filter(m -> m.getName().equalsIgnoreCase("get"+property))
		                      .findFirst()
		                      .orElse(null);
		
		if(Objects.nonNull(method)) {
			try {
				return method.invoke(this, null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		Object log = getLog();
		
		if(log instanceof Log) {
			return ((Log) log).get(property);
		}
		
		return null;
	}

	long getElapsedTime(TimeUnit timeUnit);

	String getContainerId();

	String getContainerName();

	String getSource();

	Object getLog();

	Map<String, Object> getLogAsMap();

	Object getLogMessage();
	
}
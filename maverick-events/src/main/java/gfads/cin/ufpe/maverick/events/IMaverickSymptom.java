package gfads.cin.ufpe.maverick.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public interface IMaverickSymptom {

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
		
		Map<String, Object> logMap = getLogAsMap();
		if(!logMap.isEmpty()) {
			Object result = logMap.get(property);
			if(Objects.isNull(result)) {
				Object message = getLogMessage();
				if(message instanceof Map) {
					return ((Map<String, Object>) message).get(property);
				}
			}
			return result;
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
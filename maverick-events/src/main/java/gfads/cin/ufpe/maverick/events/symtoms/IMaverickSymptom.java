package gfads.cin.ufpe.maverick.events.symtoms;

import java.lang.reflect.Method;
import java.util.Arrays;
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	String getContainerId();

	String getContainerName();

	String getSource();

	Object getLogMessage();
	
	IMaverickSymptom getEmpty();
	
}
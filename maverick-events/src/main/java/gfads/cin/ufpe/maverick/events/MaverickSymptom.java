package gfads.cin.ufpe.maverick.events;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MaverickSymptom extends MaverickEvent {

	private static final long serialVersionUID = -2345252892630650139L;

	@JsonProperty("container_id")
	private String containerId;
	@JsonProperty("container_name")
	private String containerName;
	@JsonProperty("source")
	private String source;
	@JsonProperty("log")
	private String log;
	
	private Map<String,Object> logCache;

	private MaverickSymptom() { }
	
	public static MaverickSymptom newMaverickSymptom(String json) {
		json = json.replaceAll("\r", "");
		ObjectMapper mapper = new ObjectMapper();
		MaverickSymptom result = null;
		try {
			result = mapper.readValue(json, MaverickSymptom.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static MaverickSymptom newMaverickSymptom(byte[] jsonSerialized) {
		return newMaverickSymptom(new String(jsonSerialized));
	}

	@Override
	public Object get(String property) {
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
	
	public long getElapsedTime(TimeUnit timeUnit) {
		try {
			return timeUnit.convert(System.currentTimeMillis()-(Long) get("timeMillis"), TimeUnit.MILLISECONDS);
		}
		catch(Exception e) {
			return -1L;
		}
	}
	
	public String getContainerId() {
		return containerId;
	}

	public String getContainerName() {
		return containerName;
	}

	public String getSource() {
		return source;
	}

	public String getLog() {
		return log;
	}
	
	private boolean isJson(ObjectMapper mapper, String str) {
		try {
			mapper.readTree(str);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public Map<String, Object> getLogAsMap() {
		if(Objects.nonNull(logCache)) {
			return logCache;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String log = getLog();
		if(isJson(mapper, log)) {
			try {
				logCache = mapper.readValue(log, new TypeReference<Map<String, Object>>(){});
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		return Objects.nonNull(logCache) ? logCache : new HashMap<String, Object>();
	}
	
	public Object getLogMessage() {
		String message = (String) getLogAsMap().get("message");
		ObjectMapper mapper = new ObjectMapper();
		
		if(!isJson(mapper, message)) {
			return message;
		}
		
		try {
			return mapper.readValue(message, new TypeReference<Map<String, Object>>(){});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return "MaverickSymptom [containerId=" + containerId + ", containerName=" + containerName + ", source=" + source
				+ ", log=" + log + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(! (o instanceof MaverickSymptom)) return false;
		
		MaverickSymptom symptom = (MaverickSymptom) o;
		
		return Objects.equals(containerId, symptom.getContainerId())
			   && Objects.equals(containerName, symptom.getContainerName())
			   && Objects.equals(log, symptom.getLog())
			   && Objects.equals(source, symptom.getSource());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(containerId, containerName, source, log);
	}
}

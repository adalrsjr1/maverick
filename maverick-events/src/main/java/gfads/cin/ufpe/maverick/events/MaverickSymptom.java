package gfads.cin.ufpe.maverick.events;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MaverickSymptom extends MaverickEvent implements IMaverickSymptom {

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

	private MaverickSymptom(String containerId, String containerName, String source, String log) {
		this.containerId = containerId;
		this.containerName = containerName;
		this.source = source;
		this.log = log;
	}
	
	public static MaverickSymptom newMaverickSymptom(String containerId, String containerName, String source, String log) {
		return new MaverickSymptom(containerId, containerName, source, log);
	}

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

	/* (non-Javadoc)
	 * @see gfads.cin.ufpe.maverick.events.IMaverickInterfave#get(java.lang.String)
	 */
//	@Override
	public Object get(String property) {
		return IMaverickSymptom.super.get(property);
	}
	
	/* (non-Javadoc)
	 * @see gfads.cin.ufpe.maverick.events.IMaverickInterfave#getElapsedTime(java.util.concurrent.TimeUnit)
	 */
	@Override
	public long getElapsedTime(TimeUnit timeUnit) {
		try {
			return timeUnit.convert(System.currentTimeMillis()-(Long) get("timeMillis"), TimeUnit.MILLISECONDS);
		}
		catch(Exception e) {
			return -1L;
		}
	}
	
	/* (non-Javadoc)
	 * @see gfads.cin.ufpe.maverick.events.IMaverickInterfave#getContainerId()
	 */
	@Override
	public String getContainerId() {
		return containerId;
	}

	/* (non-Javadoc)
	 * @see gfads.cin.ufpe.maverick.events.IMaverickInterfave#getContainerName()
	 */
	@Override
	public String getContainerName() {
		return containerName;
	}

	/* (non-Javadoc)
	 * @see gfads.cin.ufpe.maverick.events.IMaverickInterfave#getSource()
	 */
	@Override
	public String getSource() {
		return source;
	}

	/* (non-Javadoc)
	 * @see gfads.cin.ufpe.maverick.events.IMaverickInterfave#getLog()
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see gfads.cin.ufpe.maverick.events.IMaverickInterfave#getLogAsMap()
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see gfads.cin.ufpe.maverick.events.IMaverickInterfave#getLogMessage()
	 */
	@Override
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
		
		IMaverickSymptom symptom = (IMaverickSymptom) o;
		
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

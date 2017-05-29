package gfads.cin.ufpe.maverick.events.symtoms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;

import gfads.cin.ufpe.maverick.events.MaverickEvent;

public class DockerSymptom extends MaverickEvent implements IMaverickSymptom {

	private static final long serialVersionUID = -2345252892630650139L;

	public static final DockerSymptom EMPTY_DOCKER_SYMPTOM = new DockerSymptom();
	
	@JsonProperty("container_id")
	private String containerId = "";
	@JsonProperty("container_name")
	private String containerName = "";
	@JsonProperty("source")
	private String source = "";
	@JsonProperty("log")
	private String logMessage = "";
	
	private Map<String,Object> logCache;

	private DockerSymptom() { }

	private void setLogMessage(String log) {
		logMessage = sanitizeColor(log);
	}
	
	private DockerSymptom(String containerId, String containerName, String source, String log) {
		this.containerId = containerId;
		this.containerName = containerName;
		this.source = source;
		setLogMessage(log);
	}
	
	public static DockerSymptom newMaverickSymptom(String containerId, String containerName, String source, String log) {
		return new DockerSymptom(containerId, containerName, source, log);
	}

	private static String sanitizeColor(String token) {
		String regex = "(\\u001b\\[[0-9]+m){1}";
		return token.replaceAll(regex, "");
	}
	
	public static DockerSymptom newMaverickSymptom(String json) {
		ObjectMapper mapper = new ObjectMapper();
		DockerSymptom result = null;
		try {
			result = mapper.readValue(json, DockerSymptom.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static DockerSymptom newMaverickSymptom(byte[] jsonSerialized) {
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
	public String getLogMessage() {
		return logMessage;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
		           	      .add("containerId", containerId)
		           	      .add("containerName", containerName)
		           	      .add("source", source)
		                  .toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(! (o instanceof DockerSymptom)) return false;
		
		IMaverickSymptom symptom = (IMaverickSymptom) o;
		
		return Objects.equals(containerId, symptom.getContainerId())
			   && Objects.equals(containerName, symptom.getContainerName())
			   && Objects.equals(logMessage, symptom.getLogMessage())
			   && Objects.equals(source, symptom.getSource());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(containerId, containerName, source, logMessage);
	}
	
	@Override
	public IMaverickSymptom getEmpty() {
		return EMPTY_DOCKER_SYMPTOM;
	}
}

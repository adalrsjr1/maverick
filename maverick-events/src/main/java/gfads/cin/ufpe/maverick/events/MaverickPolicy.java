package gfads.cin.ufpe.maverick.events;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class MaverickPolicy implements Comparable<MaverickPolicy>, Serializable {
	private static final long serialVersionUID = 6018864738458188034L;

	@JsonProperty("changeRequest")
	private String changeRequest;
	@JsonProperty("action")
	private String action;
	@JsonProperty("priority")
	private int priority;

	/**
	 * Builder with class names as arguments
	 * @param source
	 * @param action
	 */
	public MaverickPolicy(@JsonProperty("changeRequest") String changeRequest,
	               @JsonProperty("action") String action,
	               @JsonProperty("priority") int priority) {
		this.changeRequest = changeRequest;
		this.action = action;
		this.priority = priority;
	}

	public String getChangeRequest() {
		return changeRequest;
	}

	public String getAction() {
		return action;
	}

	public int getPriority() {
		return priority;
	}
	
	public boolean changeRequestMatch(MaverickChangeRequest changeRequest) {
		return this.changeRequest.equals(changeRequest.getName());
	}

	public String serializeToJson() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] serializeToBytes() {
		return SerializationUtils.serialize(this);
	}

	public static MaverickPolicy deserialize(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, MaverickPolicy.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static MaverickPolicy deserialize(byte[] bytes) {
		return SerializationUtils.deserialize(bytes);
	}

	@Override
	public int compareTo(MaverickPolicy o) {
		if(o.priority == priority) {
			return action.compareTo(o.action);
		}
		return Integer.compare(o.priority, priority);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAction(), getChangeRequest(), getPriority());
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		
		MaverickPolicy policy = (MaverickPolicy) obj;
		
		return Objects.equals(this.action, policy.action) 
			   && Objects.equals(this.changeRequest, policy.changeRequest)
			   && Objects.equals(this.priority, policy.priority);
	}

	@Override
	public String toString() {
		return "MaverickPolicy [changeRequest=" + changeRequest + ", action=" + action + ", priority=" + priority + "]";
	}
	
	
}
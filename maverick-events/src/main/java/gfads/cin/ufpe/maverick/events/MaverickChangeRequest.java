package gfads.cin.ufpe.maverick.events;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MaverickChangeRequest extends MaverickEvent {
	private static final long serialVersionUID = -3297778122994192136L;

	private MaverickSymptom symptom;
	private String name;
	
	private Map<String, Serializable> metadata;
	
	public MaverickChangeRequest(String name, MaverickSymptom symptom) {
		metadata = new HashMap<>();
		this.symptom = symptom;
		this.name = name;
	}
	
	public void registerMetadata(String key, Serializable value) {
		if(Objects.nonNull(get("key"))) {
			throw new RuntimeException("This property is already present in this event.");
		}
		metadata.put(key, value);
	}
	
	public void removeMetadata(String key) {
		metadata.remove(key);
	}
	
	@Override
	public Object get(String property) {
		if(property.equalsIgnoreCase("name")) {
			return getName();
		}
		return metadata.getOrDefault(property, (Serializable) symptom.get(property));
	}
	
	public String getName() {
		return name;
	}
	
	public MaverickSymptom getSymptom() {
		return symptom;
	}
	
	public long getElapsedTime(TimeUnit timeUnit) {
		return symptom.getElapsedTime(timeUnit);
	}

	@Override
	public String toString() {
		return "MaverickChangeRequest [symptom=" + symptom + ", name=" + name + ", metadata=" + metadata + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(! (o instanceof MaverickSymptom)) return false;
		
		MaverickChangeRequest changeRequest = (MaverickChangeRequest) o;
		
		return Objects.equals(symptom, changeRequest.getSymptom())
			   && Objects.equals(name, changeRequest.getName());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(symptom, name);
	}
}

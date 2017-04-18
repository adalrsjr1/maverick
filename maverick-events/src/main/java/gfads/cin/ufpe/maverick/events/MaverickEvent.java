package gfads.cin.ufpe.maverick.events;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

public abstract class MaverickEvent implements Serializable {
	public byte[] serialize() {
		return SerializationUtils.serialize(this);
	}
	
	public static MaverickEvent deserialize(byte[] message) {
		return SerializationUtils.deserialize(message);
	}
	
	public abstract Object get(String property);
}

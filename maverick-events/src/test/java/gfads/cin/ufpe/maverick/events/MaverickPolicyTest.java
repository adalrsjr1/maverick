package gfads.cin.ufpe.maverick.events;

import junit.framework.TestCase;

public class MaverickPolicyTest extends TestCase {
	
	public void testPolicySerialization() {
		String json = "{\"changeRequest\":\"testChangeRequest\",\"action\":{\"name\":\"testAction\",\"attr1\":1,\"attr2\":3.14,\"attr3\":\"value\"},\"priority\":5}";
		
		MaverickPolicy policy = MaverickPolicy.deserialize(json);
		
		assertNotNull(policy);
		
		byte[] serializedPolicy = policy.serializeToBytes();
		MaverickPolicy deserializedPolicy = MaverickPolicy.deserialize(serializedPolicy);
		
		assertEquals(policy, deserializedPolicy);

		String newJson = deserializedPolicy.serializeToJson();
		
		assertEquals(json, newJson);
	}
}

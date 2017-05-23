package gfads.cin.ufpe.maverick.events;

import junit.framework.TestCase;

public class MaverickChangeRequestTest extends TestCase {
	public void testGet() {
		String json = "{\"log\":\"{\\\"timeMillis\\\":1489252411395,\\\"thread\\\":\\\"main\\\",\\\"level\\\":\\\"INFO\\\",\\\"loggerName\\\":\\\"gfads.cin.ufpe.maverick.sandbox.App\\\",\\\"message\\\":\\\"{\\\\\\\"key\\\\\\\":\\\\\\\"value\\\\\\\"}\\\",\\\"endOfBatch\\\":false,\\\"loggerFqcn\\\":\\\"org.apache.logging.slf4j.Log4jLogger\\\"}\r\r\",\"container_id\":\"37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78\",\"container_name\":\"/java\",\"source\":\"stdout\"}";
		String result ="MaverickSymptom [containerId=37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78, containerName=/java, source=stdout, log={\"timeMillis\":1489252411395,\"thread\":\"main\",\"level\":\"INFO\",\"loggerName\":\"gfads.cin.ufpe.maverick.sandbox.App\",\"message\":\"QWE\",\"endOfBatch\":false,\"loggerFqcn\":\"org.apache.logging.slf4j.Log4jLogger\"}]";
		IMaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json);
		
		MaverickChangeRequest changeRequest = new MaverickChangeRequest("test", symptom);
	
		byte[] bytes = changeRequest.serialize();
		MaverickChangeRequest deserialized = (MaverickChangeRequest) MaverickChangeRequest.deserialize(bytes);
		
		assertTrue("37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78".equals(deserialized.get("containerId")));
		assertTrue("/java".equals(deserialized.get("containername")));
		assertTrue("stdout".equals(deserialized.get("SOUrCe")));
		assertTrue(1489252411395L == (Long) deserialized.get("timeMillis"));
		assertTrue("value".equals(deserialized.get("key")));
		assertNull(symptom.get("timemillis")); // properties into log are case sensitive
		assertTrue("test".equals(deserialized.get("name")));
	}
}

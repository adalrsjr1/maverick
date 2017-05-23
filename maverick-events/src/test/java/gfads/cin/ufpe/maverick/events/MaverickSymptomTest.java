package gfads.cin.ufpe.maverick.events;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

public class MaverickSymptomTest extends TestCase {

	public void testMaverickSymptomInstantiation() {
		MaverickSymptom symptom1 = MaverickSymptom.newMaverickSymptom("container_id", "container_name", "source", "log");
		byte[] serialized1 = symptom1.serialize();
		
		String json= "{\"container_id\":\"container_id\", \"container_name\":\"container_name\", \"source\":\"source\", \"log\":\"log\"}";
		MaverickSymptom symptom2 = MaverickSymptom.newMaverickSymptom(json);
		byte[] serialized2 = symptom2.serialize();
		
		assertEquals(MaverickSymptom.deserialize(serialized1), MaverickSymptom.deserialize(serialized2));
	}
	
	public void testCurlHttpFluent() {
		String json= "{\"container_id\":\"container_test\", \"container_name\":\"test_name\", \"source\":\"test_source\", \"log\":\"test_log\"}";
		MaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json.getBytes());
		
		byte[] serialized = symptom.serialize();
		IMaverickSymptom nSymptom = (IMaverickSymptom) MaverickEvent.deserialize(serialized);
		assertTrue(symptom.equals(nSymptom));
	}
	
	public void testSerialization() {
		String json = "{\"log\":\"{\\\"timeMillis\\\":1489252411395,\\\"thread\\\":\\\"main\\\",\\\"level\\\":\\\"INFO\\\",\\\"loggerName\\\":\\\"gfads.cin.ufpe.maverick.sandbox.App\\\",\\\"message\\\":\\\"QWE\\\",\\\"endOfBatch\\\":false,\\\"loggerFqcn\\\":\\\"org.apache.logging.slf4j.Log4jLogger\\\"}\r\r\",\"container_id\":\"37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78\",\"container_name\":\"/java\",\"source\":\"stdout\"}";
		MaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json);
		
		byte[] serialized = symptom.serialize();
		IMaverickSymptom nSymptom = (IMaverickSymptom) MaverickEvent.deserialize(serialized);
		
		assertTrue(symptom.equals(nSymptom));
	}
	
	public void testElapsedTime() {
		String json = "{\"log\":\"{\\\"timeMillis\\\":1489252411395,\\\"thread\\\":\\\"main\\\",\\\"level\\\":\\\"INFO\\\",\\\"loggerName\\\":\\\"gfads.cin.ufpe.maverick.sandbox.App\\\",\\\"message\\\":\\\"QWE\\\",\\\"endOfBatch\\\":false,\\\"loggerFqcn\\\":\\\"org.apache.logging.slf4j.Log4jLogger\\\"}\r\r\",\"container_id\":\"37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78\",\"container_name\":\"/java\",\"source\":\"stdout\"}";
		MaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json);
		
		byte[] serialized = symptom.serialize();
		IMaverickSymptom nSymptom = (IMaverickSymptom) MaverickEvent.deserialize(serialized);
		assertTrue(nSymptom.getElapsedTime(TimeUnit.MILLISECONDS) >= 0);
		
		json = "{\"log\":\"{\\\"thread\\\":\\\"main\\\",\\\"level\\\":\\\"INFO\\\",\\\"loggerName\\\":\\\"gfads.cin.ufpe.maverick.sandbox.App\\\",\\\"message\\\":\\\"QWE\\\",\\\"endOfBatch\\\":false,\\\"loggerFqcn\\\":\\\"org.apache.logging.slf4j.Log4jLogger\\\"}\r\r\",\"container_id\":\"37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78\",\"container_name\":\"/java\",\"source\":\"stdout\"}";
		symptom = MaverickSymptom.newMaverickSymptom(json);
		
		serialized = symptom.serialize();
		nSymptom = (IMaverickSymptom) MaverickEvent.deserialize(serialized);
		assertTrue(nSymptom.getElapsedTime(TimeUnit.MILLISECONDS) < 0);
	}
	
	public void testJsonDeserialization() {
		String json = "{\"log\":\"{\\\"timeMillis\\\":1489252411395,\\\"thread\\\":\\\"main\\\",\\\"level\\\":\\\"INFO\\\",\\\"loggerName\\\":\\\"gfads.cin.ufpe.maverick.sandbox.App\\\",\\\"message\\\":\\\"QWE\\\",\\\"endOfBatch\\\":false,\\\"loggerFqcn\\\":\\\"org.apache.logging.slf4j.Log4jLogger\\\"}\r\r\",\"container_id\":\"37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78\",\"container_name\":\"/java\",\"source\":\"stdout\"}";
		String result ="MaverickSymptom [containerId=37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78, containerName=/java, source=stdout, log={\"timeMillis\":1489252411395,\"thread\":\"main\",\"level\":\"INFO\",\"loggerName\":\"gfads.cin.ufpe.maverick.sandbox.App\",\"message\":\"QWE\",\"endOfBatch\":false,\"loggerFqcn\":\"org.apache.logging.slf4j.Log4jLogger\"}]";
		IMaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json);
		
		assertTrue(result.equals(symptom.toString()));
	}
	
	public void testGet() {
		String json = "{\"log\":\"{\\\"timeMillis\\\":1489252411395,\\\"thread\\\":\\\"main\\\",\\\"level\\\":\\\"INFO\\\",\\\"loggerName\\\":\\\"gfads.cin.ufpe.maverick.sandbox.App\\\",\\\"message\\\":\\\"{\\\\\\\"key\\\\\\\":\\\\\\\"value\\\\\\\"}\\\",\\\"endOfBatch\\\":false,\\\"loggerFqcn\\\":\\\"org.apache.logging.slf4j.Log4jLogger\\\"}\r\r\",\"container_id\":\"37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78\",\"container_name\":\"/java\",\"source\":\"stdout\"}";
		String result ="MaverickSymptom [containerId=37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78, containerName=/java, source=stdout, log={\"timeMillis\":1489252411395,\"thread\":\"main\",\"level\":\"INFO\",\"loggerName\":\"gfads.cin.ufpe.maverick.sandbox.App\",\"message\":\"QWE\",\"endOfBatch\":false,\"loggerFqcn\":\"org.apache.logging.slf4j.Log4jLogger\"}]";
		IMaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json);
		
		assertTrue("37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78".equals(symptom.get("containerId")));
		assertTrue("/java".equals(symptom.get("containername")));
		assertTrue("stdout".equals(symptom.get("SOUrCe")));
		assertTrue(1489252411395L == (Long) symptom.get("timeMillis"));
		assertTrue("value".equals(symptom.get("key")));
		assertNull(symptom.get("timemillis")); // properties into log are case sensitive
	}
	
	public void testLogAsJson() {
		String json = "{\"log\":\"{\\\"timeMillis\\\":1489252411395,\\\"thread\\\":\\\"main\\\",\\\"level\\\":\\\"INFO\\\",\\\"loggerName\\\":\\\"gfads.cin.ufpe.maverick.sandbox.App\\\",\\\"message\\\":\\\"QWE\\\",\\\"endOfBatch\\\":false,\\\"loggerFqcn\\\":\\\"org.apache.logging.slf4j.Log4jLogger\\\"}\r\r\",\"container_id\":\"37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78\",\"container_name\":\"/java\",\"source\":\"stdout\"}";
		IMaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json);
		
		assertTrue(!symptom.getLogAsMap().isEmpty());
		assertTrue((Long) symptom.getLogAsMap().get("timeMillis") == 1489252411395L);
		
	}
}

package gfads.cin.ufpe.maverick.events;

import java.util.concurrent.TimeUnit;

import gfads.cin.ufpe.maverick.events.symtoms.IMaverickSymptom;
import gfads.cin.ufpe.maverick.events.symtoms.DockerSymptom;
import junit.framework.TestCase;

public class DockerSymptomTest extends TestCase {

	public void testMaverickSymptomInstantiation() {
		DockerSymptom symptom1 = DockerSymptom.newMaverickSymptom("container_id", "container_name", "source", "log");
		byte[] serialized1 = symptom1.serialize();
		
		String json= "{\"container_id\":\"container_id\", \"container_name\":\"container_name\", \"source\":\"source\", \"log\":\"log\"}";
		DockerSymptom symptom2 = DockerSymptom.newMaverickSymptom(json);
		byte[] serialized2 = symptom2.serialize();
		
		assertEquals(DockerSymptom.deserialize(serialized1), DockerSymptom.deserialize(serialized2));
	}
	
	public void testCurlHttpFluent() {
		String json= "{\"container_id\":\"container_test\", \"container_name\":\"test_name\", \"source\":\"test_source\", \"log\":\"test_log\"}";
		DockerSymptom symptom = DockerSymptom.newMaverickSymptom(json.getBytes());
		
		byte[] serialized = symptom.serialize();
		IMaverickSymptom nSymptom = (IMaverickSymptom) MaverickEvent.deserialize(serialized);
		assertTrue(symptom.equals(nSymptom));
	}
	
	public void testSerialization() {
		String json = "{\"log\":\"{\\\"timeMillis\\\":1489252411395,\\\"thread\\\":\\\"main\\\",\\\"level\\\":\\\"INFO\\\",\\\"loggerName\\\":\\\"gfads.cin.ufpe.maverick.sandbox.App\\\",\\\"message\\\":\\\"QWE\\\",\\\"endOfBatch\\\":false,\\\"loggerFqcn\\\":\\\"org.apache.logging.slf4j.Log4jLogger\\\"}\r\r\",\"container_id\":\"37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78\",\"container_name\":\"/java\",\"source\":\"stdout\"}";
		DockerSymptom symptom = DockerSymptom.newMaverickSymptom(json);
		
		byte[] serialized = symptom.serialize();
		IMaverickSymptom nSymptom = (IMaverickSymptom) MaverickEvent.deserialize(serialized);
		
		assertTrue(symptom.equals(nSymptom));
	}
	
//	public void testElapsedTime() {
//		String json = "{\"log\":\"{\\\"timeMillis\\\":1489252411395,\\\"thread\\\":\\\"main\\\",\\\"level\\\":\\\"INFO\\\",\\\"loggerName\\\":\\\"gfads.cin.ufpe.maverick.sandbox.App\\\",\\\"message\\\":\\\"QWE\\\",\\\"endOfBatch\\\":false,\\\"loggerFqcn\\\":\\\"org.apache.logging.slf4j.Log4jLogger\\\"}\r\r\",\"container_id\":\"37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78\",\"container_name\":\"/java\",\"source\":\"stdout\"}";
//		DockerSymptom symptom = DockerSymptom.newMaverickSymptom(json);
//		
//		byte[] serialized = symptom.serialize();
//		IMaverickSymptom nSymptom = (IMaverickSymptom) MaverickEvent.deserialize(serialized);
//		assertTrue(nSymptom.getElapsedTime(TimeUnit.MILLISECONDS) >= 0);
//		
//		json = "{\"log\":\"{\\\"thread\\\":\\\"main\\\",\\\"level\\\":\\\"INFO\\\",\\\"loggerName\\\":\\\"gfads.cin.ufpe.maverick.sandbox.App\\\",\\\"message\\\":\\\"QWE\\\",\\\"endOfBatch\\\":false,\\\"loggerFqcn\\\":\\\"org.apache.logging.slf4j.Log4jLogger\\\"}\r\r\",\"container_id\":\"37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78\",\"container_name\":\"/java\",\"source\":\"stdout\"}";
//		symptom = DockerSymptom.newMaverickSymptom(json);
//		
//		serialized = symptom.serialize();
//		nSymptom = (IMaverickSymptom) MaverickEvent.deserialize(serialized);
//		assertTrue(nSymptom.getElapsedTime(TimeUnit.MILLISECONDS) < 0);
//	}
	
	public void testJsonDeserialization() {
		String json = "{\"log\":\"{\\\"timeMillis\\\":1489252411395,\\\"thread\\\":\\\"main\\\",\\\"level\\\":\\\"INFO\\\",\\\"loggerName\\\":\\\"gfads.cin.ufpe.maverick.sandbox.App\\\",\\\"message\\\":\\\"QWE\\\",\\\"endOfBatch\\\":false,\\\"loggerFqcn\\\":\\\"org.apache.logging.slf4j.Log4jLogger\\\"}\r\r\",\"container_id\":\"37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78\",\"container_name\":\"/java\",\"source\":\"stdout\"}";
		String result ="MaverickSymptom [containerId=37c987742b2fe60e7c08b9dc50d3d74ef57a72d5bccdbb70dfa41a8b68f23e78, containerName=/java, source=stdout, log={\"timeMillis\":1489252411395,\"thread\":\"main\",\"level\":\"INFO\",\"loggerName\":\"gfads.cin.ufpe.maverick.sandbox.App\",\"message\":\"QWE\",\"endOfBatch\":false,\"loggerFqcn\":\"org.apache.logging.slf4j.Log4jLogger\"}]";
		IMaverickSymptom symptom = DockerSymptom.newMaverickSymptom(json);
		
		assertTrue(result.equals(symptom.toString()));
	}
	
	
}

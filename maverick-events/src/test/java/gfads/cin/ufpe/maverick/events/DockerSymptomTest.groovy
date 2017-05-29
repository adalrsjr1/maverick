package gfads.cin.ufpe.maverick.events

import java.util.concurrent.TimeUnit

import gfads.cin.ufpe.maverick.events.symtoms.IMaverickSymptom
import gfads.cin.ufpe.maverick.events.symtoms.DockerSymptom
import junit.framework.TestCase

public class DockerSymptomTest extends TestCase {

	public void testMaverickSymptomInstantiation() {
		DockerSymptom symptom1 = DockerSymptom.newMaverickSymptom("container_id", "container_name", "source", "log")
		byte[] serialized1 = symptom1.serialize()

		String json= '{"container_id":"container_id", "container_name":"container_name", "source":"source", "log":"log"}'
		DockerSymptom symptom2 = DockerSymptom.newMaverickSymptom(json)
		byte[] serialized2 = symptom2.serialize()

		assertEquals(DockerSymptom.deserialize(serialized1), DockerSymptom.deserialize(serialized2))
	}

	public void testCurlHttpFluent() {
		String json= '{"container_id":"container_test", "container_name":"test_name", "source":"test_source", "log":"test_log"}'
		DockerSymptom symptom = DockerSymptom.newMaverickSymptom(json.getBytes())

		byte[] serialized = symptom.serialize()
		IMaverickSymptom nSymptom = (IMaverickSymptom) MaverickEvent.deserialize(serialized)
		assertTrue(symptom.equals(nSymptom))
	}

	public void testSerialization() {
		String json= '{"container_id":"container_id", "container_name":"container_name", "source":"source", "log":"log"}'
		DockerSymptom symptom = DockerSymptom.newMaverickSymptom(json)

		byte[] serialized = symptom.serialize()
		IMaverickSymptom nSymptom = (IMaverickSymptom) MaverickEvent.deserialize(serialized)

		assertTrue(symptom.equals(nSymptom))
	}

	public void testJsonDeserialization() {
		String json= '{"container_id":"container_id", "container_name":"container_name", "source":"source", "log":"log"}'
		String result ="DockerSymptom{containerId=container_id, containerName=container_name, source=source}"
		IMaverickSymptom symptom = DockerSymptom.newMaverickSymptom(json)

		assert result == symptom.toString()
	}
}

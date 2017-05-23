package gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse;

import static org.junit.Assert.*

import org.junit.Test

import gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse.events.SockShopLog
import gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse.events.SockShopSymptom
import gfads.cin.ufpe.maverick.events.MaverickSymptom

public class SockShopSymptomTest {
	
	@Test
	public void testSockShopSymptomInstantiation() {
		String json= '{"container_id":"container_id", "container_name":"container_name", "source":"source", "log":"2017-05-19 20:49:13.699 INFO [orders,,,] 6 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 80 (http)"}'
		MaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json);
		SockShopSymptom sockShopSymptom = SockShopSymptom.newSockShopSymptom(symptom)
		
		assert sockShopSymptom != null
	
		assert sockShopSymptom.containerId == "container_id"
		assert sockShopSymptom.containerName == "container_name"
		assert sockShopSymptom.source == "source"
		assert sockShopSymptom.log != null
	}
	
	@Test
	public void testSockShopSymptomLog() {
		String json= '{"container_id":"container_id", "container_name":"container_name", "source":"source", "log":"2017-05-19 20:49:13.699 INFO [orders,,,] 6 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 80 (http)"}'
		MaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json);
		SockShopSymptom sockShopSymptom = SockShopSymptom.newSockShopSymptom(symptom)
		
		SockShopLog log = sockShopSymptom.getLog() as SockShopLog
		assert log.className == "s.b.c.e.t.TomcatEmbeddedServletContainer"
		assert log.logLevel == "INFO"
		assert log.text == "2017-05-19 20:49:13.699 INFO [orders,,,] 6 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 80 (http)"
		assert log.timestamp == 1495183753699
	}
	
	@Test
	public void testSockShopSymptomGet() {
		String json= '{"container_id":"container_id", "container_name":"container_name", "source":"source", "log":"2017-05-19 20:49:13.699 INFO [orders,,,] 6 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 80 (http)"}'
		MaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json);
		SockShopSymptom sockShopSymptom = SockShopSymptom.newSockShopSymptom(symptom)
		
		assert sockShopSymptom.get("className") == "s.b.c.e.t.TomcatEmbeddedServletContainer"
		assert sockShopSymptom.get("logLevel") == "INFO"
		assert sockShopSymptom.get("text") == "2017-05-19 20:49:13.699 INFO [orders,,,] 6 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 80 (http)"
		assert sockShopSymptom.get("timestamp") == 1495183753699
		assert sockShopSymptom.get("containerId") == "container_id"
		assert sockShopSymptom.get("containerName") == "container_name"
		assert sockShopSymptom.get("source") == "source"
	}
}

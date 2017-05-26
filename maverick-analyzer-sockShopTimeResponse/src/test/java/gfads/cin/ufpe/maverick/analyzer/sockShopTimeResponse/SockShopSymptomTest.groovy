package gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse;

import static org.junit.Assert.*

import org.codehaus.groovy.runtime.metaclass.MethodMetaProperty.GetMethodMetaProperty
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
	
	@Test
	public void testSockShopSymptom2() {
		String json = '{"log":"ts=2017-05-25T21:50:22Z caller=logging.go:62 method=Get id=510a0d7e-8e83-4193-b483-e27e09ddc34d sock=510a0d7e-8e83-4193-b483-e27e09ddc34d err=null took=1.30887ms","container_id":"b168799de480b76e35a9cede46baea697386bff49474b75dd1ee8b3d125a0f7d","container_name":"/dockercompose_catalogue_1","source":"stderr"}'
		MaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json);
		SockShopSymptom sockShopSymptom = SockShopSymptom.newSockShopSymptom(symptom)
		
		assert sockShopSymptom.getLog().getText() == "ts=2017-05-25T21:50:22Z caller=logging.go:62 method=Get id=510a0d7e-8e83-4193-b483-e27e09ddc34d sock=510a0d7e-8e83-4193-b483-e27e09ddc34d err=null took=1.30887ms"
	}
	
	@Test
	public void testSockShopSymptom3() {
		String json = '{"log":"GET /cart 200 37252.269 ms - -","container_id":"b168799de480b76e35a9cede46baea697386bff49474b75dd1ee8b3d125a0f7d","container_name":"/dockercompose_catalogue_1","source":"stderr"}'
		MaverickSymptom symptom = MaverickSymptom.newMaverickSymptom(json);
		SockShopSymptom sockShopSymptom = SockShopSymptom.newSockShopSymptom(symptom)
		
		assert sockShopSymptom.getLog() != null
		assert sockShopSymptom.getLog().getHttpLog() != null
		assert sockShopSymptom.getLog().getHttpLog().getMethod() == "GET"
		assert sockShopSymptom.get("method") == "GET"		
	}
}

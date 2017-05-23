package gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse;

import static org.junit.Assert.*

import org.junit.Test

import gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse.events.SockShopLog

class SockShopLogTest {
	final static SockShopLog log = new SockShopLog()
	@Test
	public void testSanitizeToken() {
		assert log.sanitizeColor("GET") == "GET"
		assert log.sanitizeColor("\u001b[0mGET") == "GET"
		assert log.sanitizeColor("200") == "200"
		assert log.sanitizeColor("\u001b[32m200") == "200"
		assert log.sanitizeColor("5.693") == "5.693"
		assert log.sanitizeColor("\u001b[0m5.693") == "5.693"
	}

	@Test
	public void testSplitString() {
		assert log.splitLogMessage("2017-05-19 20:49:13.699  ") == ["2017-05-19","20:49:13.699"]
		assert log.splitLogMessage("2017-05-19 20:49:13.699  ") == ["2017-05-19","20:49:13.699"]
		assert log.splitLogMessage("2017-05-19a b20:49:13.699") == ["2017-05-19a", "b20:49:13.699"]
		assert log.splitLogMessage("2017-05-19a b20:49:13.699  ") == ["2017-05-19a", "b20:49:13.699"]
	}
	
	@Test
	public void testDateStrToLong() {
		assert log.dateStrToLong("2017-05-19 20:49:13.699  ") == 1495183753699
		assert log.dateStrToLong("2017-05-19 20:49:13.699") == 1495183753699
	}
	
	@Test
	public void testStringDateFormat() {
		assert true == log.isDate("2017-05-19")
		assert true == log.isDate("2017-05-19 ")
		assert true == log.isDate("20:49:13.699")
		assert false == log.isDate("20:49:13.")
		assert true == log.isDate(" 20:49:13.699  ")
		assert true == log.isDate("2017-05-19 20:49:13.699")
		assert true == log.isDate("2017-05-19 20:49:13.699   ")
		assert true == log.isDate("2017-05-19 20:49:13.69")
		assert true == log.isDate("2017-05-19 20:49:13.6")
		assert false == log.isDate("2017-05-19 20:49:13.")
		assert false == log.isDate("2017-05-19 20:49:13.acds")
		assert false == log.isDate("2017-05-19 20:49:13.3asd")
	}
	
	@Test
	public void testIsLogLevel() {
		assert true == log.isLogLevel("  INFO ")
		assert true == log.isLogLevel("  DEBUG   ")
		assert true == log.isLogLevel("  WARN   ")
		assert true == log.isLogLevel("  ERROR ")
		assert true == log.isLogLevel("  FATAL ")
		assert true == log.isLogLevel("  TRACE ")
		assert false == log.isLogLevel("  WARNING ")
		assert true == log.isLogLevel("INFO")
	}
	
	@Test
	public void testIsJson() {
		assert false == log.isJson(" o.s.j.e.a.AnnotationMBeanExporter        : Located managed bean 'refreshEndpoint':")
	}
	
	@Test
	public void testIsClassName() {
		assert true == log.isClassName("o.s.d.r.w.RepositoryRestHandlerMapping")
		assert true == log.isClassName("o.s.d.r.RepositoryRestHandlerMapping")
		assert true == log.isClassName("o.RepositoryRestHandlerMapping")
		assert false == log.isClassName("RepositoryRestHandlerMapping")
	}
	
	@Test 
	public void testCreateEmptySockShopSymptom() {
		SockShopLog l0 = new SockShopLog("")
		assert l0.text == ""
		assert l0.className == ""
		assert l0.timestamp == -1L
		assert l0.logLevel == ""
	}
	
	@Test
	public void testCreateOnlyTextSockShopSymptom() {
		SockShopLog l1 = new SockShopLog("Request received: /cart, undefined")
		assert l1.text == "Request received: /cart, undefined"
		assert l1.className == ""
		assert l1.timestamp == -1L
		assert l1.logLevel == ""
	}
	
	@Test
	public void testCreateSockShopSymptom() {
		SockShopLog l2 = new SockShopLog("2017-05-19 20:49:13.699 INFO [orders,,,] 6 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 80 (http)")
		assert l2.text == "2017-05-19 20:49:13.699 INFO [orders,,,] 6 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 80 (http)"
		assert l2.timestamp == 1495183753699
		assert l2.logLevel == "INFO"
		assert l2.className == "s.b.c.e.t.TomcatEmbeddedServletContainer"
	}
	
	@Test
	public void testLogAsMap() {
		SockShopLog l2 = new SockShopLog("2017-05-19 20:49:13.699 INFO [orders,,,] 6 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 80 (http)")
		
		assert l2.getLogAsMap() == [className: "s.b.c.e.t.TomcatEmbeddedServletContainer",
									timestamp: 1495183753699,
									logLevel: "INFO",
									text: "2017-05-19 20:49:13.699 INFO [orders,,,] 6 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 80 (http)"]		
	}
	
	@Test
	public void testGet() {
		SockShopLog l2 = new SockShopLog("2017-05-19 20:49:13.699 INFO [orders,,,] 6 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 80 (http)")
		assert l2.get("timestamp") == 1495183753699
		assert l2.get("className") == "s.b.c.e.t.TomcatEmbeddedServletContainer"
		assert l2.get("logLevel") == "INFO"
		assert l2.get("text") == "2017-05-19 20:49:13.699 INFO [orders,,,] 6 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 80 (http)"
	}
}

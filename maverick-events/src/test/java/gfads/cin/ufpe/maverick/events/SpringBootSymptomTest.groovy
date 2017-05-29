package gfads.cin.ufpe.maverick.events;

import gfads.cin.ufpe.maverick.events.symtoms.DockerSymptom
import gfads.cin.ufpe.maverick.events.symtoms.SpringBootSymptom
import groovy.util.GroovyTestCase
import junit.framework.Test

//http://docs.spring.io/spring-boot/docs/1.2.1.RELEASE/reference/htmlsingle/#boot-features-logging-format
class SpringBootSymptomTest extends GroovyTestCase {
	static final SpringBootSymptom symptom = new SpringBootSymptom("")
	
	void testDateStrToLong() {
		assert 1495969743320 == symptom.dateStrToLong("2017-05-28 23:09:03.320")
		assert -1 == symptom.dateStrToLong("2017-05-28 23:09:03.")
		assert -1 != symptom.dateStrToLong("2017-05-28 23:09:03.3")
	}
	
	void testSpringBootSymptomInstance() {
		SpringBootSymptom s = new SpringBootSymptom("2017-05-28 23:09:03.320  INFO [shipping,,,] 7 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Bean with name 'refreshEndpoint' has been autodetected for JMX exposure") 
		
		assert s.logLevel == "INFO"
		assert s.timestamp == 1495969743320
		assert s.processId == 7
		assert s.serviceName == "shipping,,,"
		assert s.threadName == "main"
		assert s.loggerName == "o.s.j.e.a.AnnotationMBeanExporter"
		assert s.springBootLogMessage == "Bean with name 'refreshEndpoint' has been autodetected for JMX exposure"
	}
	
	void testNonSpringBootSymptom() {
		SpringBootSymptom s = new SpringBootSymptom("2017-05-28   INFO [shipping,,,           main] o.s.j.e.a.AnnotationMBeanExn with name 'refreshEndpoint' has been autodetected for JMX exposure")
		
		assertEquals(s.getEmpty(), s)
	}
	
	void testDockerBootSymptom() {
		String json = '{"container_name":"/dockercompose_shipping_1","source":"stdout","log":"2017-05-29 19:05:39.075  INFO [shipping,,,] 6 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 80 (http)","container_id":"ba74f84b113fec32eb449a93a0e45016bc8ef38ff8b18029d08c66e1fb58ff79"}'
		DockerSymptom ds = DockerSymptom.newMaverickSymptom(json)
		SpringBootSymptom ss = new SpringBootSymptom(ds)
		
		assert ss != null
		assert ss.symptom == ds
		assert ss.containerId == "ba74f84b113fec32eb449a93a0e45016bc8ef38ff8b18029d08c66e1fb58ff79"
		assert ss.timestamp == 1496041539075 
	}
	
}

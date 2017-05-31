package gfads.cin.ufpe.maverick.analyzer.sockshop.responsetime.events;

import gfads.cin.ufpe.maverick.events.symtoms.DockerSymptom
import gfads.cin.ufpe.maverick.events.symtoms.sockshop.HttpSockShopSymptom
import junit.framework.TestCase

class HttpSockShoptSymptomTest extends TestCase {

	static final HttpSockShopSymptom symptom = new HttpSockShopSymptom()
	// GET /catalogue/510a0d7e-8e83-4193-b483-e27e09ddc34d 200 2.386 ms - -
	// GET /catalogue?size=3 200 11.949 ms - -
	// POST /cart 201 47.787 ms - -
	// GET /catalogue?sort=id&size=3&tags=sport 200 2.425 ms - -
	// GET /catalogue/images/colourful_socks.jpg 200 1.365 ms - 486192
	public void testHttpSockShoptParser1() {
		HttpSockShopSymptom s = new HttpSockShopSymptom("GET /catalogue/510a0d7e-8e83-4193-b483-e27e09ddc34d 200 2.386 ms - -")
		assert s.method == "GET"
		assert s.path == "/catalogue/510a0d7e-8e83-4193-b483-e27e09ddc34d"
		assert s.response == 200
		assert s.responseTime == 2.386f
		assert s.responseTimeUnit == "ms"
		assert s.params == [:]
	}
	
	public void testHttpSockShoptParser2() {
		HttpSockShopSymptom s = new HttpSockShopSymptom("GET /catalogue?size=3 200 11.949 ms - -")
		assert s.method == "GET"
		assert s.path == "/catalogue"
		assert s.params == [size:"3"]
		assert s.response == 200
		assert s.responseTime == 11.949f
		assert s.responseTimeUnit == "ms"
	}
	
	public void testHttpSockShoptParser3() {
		HttpSockShopSymptom s = new HttpSockShopSymptom("POST /cart 201 47.787 ms - -")
		assert s.method == "POST"
		assert s.path == "/cart"
		assert s.response == 201
		assert s.responseTime == 47.787f
		assert s.responseTimeUnit == "ms"
		assert s.params == [:]
	}
	
	public void testHttpSockShoptParser4() {
		HttpSockShopSymptom s = new HttpSockShopSymptom("GET /catalogue?sort=id&size=3&tags=sport 200 2.425 ms - -")
		assert s.method == "GET"
		assert s.path == "/catalogue"
		assert s.params == [sort:"id",size:"3",tags:"sport"]
		assert s.response == 200
		assert s.responseTime == 2.425f
		assert s.responseTimeUnit == "ms"
	}
	
	public void testHttpSockShoptParser5() {
		HttpSockShopSymptom s = new HttpSockShopSymptom("GET /catalogue/images/colourful_socks.jpg 200 1.365 ms - 486192")
		assert s.method == "GET"
		assert s.path == "/catalogue/images/colourful_socks.jpg"
		assert s.response == 200
		assert s.params == [:]
		assert s.responseTime == 1.365f
		assert s.responseTimeUnit == "ms"
	}
	
	public void testParseParams() {
		assert symptom.parseParams("?size=3") == [size:"3"]
		assert symptom.parseParams("?sort=id&size=3&tags=sport") == [sort:"id", size:"3", tags:"sport"]
	}
	
	public void testInstance1() {
		String json = '{"log":"\u001b[0mGET /catalogue?sort=id&size=3&tags=sport \u001b[32m200 \u001b[0m2.425 ms - -\u001b[0m","container_id":"754656f9793dbf60c6dcebab925df4171e1a505a089a354685a9f1ba7ffde74c","container_name":"/dockercompose_front-end_1","source":"stdout"}'
		DockerSymptom ds = DockerSymptom.newMaverickSymptom(json)
		assert ds != null
		HttpSockShopSymptom s = HttpSockShopSymptom.newHttpSockShopSymptom(ds)
		assert s != null
		assert s.method == "GET"
		assert s.path == "/catalogue"
		assert s.params == [sort:"id",size:"3",tags:"sport"]
		assert s.response == 200
		assert s.responseTime == 2.425f
		assert s.responseTimeUnit == "ms"
	}
	
	public void testInstance2() {
		String str = ' {"container_id":"754656f9793dbf60c6dcebab925df4171e1a505a089a354685a9f1ba7ffde74c","container_name":"/dockercompose_front-end_1","source":"stdout","log":"\u001b[0mGET /catalogue/510a0d7e-8e83-4193-b483-e27e09ddc34d \u001b[32m200 \u001b[0m2.165 ms - -\u001b[0m"}'
		DockerSymptom ds = DockerSymptom.newMaverickSymptom(str)
		assert ds != null
		HttpSockShopSymptom s = new HttpSockShopSymptom(ds)
		assert s != null
		assert s.method == "GET"
		assert s.path == "/catalogue/510a0d7e-8e83-4193-b483-e27e09ddc34d"
		assert s.params == [:]
		assert s.response == 200
		assert s.responseTime == 2.165f
		assert s.responseTimeUnit == "ms"
	}
}

package gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse;

import static org.junit.Assert.*

import java.util.Currency.CurrencyNameGetter

import org.junit.Test

import gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse.events.HttpLog

class HttpSockShopSymptom {
	//GET /catalogue/images/cross_2.jpeg 200 1.151 ms - 33687"
	//GET /catalogue?sort=id&size=3&tags=sport 200 2.572 ms
	HttpLog l = new HttpLog("")
	
	@Test
	public void testFull() {
		HttpLog l = new HttpLog("GET /catalogue/images/cross_2.jpeg 200 1.151 ms - 33687")
		
		assert l.method == "GET"
		assert l.path == "/catalogue/images/cross_2.jpeg"
		assert l.responseCode == 200
		assert l.responseTime == 1.151f
		assert l.params == [:]
	}
	
	@Test
	public void testFull2() {
		HttpLog l = new HttpLog("GET /catalogue?sort=id&size=3&tags=sport 200 2.572 ms")
		
		assert l.method == "GET"
		assert l.path == "/catalogue"
		assert l.responseCode == 200
		assert l.responseTime == 2.572f
		assert l.params == [sort:"id", size:"3", tags:"sport"]
	}
	
	@Test
	public void testGet() {
		HttpLog l = new HttpLog("GET /catalogue?sort=id&size=3&tags=sport 200 2.572 ms")
		
		assert l.get("method") == "GET"
		assert l.get("path") == "/catalogue"
		assert l.get("responseCode") == 200
		assert l.get("") == null
		assert l.get("responseTime") == 2.572f
		assert l.get("params") == [sort:"id", size:"3", tags:"sport"]
	}
		
	@Test
	public void testSplit() {
		assert l.split("GET /catalogue/images/cross_2.jpeg 200 1.151 ms - 33687") == ["GET", "/catalogue/images/cross_2.jpeg", "200", "1.151", "ms", "-", "33687"]
		assert l.split("GET /catalogue?sort=id&size=3&tags=sport 200 2.572 ms") == ["GET", "/catalogue?sort=id&size=3&tags=sport", "200", "2.572", "ms"]
	}
	
	@Test
	public void testHttpLogInstance() {
		assert l != null
	}
	
	@Test
	public void testHttpMethod() {
		assert l.isMethod("GET ") == true
	}
	
	@Test
	public void testHttpResponse() {
		assert l.isResponseCode(" 200 ") == true
	}
	
	@Test
	public void testHttpPath() {
		assert l.isPath(" /catalogue") == true
		assert l.isPath(" /catalogue?sort=id&size=3&tags=sport") == true
		assert l.isPath("/catalogue/images/cross_2.jpeg ") == true
	}
	
	@Test
	public void testHttpParameters() {
		assert l.isParams("?sort=id&size=3&tags=sport ") == true
		assert l.isParams("sort=id&size=3&tags=sport ") == true
	}
	
	@Test
	public void testTimeResponse() {
		assert l.isResponseTime(" 2.572 ") == true
		assert l.isResponseTime("1.151  ") == true
		assert l.isResponseTime(" 1.151 ") == true
		assert l.isResponseTime(" 37252.269 ") == true
		
	}
	
	
	

}

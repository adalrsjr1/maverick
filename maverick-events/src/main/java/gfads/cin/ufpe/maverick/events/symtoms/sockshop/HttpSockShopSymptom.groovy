package gfads.cin.ufpe.maverick.events.symtoms.sockshop

import java.util.regex.Matcher

import com.google.common.base.MoreObjects

import gfads.cin.ufpe.maverick.events.MaverickEvent
import gfads.cin.ufpe.maverick.events.symtoms.IMaverickSymptom
import gfads.cin.ufpe.maverick.events.symtoms.sockshop.HttpSockShopSymptom
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class HttpSockShopSymptom extends MaverickEvent implements IMaverickSymptom {

	public static final HttpSockShopSymptom EMPTY_HTTP_SOCK_SHOP_SYMPTOM = new HttpSockShopSymptom() 
	
	private IMaverickSymptom symptom = EMPTY_HTTP_SOCK_SHOP_SYMPTOM
	private String method = ""
	private String path = ""
	private Map params = [:]
	private int response = -1
	private float responseTime = -1f
	private String responseTimeUnit = ""
	private HttpSockShopSymptom() { }

	public HttpSockShopSymptom (IMaverickSymptom symptom) {
		this.symptom = symptom
		httpSockShopParser(this.symptom.getLogMessage())
	}
	
	public HttpSockShopSymptom(String log) {
		httpSockShopParser(log)
	}
	
	public static HttpSockShopSymptom newHttpSockShopSymptom(IMaverickSymptom symptom) {
		return new HttpSockShopSymptom(symptom)
	}
	
	private void httpSockShopParser(String log) {
		Matcher matcher = (log =~ /(?<method>GET|POST|PUT|DELETE)\s+(?<path>[a-zA-Z\/_0-9\.-]*)(?<params>\?\S+)*\s(?<response>[0-9]{3})\s(?<responsetime>[0-9\.]+)\s(?<timeunit>\S{2})(.)*/)

		if(!matcher.find()) {
			return
		}
		
		method = matcher.group("method")
		path = matcher.group("path")
		response = Integer.parseInt(matcher.group("response"))
		responseTime = Float.parseFloat(matcher.group("responsetime"))
		responseTimeUnit = matcher.group("timeunit")
		
		def paramsRecognized = matcher.group("params")
		if(paramsRecognized) {
			params = parseParams(paramsRecognized)
		}
	}
	
	private Map parseParams(String string) {
		string = string.substring(1)
		
		return string.split("&").inject([:]) {result, tokens ->
			String[] aux = tokens.split("=")
			result[(aux[0])] = (aux[1])
			result 
		}
	}
	
	@Override
	public String getContainerId() {
		return symptom.getContainerId()
	}

	@Override
	public String getContainerName() {
		return symptom.getContainerName()
	}

	@Override
	public String getSource() {
		return symptom.getSource()
	}

	@Override
	public Object getLogMessage() {
		return symptom.getLogMessage()
	}

	@Override
	public IMaverickSymptom getEmpty() {
		return EMPTY_HTTP_SOCK_SHOP_SYMPTOM
	}

	public IMaverickSymptom getSymptom() {
		return symptom;
	}

	public String getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public Map getParams() {
		return params;
	}

	public int getResponse() {
		return response;
	}

	public float getResponseTime() {
		return responseTime;
	}

	public String getResponseTimeUnit() {
		return responseTimeUnit;
	}
	
	public IMaverickSymptom empty() {
		return EMPTY_HTTP_SOCK_SHOP_SYMPTOM
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
		                  .add("symptom", symptom)
						  .add("method", method)
						  .add("path", path)
						  .add("params", params)
						  .add("response", response)
						  .add("responseTime", responseTime)
						  .add("responseTimeUnit", responseTimeUnit)
						  .toString()
		
	}
}

package gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse.events

import com.fasterxml.jackson.annotation.JsonProperty
import gfads.cin.ufpe.maverick.events.Log
import java.lang.reflect.Method

class HttpLog implements Log {
	public static final HttpLog EMPTY_HTTP_LOG = new HttpLog("")
	
	@JsonProperty("method")
	private String method = ""
	@JsonProperty("responseCode")
	private int responseCode = -1
	@JsonProperty("path")
	private String path = ""
	@JsonProperty("params")
	private Map params = [:]
	@JsonProperty("responseTime")
	private float responseTime = -1f
	
	public HttpLog(String log) {
		split(log).each { String token ->
			if(isMethod(token) && method == "") {
				this.method = token;
			}
			else if(isPath(token) && path == "") {
				def httpParams = token.split(/\?/)
				this.path = httpParams[0]
				if(httpParams.size() > 1 && isParams(httpParams[1]) && params == [:]) {
					def paramsStr = httpParams[1].split("&")
					
					this.params = paramsStr.inject([:]) {result, value ->
						def aux = value.split("=")
						result[aux[0]] = aux[1]
						result
					}
				}
			}
			else if(isResponseCode(token) && responseCode == -1) {
				this.responseCode = Integer.parseInt(token)
			}
			else if(isResponseTime(token) && responseTime == -1f) {
				this.responseTime = Float.parseFloat(token)
			}
		}
	}

	private def split(String log) {
		log.split(" ")
	}
		
	private isMethod(String token) {
		token.matches(/(GET|POST|PUT|DELETE)\s*/)
	}
	
	private isResponseCode(String token) {
		token.matches(/\s*[0-9]{3}\s*/)
	}
	
	private isPath(String token) {
		token.matches(/\s*[\/a-z_0-9-\.?=&]+\s*/)
	}
	
	private isParams(String token) {
		token.matches(/\?*[\/a-z_0-9-\.=&]+\s*/)
	}
	
	private isResponseTime(String token) {
		token.matches(/\s*([0-9])+\.[0-9]{3}\s*/)
	}

	public String getMethod() {
		return method;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getPath() {
		return path;
	}

	public Map getParams() {
		return params;
	}

	public float getResponseTime() {
		return responseTime;
	}
	
	@Override
	public Object get(String property) {
		if(property == "") return null
		def methods = this.getClass().getMethods()
		Method method = methods.find { m ->
			m.getName().equalsIgnoreCase("get"+property)
		}
		
		if(Objects.nonNull(method)) {
			try {
				return method.invoke(this, null)
			} catch (Exception e) {
				e.printStackTrace()
			}
		}
		
		return null
	}
	
	@Override
	public String toString() {
		return "HttpLog [method=" + method + ", responseCode=" + responseCode + ", path=" + path + ", params=" + params	+ ", responseTime=" + responseTime + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(method, responseCode, params, responseTime, path);
	}

	@Override
	public boolean equals(Object o) {
		if(o == this) return true
		if(! (o instanceof SockShopLog)) return false
		
		HttpLog httpLog = (HttpLog) o
		
		Objects.equals(method, httpLog.method) &&
		Objects.equals(responseCode, httpLog.responseCode) &&
		Objects.equals(params, httpLog.params) &&
		Objects.equals(responseTime, httpLog.responseTime)
		Objects.equals(path, httpLog.path)
	}
}

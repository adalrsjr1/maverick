package gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse.events

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Map
import java.util.Objects
import java.util.concurrent.ConcurrentHashMap.MapEntry

import com.google.common.base.Strings 
import gfads.cin.ufpe.maverick.events.Log
import gfads.cin.ufpe.maverick.events.MaverickSymptom
import groovy.json.JsonException
import groovy.json.JsonSlurper

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper

class SockShopLog implements Log {
	public static final EMPTY_SOCK_SHOP_LOG = new SockShopLog("")
	
	@JsonProperty("timestamp")
	private long timestamp = -1L
	@JsonProperty("logLevel")
	private String logLevel = ""
	@JsonProperty("className")
	private String className = ""
	@JsonProperty("httpLog")
	private HttpLog httpLog = HttpLog.EMPTY_HTTP_LOG
	
	@JsonProperty("text")
	private String text = ""
	
	public SockShopLog(String text) {
		this.text = sanitizeColor(text)
		if(isHttpRequest(this.text)) {
			httpLog = new HttpLog(this.text)
		}
		splitLogMessage(this.text)
		
	}
	
	private def splitLogMessage(String text) {
		if(Strings.isNullOrEmpty(text)) {
			return
		}
		
		int count = 1
		String aux = ""
		def result = text.split(/\s+/).each { token ->
			String s = token
			if(isDate(s)) {
				aux += s + " "
			}
			else if(isLogLevel(s)) {
				logLevel = s
			}
			else if(isClassName(s)) {
				className = s
			}
			
		}
		timestamp = dateStrToLong(aux)
		return result
	}
	
	private String sanitizeColor(String token) {
		token.replaceAll(/(\u001b\[[0-9]+m){1}/,"")
	}
	
	private boolean isDate(String token) {
		def date = token ==~ /([0-9]{4}(-[0-9]{2}){2})+\s*/
		def time = token ==~ /\s*([0-9]{2}(:[0-9]{2}){2}(\.[0-9]+)?)+\s*/
		def total = token ==~ /[0-9]{4}(-[0-9]{2}){2} [0-9]{2}(:[0-9]{2}){2}\.[0-9]+\s*/
		return date || time || total
	}
	
	private boolean isLogLevel(String token) {
		token.matches(/\s*(ERROR|INFO|DEBUG|WARN|FATAL|TRACE)\s*/)
	}
	
	private boolean isJson(String token) {
		JsonSlurper json = new JsonSlurper()
		try {
			json.parseText(token) != null
		}
		catch(JsonException e) {
			return false
		}
	}
	
	private boolean isClassName(String token) {
		token.matches(/\s*(\D\.)+\D+\s*/)
	}
	
	private boolean isHttpRequest(String token) {
		token.matches(/(GET|POST|PUT|DELETE)\s[\/a-z?_0-9-\.=&]+\s[0-9]{3}.+/)
	}
	
	private Long dateStrToLong(String token) {
		if(Strings.isNullOrEmpty(token)) {
			return -1L
		}
		DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
		
		DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
		
		String dateStr = localFormat.format(utcFormat.parse(token))
		localFormat.parse(dateStr).getTime()
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public String getClassName() {
		return className;
	}

	public String getText() {
		return text;
	}
	
	public HttpLog getHttpLog() {
		return httpLog
	}

	public Map getLogAsMap() {
		this.class.declaredFields.findAll { !it.synthetic }.inject([:]) { result, entry ->
			if(entry.name != "EMPTY_SOCK_SHOP_LOG") {
				result[ (entry.name) ] = this."$entry.name" 
			}
			result
	    }
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
		
		return httpLog.get(property)
	}

	@Override
	public String toString() {
		return "SockShopLog [timestamp=" + timestamp + ", logLevel=" + logLevel + ", className=" + className + ", httpLog=" + httpLog + ", text=" + text + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(timestamp, className, logLevel, text, http);
	}

	@Override
	public boolean equals(Object o) {
		if(o == this) return true
		if(! (o instanceof SockShopLog)) return false
		
		SockShopLog sockShopLog = (SockShopLog) o
		
		Objects.equals(timestamp, sockShopLog.timestamp) &&
	    Objects.equals(className, sockShopLog.className) &&
		Objects.equals(logLevel, sockShopLog.logLevel) &&
		Objects.equals(text, sockShopLog.text) &&
		Objects.equals(httpLog, sockShopLog.httpLog)
	}
}

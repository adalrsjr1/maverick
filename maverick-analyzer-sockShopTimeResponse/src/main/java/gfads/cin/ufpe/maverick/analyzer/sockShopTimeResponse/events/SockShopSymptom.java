package gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse.events;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonProperty;

import gfads.cin.ufpe.maverick.events.IMaverickSymptom;
import gfads.cin.ufpe.maverick.events.Log;
import gfads.cin.ufpe.maverick.events.MaverickEvent;
import gfads.cin.ufpe.maverick.events.MaverickSymptom;

public class SockShopSymptom extends MaverickEvent implements IMaverickSymptom {

	private static final long serialVersionUID = 2270760031304402236L;
	
	@JsonProperty("symptom")
	private IMaverickSymptom symptom;
	@JsonProperty("log")
	private SockShopLog log;
	
	private SockShopSymptom() { }

	private SockShopSymptom(IMaverickSymptom symptom) {
		this.symptom = symptom;
		String logMsg = this.symptom.getLog().toString();
		log = new SockShopLog(logMsg);
	}
	
	public static SockShopSymptom newSockShopSymptom(IMaverickSymptom symptom) {
		return new SockShopSymptom(symptom);
	}

	@Override
	public long getElapsedTime(TimeUnit timeUnit) {
		try {
			return timeUnit.convert(System.currentTimeMillis()-(Long) get("timeStamp"), TimeUnit.MILLISECONDS);
		}
		catch(Exception e) {
			return -1L;
		}
	}

	@Override
	public String getContainerId() {
		return symptom.getContainerId();
	}

	@Override
	public String getContainerName() {
		return symptom.getContainerName();
	}

	@Override
	public String getSource() {
		return symptom.getSource();
	}

	@Override
	public Log getLog() {
		return log;
	}

	@Override
	public Map<String, Object> getLogAsMap() {
		return log.getLogAsMap();
	}

	@Override
	public String getLogMessage() {
		return symptom.getLog().toString();
	}

	@Override
	public Object get(String property) {
		return IMaverickSymptom.super.get(property);
	}

	@Override
	public String toString() {
		return "SockShopSymptom [containerId=" + getContainerId() + ", containerName=" + getContainerName()
				+ ", source=" + getSource() + ", log=" + getLog() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(symptom, log);
	}

	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(! (o instanceof MaverickSymptom)) return false;
		
		SockShopSymptom sockShopSymptom = (SockShopSymptom) o;
		
		return Objects.equals(symptom, sockShopSymptom.symptom)
			   && Objects.equals(log, sockShopSymptom.log);
	}

}

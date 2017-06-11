package gfads.cin.ufpe.maverick.events.symtoms

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.MoreObjects
import com.google.common.base.Strings

import gfads.cin.ufpe.maverick.events.MaverickEvent
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

// http://docs.spring.io/spring-boot/docs/1.2.1.RELEASE/reference/htmlsingle/#boot-features-logging-format
@EqualsAndHashCode
class SpringBootSymptom extends MaverickEvent implements IMaverickSymptom {
	private static final Logger LOG = LoggerFactory.getLogger(SpringBootSymptom.class)

	public static final SpringBootSymptom EMPTY_SPRINGBOOT_SYMPTOM = new SpringBootSymptom("")

	@JsonProperty("symptom")
	private IMaverickSymptom symptom = EMPTY_SPRINGBOOT_SYMPTOM
	@JsonProperty("text")
	private String text = ""
	@JsonProperty("timestamp")
	private long timestamp = -1L
	@JsonProperty("logLevel")
	private String logLevel = ""
	@JsonProperty("serviceName")
	private String serviceName = ""
	@JsonProperty("processId")
	private int processId = -1
	@JsonProperty("threadName")
	private String threadName = ""
	@JsonProperty("loggerName")
	private String loggerName = ""
	@JsonProperty("springBootLogMessage")
	private String springBootLogMessage = ""
	
	public SpringBootSymptom(IMaverickSymptom symptom) {
		this.symptom = symptom
		springBootLogParser(this.symptom.getLogMessage())
	}

	public SpringBootSymptom(String string) {
		this.text = string
		springBootLogParser(this.text)
	}

	public SpringBootSymptom() { }
	
	public static SpringBootSymptom newSpringBootSymtom(IMaverickSymptom symptom) {
		return new SpringBootSymptom(symptom)
	}
	
	private void springBootLogParser(String log) {
		Matcher matcher = (log =~ /([0-9-:\s\.]{23})\s+(INFO|TRACE|DEBUG|WARN|ERROR)\s\[(\S+)\]\s(\d+)\s-{3}\s\[\s*(\S+)\]\s*(\S+)\s*:\s*(.*)/)

		if(!matcher.find()) {
			return
		}
		
		text = log

		timestamp = dateStrToLong(matcher.group(1))
		logLevel = matcher.group(2)
		serviceName = matcher.group(3)
		processId = Integer.parseInt(matcher.group(4))
		threadName = matcher.group(5)
		loggerName = matcher.group(6)
		springBootLogMessage = matcher.group(7)
	}

	private Long dateStrToLong(String token) {
		if(Strings.isNullOrEmpty(token)) {
			return -1L
		}

		try {
			DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"))

			DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")

			String dateStr = localFormat.format(utcFormat.parse(token))
			return localFormat.parse(dateStr).getTime()
		}
		catch(ParseException e) {
			LOG.warn(e.getMessage())
			return -1L
		}
	}

	private String sanitizeColor(String token) {
		token.replaceAll(/(\u001b\[[0-9]+m){1}/,"")
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

	public IMaverickSymptom getSymptom() {
		return symptom
	}

	public long getTimestamp() {
		return timestamp
	}

	public String getLogLevel() {
		return logLevel
	}

	public String getServiceName() {
		return serviceName
	}

	public int getProcessId() {
		return processId
	}

	public String getThreadName() {
		return threadName
	}

	public String getLoggerName() {
		return loggerName
	}

	public String getSpringBootLogMessage() {
		return springBootLogMessage
	}

	@Override
	public IMaverickSymptom getEmpty() {
		return EMPTY_SPRINGBOOT_SYMPTOM
	}
	
	@Override
	public String toString() {
		MoreObjects.toStringHelper(this)
		           .add("dockerSymptom", symptom)
				   .add("timestamp", timestamp)
				   .add("logLevel", logLevel)
				   .add("serviceName", serviceName)
				   .add("processId", processId)
				   .add("threadName", threadName)
				   .add("loggerName", loggerName)
				   .add("springBootLogMessage", springBootLogMessage)
				   .toString()
	}

	@Override
	public String getCorrelationId() {
		return symptom.getCorrelationId();
	}
}

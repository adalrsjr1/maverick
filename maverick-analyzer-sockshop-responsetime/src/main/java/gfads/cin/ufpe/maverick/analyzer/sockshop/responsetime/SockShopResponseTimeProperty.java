package gfads.cin.ufpe.maverick.analyzer.sockshop.responsetime;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.analyzer.worker.Property;
import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.symtoms.IMaverickSymptom;
import gfads.cin.ufpe.maverick.events.symtoms.SpringBootSymptom;
import gfads.cin.ufpe.maverick.events.symtoms.sockshop.HttpSockShopSymptom;

/*
 * All Property instantiation must have a @Component annotation and
 * extends a Property
 */
@Component
public class SockShopResponseTimeProperty extends Property {

	private static final Logger LOG = LoggerFactory.getLogger(SockShopResponseTimeProperty.class);
	
	private final Float offset;
	
	/**
	 * Instantiated automatically by SockShopTimeResponsePropertyConfig
	 * @param name
	 */
	public SockShopResponseTimeProperty(String name, Float offset) {
		super(name);
		this.offset = offset;
	}
	
	@Override
	/**
	 * Should implement the logic to process a symptom and send a 
	 * change request to Planner. To send a requect it should call
	 * sendChangeRequest(changeRequest)
	 */
	public void process(IMaverickSymptom symptom) {
		SpringBootSymptom newSymptom = SpringBootSymptom.newSpringBootSymtom(symptom);
		HttpSockShopSymptom httpSymptom = HttpSockShopSymptom.newHttpSockShopSymptom(newSymptom);
		
		if(httpSymptom.getResponseTime() > offset) {
			MaverickChangeRequest changeRequest = new MaverickChangeRequest(name, httpSymptom);
			sendChangeRequest(changeRequest);
			LOG.debug(changeRequest.toString());
		}
	}
	
}

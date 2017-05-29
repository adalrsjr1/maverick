package gfads.cin.ufpe.maverick.analyzer.tutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.analyzer.worker.Property;
import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.symtoms.IMaverickSymptom;
import gfads.cin.ufpe.maverick.ltl.core.NonViolationEvent;

/*
 * All Property instantiation must have a @Component annotation and
 * extends a Property
 */
@Component
public class TutorialProperty extends Property {

	private static final Logger LOG = LoggerFactory.getLogger(TutorialProperty.class);
	
	/**
	 * Instantiated automatically by TutorialPropertyConfig
	 * @param name
	 */
	public TutorialProperty(String name) {
		super(name);
	}

	@Override
	/**
	 * Should implement the logic to process a symptom and send a 
	 * change request to Planner. To send a requect it should call
	 * sendChangeRequest(changeRequest)
	 */
	public void process(IMaverickSymptom symptom) {
		LOG.info(">>> [property {}]: {}", super.name, symptom);
		MaverickChangeRequest changeRequest = new MaverickChangeRequest(name, symptom);
		sendChangeRequest(changeRequest);
	}

}

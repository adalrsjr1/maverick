package gfads.cin.ufpe.maverick.analyzer.mock.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.events.MaverickSymptom;

@Component
public class MockProperty extends Property {
	private static final Logger LOG = LoggerFactory.getLogger(MockProperty.class);
	public MockProperty(@Value("${maverick.processing-unit.name}")String name, ProcessingUnit processingUnit) {
		super(name, processingUnit);
	}

	@Override
	public void process(MaverickSymptom symptom) {
		super.process(symptom);
		LOG.error(symptom.toString());
	}
	
	

}

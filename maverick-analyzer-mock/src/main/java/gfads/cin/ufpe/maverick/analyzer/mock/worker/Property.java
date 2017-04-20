package gfads.cin.ufpe.maverick.analyzer.mock.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.MaverickSymptom;

public abstract class Property {
	protected ProcessingUnit processingUnit;
	protected String name;
	
	public Property(String name, ProcessingUnit processingUnit) {
		this.name = name;
		this.processingUnit = processingUnit;
	}
	
	public String getName() {
		return name;
	}
	
	public void notifyChangeRequest(MaverickChangeRequest changeRequest) {
		processingUnit.sendChangeRequest(changeRequest);
	}
	
	public void process(MaverickSymptom symptom) {
		MaverickChangeRequest changeRequest = new MaverickChangeRequest(name, symptom);
		processingUnit.sendChangeRequest(changeRequest);
	}
}

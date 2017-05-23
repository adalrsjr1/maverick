package gfads.cin.ufpe.maverick.analyzer.worker;

import org.springframework.beans.factory.annotation.Autowired;

import gfads.cin.ufpe.maverick.events.IMaverickSymptom;
import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;

public abstract class Property {
	@Autowired
	private ProcessingUnit processingUnit;
	protected String name;
	
	public Property(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void sendChangeRequest(MaverickChangeRequest changeRequest) {
		processingUnit.sendChangeRequest(changeRequest);
	}
	
	public abstract void process(IMaverickSymptom symptom);
}

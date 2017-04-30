package gfads.cin.ufpe.maverick.analyzer.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.analyzer.endpoint.Sender;
import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.MaverickSymptom;

@Component
public class ProcessingUnit {
	@Autowired
	private Sender sender;
	
	@Autowired
	private Property property;
	
	public ProcessingUnit() { }
	
	public void doWork(MaverickSymptom symptom) {
		property.process(symptom);
	}
	
	public void sendChangeRequest(MaverickChangeRequest changeRequest) {
		sender.send(changeRequest);
	}
	
}

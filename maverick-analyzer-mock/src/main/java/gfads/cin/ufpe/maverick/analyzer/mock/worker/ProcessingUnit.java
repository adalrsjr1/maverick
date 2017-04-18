package gfads.cin.ufpe.maverick.analyzer.mock.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.analyzer.mock.endpoint.Sender;
import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.MaverickSymptom;

@Component
public class ProcessingUnit {
	private final Sender sender;
	
	@Autowired
	private Property property;
	
	public ProcessingUnit(Sender sender) {
		this.sender = sender;
	}

	public void doWork(MaverickSymptom symptom) {
		property.process(symptom);
	}
	
	public void sendChangeRequest(MaverickChangeRequest changeRequest) {
		sender.send(changeRequest);
	}
	
}

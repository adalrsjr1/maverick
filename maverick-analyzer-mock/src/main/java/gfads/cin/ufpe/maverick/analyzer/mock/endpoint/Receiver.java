package gfads.cin.ufpe.maverick.analyzer.mock.endpoint;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.analyzer.mock.worker.ProcessingUnit;
import gfads.cin.ufpe.maverick.events.MaverickSymptom;

@Component
public class Receiver {
	@Autowired
	private ProcessingUnit processingUnit;
	
	@RabbitListener(queues = "#{queue.name}")
	public void receive(byte[] in) throws InterruptedException {
		processingUnit.doWork(MaverickSymptom.newMaverickSymptom(in));
	}

}

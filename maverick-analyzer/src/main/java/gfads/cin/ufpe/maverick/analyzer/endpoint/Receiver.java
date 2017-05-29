package gfads.cin.ufpe.maverick.analyzer.endpoint;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.analyzer.worker.ProcessingUnit;
import gfads.cin.ufpe.maverick.events.symtoms.DockerSymptom;

@Component
public class Receiver {
	@Autowired
	private ProcessingUnit processingUnit;
	
	@RabbitListener(queues = "#{queue.name}")
	public void receive(byte[] in) throws InterruptedException {
		processingUnit.doWork(DockerSymptom.newMaverickSymptom(in));
	}

}

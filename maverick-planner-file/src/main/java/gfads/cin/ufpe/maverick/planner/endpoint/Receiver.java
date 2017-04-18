package gfads.cin.ufpe.maverick.planner.endpoint;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.planner.PolicySelector;

@Component
public class Receiver {
	@Autowired
	private PolicySelector policySelector;
	
	@RabbitListener(queues = "#{plannerQueue.name}")
	public void receive(MaverickChangeRequest changeRequest) throws InterruptedException {
		policySelector.doWork(changeRequest);
	}

}

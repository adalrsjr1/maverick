package gfads.cin.ufpe.maverick.executor.endpoint;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.events.MaverickChangePlan;
import gfads.cin.ufpe.maverick.executor.ExecutorCore;

@Component
public class Receiver {

	@Autowired
	private ExecutorCore executorCore;

	@RabbitListener(queues = "#{adaptationQueue.name}")
	public void receive(MaverickChangePlan changePlan) throws InterruptedException {
		try {
			executorCore.doWork(changePlan);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}

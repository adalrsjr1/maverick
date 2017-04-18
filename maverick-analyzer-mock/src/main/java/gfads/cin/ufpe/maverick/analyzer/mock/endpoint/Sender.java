package gfads.cin.ufpe.maverick.analyzer.mock.endpoint;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;

public class Sender {
	@Autowired
	private RabbitTemplate template;
	@Autowired
	private Queue plannerQueue;
	
	public void send(MaverickChangeRequest changeRequest) {
		template.convertAndSend(plannerQueue.getName(), changeRequest);
	}
	
}

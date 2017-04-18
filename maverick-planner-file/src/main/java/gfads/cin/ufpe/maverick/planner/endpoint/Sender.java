package gfads.cin.ufpe.maverick.planner.endpoint;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import gfads.cin.ufpe.maverick.events.MaverickChangePlan;

public class Sender {
	@Autowired
	private RabbitTemplate template;
	@Autowired
	private Queue adaptationQueue;
	
	public void send(MaverickChangePlan changePlan) {
		MessagePostProcessor postProcessor = new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) {
				
				message.getMessageProperties().setPriority(changePlan.getPriority());
				return message;
			}
		};
		
		template.convertAndSend(adaptationQueue.getName(), changePlan, (MessagePostProcessor) postProcessor);
	}
}

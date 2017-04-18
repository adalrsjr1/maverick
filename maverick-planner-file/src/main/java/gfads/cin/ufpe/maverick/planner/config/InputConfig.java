package gfads.cin.ufpe.maverick.planner.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gfads.cin.ufpe.maverick.planner.endpoint.Receiver;

@Configuration
public class InputConfig {
	
	public static final String QUEUE_NAME = "maverick-planner-queue";

	@Bean
	public Queue plannerQueue() {
		return new Queue(QUEUE_NAME);
	}
	
	@Bean
	public Receiver receiver() {
		return new Receiver();
	}

}

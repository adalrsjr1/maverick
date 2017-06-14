package gfads.cin.ufpe.maverick.planner.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gfads.cin.ufpe.maverick.planner.endpoint.Receiver;

@Configuration
public class InputConfig {
	
	@Bean
	public Queue plannerQueue(@Value("${maverick.planner.incoming.queue")String queueName) {
		return new Queue(queueName);
	}
	
	@Bean
	public Receiver receiver() {
		return new Receiver();
	}

}

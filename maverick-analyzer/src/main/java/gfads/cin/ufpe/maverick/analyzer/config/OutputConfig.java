package gfads.cin.ufpe.maverick.analyzer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gfads.cin.ufpe.maverick.analyzer.endpoint.Sender;

@Configuration
public class OutputConfig {

	private static final String QUEUE_NAME = "maverick-planner-queue";
	
	@Bean
	public Queue plannerQueue() {
		return new Queue(QUEUE_NAME);
	}
	
	@Bean
	public Sender sender() {
		return new Sender();
	}
}

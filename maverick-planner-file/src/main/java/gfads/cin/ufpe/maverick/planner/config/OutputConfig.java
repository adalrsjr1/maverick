package gfads.cin.ufpe.maverick.planner.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gfads.cin.ufpe.maverick.planner.endpoint.Sender;

@Configuration
public class OutputConfig {

	private static final String QUEUE_NAME = "maverick-adaptation-queue";
	
	@Bean
	public Queue adaptationQueue() {
		boolean durable = false;
		boolean exclusive = false;
		boolean autoDelete = false;
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-max-priority",10);
		return new Queue(QUEUE_NAME, durable, exclusive, autoDelete, arguments);
	}
	
	@Bean
	public Sender sender() {
		return new Sender();
	}
}

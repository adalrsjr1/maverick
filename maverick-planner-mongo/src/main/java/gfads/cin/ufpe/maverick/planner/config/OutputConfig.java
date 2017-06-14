package gfads.cin.ufpe.maverick.planner.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gfads.cin.ufpe.maverick.planner.endpoint.Sender;

@Configuration
public class OutputConfig {

	@Bean
	public Queue adaptationQueue(@Value("${maverick.adaptation.queue}") String queueName) {
		boolean durable = false;
		boolean exclusive = false;
		boolean autoDelete = false;
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-max-priority",10);
		return new Queue(queueName, durable, exclusive, autoDelete, arguments);
	}
	
	@Bean
	public Sender sender() {
		return new Sender();
	}
}

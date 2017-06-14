package gfads.cin.ufpe.maverick.analyzer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gfads.cin.ufpe.maverick.analyzer.endpoint.Sender;

@Configuration
public class OutputConfig {

	@Bean
	public Queue plannerQueue(@Value("${maverick.analyzer.outgoing.queue}") String queueName) {
		return new Queue(queueName);
	}
	
	@Bean
	public Sender sender() {
		return new Sender();
	}
}

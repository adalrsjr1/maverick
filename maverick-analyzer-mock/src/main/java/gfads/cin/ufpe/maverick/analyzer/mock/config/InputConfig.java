package gfads.cin.ufpe.maverick.analyzer.mock.config;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gfads.cin.ufpe.maverick.analyzer.mock.endpoint.Receiver;

@Configuration
public class InputConfig {
	
	public static final String EXCHANGE_NAME = "fluentd.fanout"; 

	@Bean
	public FanoutExchange exchange() {
		return new FanoutExchange(EXCHANGE_NAME);
	}
	
	@Bean
	public Queue queue() {
		return new AnonymousQueue();
	}
	
	@Bean
	public Binding binding(FanoutExchange exchange, Queue queue) {
		return BindingBuilder.bind(queue).to(exchange);
	}
	
	@Bean
	public Receiver receiver() {
		return new Receiver();
	}

}

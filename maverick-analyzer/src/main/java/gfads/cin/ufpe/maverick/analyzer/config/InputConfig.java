package gfads.cin.ufpe.maverick.analyzer.config;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gfads.cin.ufpe.maverick.analyzer.endpoint.Receiver;

@Configuration
public class InputConfig {

	public static final String EXCHANGE_NAME = "fluentd.fanout"; 

	@Bean
	public FanoutExchange fanout() {
		return new FanoutExchange(EXCHANGE_NAME);
	}

	@Bean
	public Queue queue() {
		return new AnonymousQueue();
	}

	@Bean
	public Binding binding(FanoutExchange fanout, Queue queue) {
		return BindingBuilder.bind(queue).to(fanout);
	}

	@Bean
	public Receiver receiver() {
		return new Receiver();
	}
}

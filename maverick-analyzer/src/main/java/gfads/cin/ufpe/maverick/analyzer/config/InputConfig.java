package gfads.cin.ufpe.maverick.analyzer.config;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gfads.cin.ufpe.maverick.analyzer.endpoint.Receiver;

@Configuration
public class InputConfig {

	@Bean
	public FanoutExchange fanout(@Value("${maverick.analyzer.incoming.exchange}") String exchangeName) {
		return new FanoutExchange(exchangeName);
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

package gfads.cin.ufpe.maverick.analyzer.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	/*
	 * --spring.rabbitmq.host=<rabbitmq address> 
	 * --maverick.property.name=<property name>
	 * --maverick.analyzer.incoming.exchange=<fluentd.fanout>
	 * --maverick.analyzer.outgoing.queue=<planner.queue>
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}

package gfads.cin.ufpe.maverick.analyzer.temporal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemporalApp {

	/*
	 * --spring.rabbitmq.host=<rabbitmq address>
	 * --maverick.property.name=<property name>
	 * --maverick.property.checker=<cheker name PascalCase>
	 * --maverick.property.ltl=<ltl property>
	 * --maverick.analyzer.incoming.exchange=<fluentd.fanout>
	 * --maverick.analyzer.outgoing.queue=<planner.queue>
	 */
	public static void main(String[] args) {
		SpringApplication.run(TemporalApp.class, args);
	}
}

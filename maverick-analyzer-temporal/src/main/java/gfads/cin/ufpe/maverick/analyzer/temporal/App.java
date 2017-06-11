package gfads.cin.ufpe.maverick.analyzer.temporal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

	/*
	 * --spring.rabbitmq.host=<rabbitmq address>
	 * --maverick.property.name=<property name>
	 * --maverick.property.checker=<cheker name PascalCase>
	 * --maverick.property.ltl=<ltl property>
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}

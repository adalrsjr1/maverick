package gfads.cin.ufpe.maverick.analyzer.sockshop.responsetime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	/*
	 * --spring.rabbitmq.host=<rabbitmq address> 
	 * --maverick.property.name=<property name>
	 * --maverick.property.offset=<responsetime offset>
	 * --spring.profiles.active=work-queues,sender
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}

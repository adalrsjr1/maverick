package gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	/*
	 * --spring.rabbitmq.host=<rabbitmq address> 
	 * --maverick.property.name=<property name>
	 * --spring.profiles.active=work-queues,sender
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}

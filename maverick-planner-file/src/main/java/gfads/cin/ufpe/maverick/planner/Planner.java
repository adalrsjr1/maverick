package gfads.cin.ufpe.maverick.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Planner {
	/*
	 * --spring.rabbitmq.host=198.162.52.145 
	 * --planner.policies.repository=src/main/resources/policies.json 
	 * --planner.nWorkers=8
	 */
	public static void main(String[] args) {
		SpringApplication.run(Planner.class, args);
	}
}

package gfads.cin.ufpe.maverick.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Planner {
	/*
	 * --spring.rabbitmq.host=198.162.52.145
	 * --spring.data.mongodb.host=mongoserver
	 * --spring.data.mongodb.port=27017 
	 * --spring.data.mongodb.database=test
	 * --maverick.planner.nWorkers=8
	 */
	public static void main(String[] args) {
		SpringApplication.run(Planner.class, args);
	}
}

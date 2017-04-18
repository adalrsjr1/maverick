package gfads.cin.ufpe.maverick.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Executor {
	/*
	 * --spring.rabbitmq.host=198.162.52.145
	 * --executor.actions.repository=src/main/resources/actions
	 */
	public static void main(String[] args) {
		SpringApplication.run(Executor.class, args);
	}
}

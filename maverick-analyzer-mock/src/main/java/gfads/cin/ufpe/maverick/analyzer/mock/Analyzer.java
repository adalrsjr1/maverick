package gfads.cin.ufpe.maverick.analyzer.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Analyzer {
	/*
	 * --spring.rabbitmq.host=198.162.52.145 
	 * --maverick.processing-unit.name=mock
	 */
	public static void main(String[] args) {
		SpringApplication.run(Analyzer.class, args);
	}
}

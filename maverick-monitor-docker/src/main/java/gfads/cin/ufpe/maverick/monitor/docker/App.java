package gfads.cin.ufpe.maverick.monitor.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import gfads.cin.ufpe.maverick.monitor.Monitor;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(Monitor.class, args).close();
	}
}

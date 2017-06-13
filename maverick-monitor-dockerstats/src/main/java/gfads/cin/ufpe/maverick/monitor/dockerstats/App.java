package gfads.cin.ufpe.maverick.monitor.dockerstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import gfads.cin.ufpe.maverick.monitor.Monitor;

@SpringBootApplication
public class App {

	/*
	 * --maverick.monitor.fluentd.port=<9880>
	 * --maverick.monitor.fluentd.address=<localhost>
	 * --maverick.monitor.fluentd.tag=<test>
	 * --maverick.monitor.docker.stats.sleeptime=<long>
	 */
	public static void main(String[] args) {
		SpringApplication.run(Monitor.class, args).close();
	}
}

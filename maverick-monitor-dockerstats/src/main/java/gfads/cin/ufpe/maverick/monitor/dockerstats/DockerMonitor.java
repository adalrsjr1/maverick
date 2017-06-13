package gfads.cin.ufpe.maverick.monitor.dockerstats;

import java.util.concurrent.Executors;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.EventStream;
import com.spotify.docker.client.ObjectMapperProvider;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerStats;
import com.spotify.docker.client.messages.Event;

import gfads.cin.ufpe.maverick.monitor.worker.Worker;

@Component
public class DockerMonitor extends Worker implements CommandLineRunner {

	@Autowired
	private DockerClient docker;
	@Autowired
	private Long sleepTime;
	
	
	private void dockerMonitoring() throws Exception {
		DockerClient docker = DefaultDockerClient.fromEnv().build();
		docker.listContainers().stream()
							   .map(c -> c.id())
							   .collect(Collectors.toList())
							   .forEach(id -> {
									try {
										ContainerStats stats = docker.stats(id);
										
										ObjectMapperProvider omp = new ObjectMapperProvider();
										ObjectMapper mapper = omp.getContext(ContainerStats.class);
										
										String json = mapper.writerWithView(Event.class).writeValueAsString(stats);
										
//										ContainerStats ev2 = mapper.getFactory().createParser(json).readValueAs(ContainerStats.class);
										
										sendMonitoredEvent(json);
									} catch (Exception e) {
										throw new RuntimeException(e);
									}
								});

	}

	@Override
	public void monitoring() {
		while(true) {
			try {
				dockerMonitoring();
				Thread.sleep(sleepTime);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run(String... arg0) throws Exception {
		monitoring();
	}
}

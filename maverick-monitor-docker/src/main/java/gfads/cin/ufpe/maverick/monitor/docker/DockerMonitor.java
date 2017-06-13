package gfads.cin.ufpe.maverick.monitor.docker;

import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.EventStream;
import com.spotify.docker.client.ObjectMapperProvider;
import com.spotify.docker.client.messages.Event;

import gfads.cin.ufpe.maverick.monitor.worker.Worker;

@Component
public class DockerMonitor extends Worker implements CommandLineRunner {

	@Autowired
	private DockerClient docker;
	@Autowired
	private EventStream dockerEvents;
	
	private void dockerMonitoring() throws Exception {
		while(dockerEvents.hasNext()) {
			Event ev = dockerEvents.next();

			ObjectMapperProvider omp = new ObjectMapperProvider();
			ObjectMapper mapper = omp.getContext(Event.class);

			String json;
			try {
				json = mapper.writerWithView(Event.class).writeValueAsString(ev);
				sendMonitoredEvent(json);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void monitoring() {
		try {
			dockerMonitoring();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run(String... arg0) throws Exception {
		monitoring();
	}

}

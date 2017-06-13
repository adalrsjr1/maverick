package gfads.cin.ufpe.maverick.monitor.docker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.EventStream;
import com.spotify.docker.client.exceptions.DockerException;

@Configuration
//To get properties from dependencies
@ComponentScan(basePackages={"gfads.cin.ufpe.maverick.monitor.config"}) 
public class DockerMonitorConfig {
	
	@Bean
	public DockerClient docker() throws Exception {
		return DefaultDockerClient.fromEnv().build();
	}

	@Bean
	public EventStream dockerEvents(DockerClient docker) throws Exception {
		return docker.events();
	}
}

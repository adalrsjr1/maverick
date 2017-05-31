package gfads.cin.ufpe.maverick.executor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorConfig {

	@Bean
	public String actionsRepository(@Value("${executor.actions.repository}") String path) {
		return path;
	}
}

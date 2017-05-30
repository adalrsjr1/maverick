package gfads.cin.ufpe.maverick.planner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlannerConfig {

	@Bean
	public Integer nWorkers(@Value("${maverick.planner.nWorkers:#{-1}}") int n) {
		return n;
	}
}

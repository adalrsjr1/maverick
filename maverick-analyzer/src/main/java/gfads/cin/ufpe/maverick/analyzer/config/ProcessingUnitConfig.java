package gfads.cin.ufpe.maverick.analyzer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gfads.cin.ufpe.maverick.analyzer.worker.ProcessingUnit;

@Configuration
public class ProcessingUnitConfig {

	@Bean
	public ProcessingUnit processingUnit() {
		return new ProcessingUnit();
	}
	
}

package gfads.cin.ufpe.maverick.analyzer.tutorial;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import gfads.cin.ufpe.maverick.analyzer.worker.Property;

@Configuration
// To get properties from dependencies
@ComponentScan(basePackages={"gfads.cin.ufpe.maverick.analyzer.config"}) 
public class TutorialPropertyConfig {
	
	@Bean
	/**
	 * Instantiate property's name. This name come from user paramenter
	 * --maverick.property.name=<property name>
	 * @param name
	 * @return
	 */
	public String name(@Value("${maverick.property.name}") String name) {
		return name;
	}
	
	@Bean
	/**
	 * Instantiate a property with the name already instantiated
	 * @param name
	 * @return
	 */
	public Property property(String name) {
		return new TutorialProperty(name);
	}
}

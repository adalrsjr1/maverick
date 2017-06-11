package gfads.cin.ufpe.maverick.analyzer.temporal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystem;
import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystemFactory;
import gfads.cin.ufpe.maverick.ltl.core.checker.TransitionChecker;

@Configuration
@ComponentScan(basePackages={"gfads.cin.ufpe.maverick.analyzer.config"})
public class TemporalPropertyConfig {
	private static final Logger LOG = LoggerFactory.getLogger(TemporalPropertyConfig.class);
	
	@Bean
	public String name(@Value("${maverick.property.name") String name) {
		return name;
	}
	
	@Bean 
	public TransitionChecker checker(@Value("${maverick.property.checker}") String clazz) {
		final String pkgName = "gfads.cin.ufpe.maverick.analyzer.temporal.checkers.";
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		Class<? extends TransitionChecker> cls = null;
		TransitionChecker checker = null;
		try {
			cls = (Class<? extends TransitionChecker>) classLoader.loadClass(pkgName + clazz);
			checker = cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return checker;
	}
	
	@Bean
	public LabeledTransitionSystem ltlProperty(@Value("${maverick.property.ltl}") String ltl, TransitionChecker checker) {
		LOG.info("Creating LTS for {}", ltl);
		LabeledTransitionSystem lts = LabeledTransitionSystemFactory.create(ltl, checker);
		return lts;
	}
	
}

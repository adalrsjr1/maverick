package gfads.cin.ufpe.maverick.planner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.events.MaverickChangePlan;
import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.MaverickPolicy;
import gfads.cin.ufpe.maverick.planner.endpoint.Sender;
import gfads.cin.ufpe.maverick.planner.repository.PolicyRepository;

@Component
public class PolicySelector {
	private static final Logger LOG = LoggerFactory.getLogger(PolicySelector.class);
	
	@Autowired
	private PolicyRepository repository;

	private Sender sender;
	private Integer nWorkers;
	
	private ExecutorService threadPool;
	
	public PolicySelector(Sender sender, Integer nWorkers) {
		this.sender = sender;
		this.nWorkers = nWorkers;

		LOG.debug("PolicySelector Workers: {}", nWorkers);
		
		threadPool = nWorkers > 0 ? Executors.newFixedThreadPool(this.nWorkers) 
				                  : Executors.newCachedThreadPool();
	}
	
	public void doWork(MaverickChangeRequest changeRequest) {
		
		LOG.trace("Planner Processing: {}", changeRequest);
		threadPool.execute(() -> {
			Iterable<MaverickPolicy> policies = repository.findByChangeRequest(changeRequest.getName());
			
			int count = 0;
			for(MaverickPolicy policy : policies) {
				sender.send(new MaverickChangePlan(policy, changeRequest));
				count++;
			}
			LOG.info("{} Policies selected for {}", count, changeRequest.getName());
		});
		
	}
}

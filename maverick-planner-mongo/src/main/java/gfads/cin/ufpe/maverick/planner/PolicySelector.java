package gfads.cin.ufpe.maverick.planner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.events.MaverickChangePlan;
import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.MaverickPolicy;
import gfads.cin.ufpe.maverick.planner.endpoint.Sender;
import gfads.cin.ufpe.maverick.planner.repository.PolicyRepository;

@Component
public class PolicySelector {
	@Autowired
	private PolicyRepository repository;
	private Sender sender;

	private Integer nWorkers;
	
	private ExecutorService threadPool;
	
	public PolicySelector(Sender sender, @Value("${planner.nWorkers}") Integer nWorkers) {
		this.sender = sender;
		this.nWorkers = nWorkers;
		
		threadPool = Executors.newFixedThreadPool(this.nWorkers);
	}
	
	public void doWork(MaverickChangeRequest changeRequest) {
		threadPool.execute(() -> {
			List<MaverickPolicy> policies = repository.fetchAdaptationPlans(changeRequest);

			policies.forEach(policy -> {
				sender.send(new MaverickChangePlan(policy, changeRequest));
			});
		});
		
	}
}

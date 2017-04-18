package gfads.cin.ufpe.maverick.planner.repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.MaverickPolicy;

@Component
public class FilePolicyRepositoryProxy implements PolicyRepository {

	private String policyRepositoryPath;

	private final FilePolicyRepository repository;
	private final Map<MaverickChangeRequest, List<MaverickPolicy>> cache;
	private final ReentrantReadWriteLock lock;

	public FilePolicyRepositoryProxy(@Value("${planner.policies.repository}")String policyRepositoryPath) {
		this.policyRepositoryPath = policyRepositoryPath;
		repository = new FilePolicyRepository(this.policyRepositoryPath);
		cache = new WeakHashMap<>();
		lock = new ReentrantReadWriteLock();
	}

	@Override
	public synchronized void storePolicy(MaverickPolicy policy) {
		repository.storePolicy(policy);
	}

	@Override
	public List<MaverickPolicy> fetchAdaptationPlans(MaverickChangeRequest changeRequest) {
		List<MaverickPolicy> policies = null;
		boolean isEmpty = true;
		try {
			lock.readLock().lock();
			if (cache.containsKey(changeRequest)) {
				policies = cache.get(changeRequest);
			}
			isEmpty = Objects.isNull(policies) || policies.isEmpty();
		}
		finally {
			lock.readLock().unlock();
		}

		if(isEmpty) {
			try {
				lock.writeLock().lock();

				policies = repository.fetchAdaptationPlans(changeRequest);
				cache.put(changeRequest, policies);
			}
			finally {
				lock.writeLock().unlock();
			}
		}
		return policies;
	}

	@Override
	public synchronized List<MaverickPolicy> fetchAll() {
		if(cache.isEmpty()) {
			return repository.fetchAll();
		}
		return cache.values().stream().flatMap(List::stream).collect(Collectors.toList());
	}



}

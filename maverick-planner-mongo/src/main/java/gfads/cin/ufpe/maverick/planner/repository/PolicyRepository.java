package gfads.cin.ufpe.maverick.planner.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import gfads.cin.ufpe.maverick.events.MaverickPolicy;

public interface PolicyRepository extends MongoRepository<MaverickPolicy, String> {

	@Cacheable (cacheNames = "policies")
	Iterable<MaverickPolicy> findAllByName(String changeRequestName);
}

package gfads.cin.ufpe.maverick.planner.repository;

import gfads.cin.ufpe.maverick.events.MaverickPolicy;

public interface PolicyRepository {

	
	Iterable<MaverickPolicy> findByChangeRequest(String changeRequestName);
	
	Iterable<MaverickPolicy> findAll();
}

package gfads.cin.ufpe.maverick.planner.repository;

import java.util.List;

import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.MaverickPolicy;

public interface PolicyRepository {

	void storePolicy(MaverickPolicy policy);

	List<MaverickPolicy> fetchAdaptationPlans(MaverickChangeRequest changeRequest);

	List<MaverickPolicy> fetchAll();

}

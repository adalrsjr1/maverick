package gfads.cin.ufpe.maverick.planner;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.MaverickPolicy;
import gfads.cin.ufpe.maverick.planner.repository.FilePolicyRepository;
import gfads.cin.ufpe.maverick.planner.repository.FilePolicyRepositoryProxy;
import gfads.cin.ufpe.maverick.planner.repository.PolicyRepository;
import junit.framework.TestCase;

public class FilePolicyRepositoryTest extends TestCase {
	final private ExecutorService tPool = Executors.newCachedThreadPool();
	
	public void testIsUpdated() throws IOException {
		Path path = Paths.get("src/test/resources/testWritePolicy.json");
		if(!Files.exists(path)) {
			Files.createFile(path);
		}
		FilePolicyRepository repository = new FilePolicyRepository(path.toString());
		assertTrue(repository.wasRecentlyUpdated(10*1000));
		Files.deleteIfExists(path);
		
		path = Paths.get("src/test/resources/testReadPolicies.json");
		if(!Files.exists(path)) {
			assertTrue(false);
		}
		repository = new FilePolicyRepository(path.toString());
		assertFalse(repository.wasRecentlyUpdated(10*1000));
	}
	
	public void testWritePolicy() throws Exception {
		Path path = Paths.get("src/test/resources/testWritePolicy.json");
		if(!Files.exists(path)) {
			Files.createFile(path);
		}
		
		PolicyRepository repository = new FilePolicyRepositoryProxy(path.toString());;
		MaverickChangeRequest request = new MaverickChangeRequest("changeRequest", null);
		MaverickPolicy policy = new MaverickPolicy("changeRequest", "action", 0);
		repository.storePolicy(policy);
		
		List<MaverickPolicy> policies = null;

		try(BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
			policies = reader.lines()
					.map(l -> MaverickPolicy.deserialize(l))
					.filter(p -> p.changeRequestMatch(request))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		MaverickPolicy p = policies.remove(0);
		assertTrue(p.equals(policy));
		
		Files.deleteIfExists(path);
	}
	
	public void testReadPolicy() {
		Path path = Paths.get("src/test/resources/testReadPolicies.json");
		if(!Files.exists(path)) {
			assertTrue(false);
		}
		
		PolicyRepository repository = new FilePolicyRepositoryProxy(path.toString());;
		MaverickChangeRequest request = new MaverickChangeRequest("changeRequest1", null);
		List<MaverickPolicy> policies = repository.fetchAdaptationPlans(request);
		
		assertTrue(policies.size() == 2);
		assertEquals("action1", policies.get(0).getAction());
		assertEquals("action2", policies.get(1).getAction());
		
		request = new MaverickChangeRequest("changeRequest2", null);
		policies = repository.fetchAdaptationPlans(request);
		assertEquals("action2", policies.get(0).getAction());
		assertEquals(10, policies.get(0).getPriority());
	}
	
	public void testReadPolicy10() {
		
		for(int i = 0; i < 10; i++) {
			tPool.execute(()-> {
				testReadPolicy();
			});
		}
	}
	
	public void testReadPolicy100() {
		for(int i = 0; i < 100; i++) {
			tPool.execute(()-> {
				testReadPolicy();
			});
		}
	}
	
	public void testReadPolicies() {
		Path path = Paths.get("src/test/resources/testReadPolicies.json");
		if(!Files.exists(path)) {
			assertTrue(false);
		}
		
		PolicyRepository repository = new FilePolicyRepositoryProxy(path.toString());;
		List<MaverickPolicy> policies = repository.fetchAll();
		
		assertTrue(policies.size() == 4);
		assertEquals("action0", policies.get(0).getAction());
		assertEquals("action1", policies.get(1).getAction());
		assertEquals("action2", policies.get(2).getAction());
		assertEquals("action2", policies.get(3).getAction());
		
		assertEquals("changeRequest0", policies.get(0).getChangeRequest());
		assertEquals("changeRequest1", policies.get(1).getChangeRequest());
		assertEquals("changeRequest2", policies.get(2).getChangeRequest());
		assertEquals("changeRequest1", policies.get(3).getChangeRequest());
	}
	
}

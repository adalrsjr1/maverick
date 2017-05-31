package gfads.cin.ufpe.maverick.planner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.MaverickPolicy;
import gfads.cin.ufpe.maverick.planner.repository.FilePolicyRepository;
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

	public void testReadPolicy() {
		Path path = Paths.get("src/test/resources/testReadPolicies.json");
		if(!Files.exists(path)) {
			assertTrue(false);
		}

		PolicyRepository repository = new FilePolicyRepository(path.toString());;
		for(int i = 0; i < 2; i++) {
			MaverickChangeRequest request = new MaverickChangeRequest("changeRequest1", null);
			List<MaverickPolicy> policies = (List<MaverickPolicy>) repository.findByChangeRequest(request.getName());

			assertTrue(policies.size() == 2);

			Map action1 = new HashMap();
			action1.put("name", "action1");
			Map action2 = new HashMap();
			action2.put("name", "action2");

			assertEquals(action1, policies.get(0).getAction());
			assertEquals(action2, policies.get(1).getAction());

			request = new MaverickChangeRequest("changeRequest2", null);
			policies = (List<MaverickPolicy>) repository.findByChangeRequest(request.getName());
			assertEquals(action2, policies.get(0).getAction());
			assertEquals(10, policies.get(0).getPriority());
		}
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void testReadPolicies() {
		Path path = Paths.get("src/test/resources/testReadPolicies.json");
		if(!Files.exists(path)) {
			assertTrue(false);
		}

		PolicyRepository repository = new FilePolicyRepository(path.toString());;
		List<MaverickPolicy> policies = (List<MaverickPolicy>) repository.findAll();

		assertTrue(policies.size() == 4);
		Map action0, action1, action2;
		action0 = new HashMap();
		action0.put("name", "action0");
		action0.put("attr1", 1);
		action0.put("attr2", 3.14);
		action0.put("attr3", "value");
		action1 = new HashMap();
		action1.put("name", "action1");
		action2 = new HashMap();
		action2.put("name", "action2");

		assertEquals(action0, policies.get(0).getAction());
		assertEquals(action0.get("attr2"), policies.get(0).getAction().get("attr2"));
		assertEquals(action1, policies.get(1).getAction());
		assertEquals(action2, policies.get(2).getAction());
		assertEquals(action2, policies.get(3).getAction());

		assertEquals("changeRequest0", policies.get(0).getChangeRequest());
		assertEquals("changeRequest1", policies.get(1).getChangeRequest());
		assertEquals("changeRequest2", policies.get(2).getChangeRequest());
		assertEquals("changeRequest1", policies.get(3).getChangeRequest());
	}

}

package gfads.cin.ufpe.maverick.planner

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import gfads.cin.ufpe.maverick.events.MaverickPolicy
import gfads.cin.ufpe.maverick.planner.repository.PolicyRepository
import junit.framework.TestCase

@SpringBootTest
@RunWith(SpringRunner.class)
public class PolicyRepositoryTest extends TestCase {

	@Autowired
	private PolicyRepository policyRepository

	@Before
	public void setUp() throws Exception {
		MaverickPolicy policy1 = new MaverickPolicy("changeRequest1", [name:"policy1", attr1:1, attr2:3.14, attr3:"value"], 0)
		MaverickPolicy policy2 = new MaverickPolicy("changeRequest2", [name:"policy2", attr:[1, 2.3, "value2"]], 2)
		MaverickPolicy policy3 = new MaverickPolicy("changeRequest1", [name:"policy3"], 5)

		assertNull(policy1.getId())
		assertNull(policy2.getId())
		assertNull(policy3.getId())

		this.policyRepository.save(policy1)
		this.policyRepository.save(policy2)
		this.policyRepository.save(policy3)

		assertNotNull(policy1.getId())
		assertNotNull(policy2.getId())
		assertNotNull(policy3.getId())
	}

	@Test
	public void testFetchData() {
		/*Test data retrieval*/
		def policies = policyRepository.findByChangeRequest("changeRequest1")
		assertNotNull(policies)
		assertEquals(policies.size(), 2)

		/*Get all products, list should only have two*/
		Iterable<MaverickPolicy> policies2 = policyRepository.findAll()
		int count = 0
		for(MaverickPolicy p : policies2){
			count++
		}
		assertEquals(count, 3)
	}

	@After
	public void tearDown() throws Exception {
		this.policyRepository.deleteAll()
	}
}

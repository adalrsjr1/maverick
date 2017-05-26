package gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse.events.SockShopLog;
import gfads.cin.ufpe.maverick.analyzer.sockShopTimeResponse.events.SockShopSymptom;
import gfads.cin.ufpe.maverick.analyzer.worker.Property;
import gfads.cin.ufpe.maverick.events.IMaverickSymptom;

/*
 * All Property instantiation must have a @Component annotation and
 * extends a Property
 */
@Component
public class SockShopTimeResponseProperty extends Property {

	private static final Logger LOG = LoggerFactory.getLogger(SockShopTimeResponseProperty.class);
	
	private final Integer offset;
	
	/**
	 * Instantiated automatically by SockShopTimeResponsePropertyConfig
	 * @param name
	 */
	public SockShopTimeResponseProperty(String name, Integer offset) {
		super(name);
		this.offset = offset;
	}

	@Override
	/**
	 * Should implement the logic to process a symptom and send a 
	 * change request to Planner. To send a requect it should call
	 * sendChangeRequest(changeRequest)
	 */
	public void process(IMaverickSymptom symptom) {
		SockShopSymptom newSymptom = SockShopSymptom.newSockShopSymptom(symptom);
		Object responseTime = newSymptom.get("responseTime");
		
		if(Objects.nonNull(responseTime) && responseTime instanceof Integer) {
			System.out.println(((Integer)newSymptom.get("responseTime") > 10) + "");
		}
		
		
		System.out.println(newSymptom);
	}
}

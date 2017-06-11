package gfads.cin.ufpe.maverick.analyzer.temporal;

import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.analyzer.worker.Property;
import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.symtoms.IMaverickSymptom;
import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystem;
import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystemEvent;
import gfads.cin.ufpe.maverick.ltl.core.LabeledTransitionSystemState;
import gfads.cin.ufpe.maverick.ltl.core.ViolationEvent;

@Component
public class TemporalProperty extends Property {
	private static final Logger LOG = LoggerFactory.getLogger(TemporalProperty.class);
	
	@Autowired
	private LabeledTransitionSystem ltlProperty;
	
	// TODO: change this to be ThreadSafe and GC safe by using Redis/etcd/consul...
	private Map<String, LabeledTransitionSystemState> cache = new WeakHashMap<>();
	
	public TemporalProperty(String name) {
		super(name);
	}

	private LabeledTransitionSystemState getState(String key) {
		LabeledTransitionSystemState state;
		if(cache.containsKey(key)) {
			state = cache.get(key);
		}
		else {
			state = new LabeledTransitionSystemState(key);
			cache.put(key,state);
		}
		return state;
	}
	
	private void removeState(String key) {
		cache.remove(key);
	}
	
	@Override
	public void process(IMaverickSymptom symptom) {
		LabeledTransitionSystemState state = getState(symptom.getCorrelationId());
		LabeledTransitionSystemEvent event = ltlProperty.next(state, symptom);
		
		if(event instanceof ViolationEvent) {
			removeState(symptom.getCorrelationId());
			sendChangeRequest(new MaverickChangeRequest(name, symptom));
		}
	}

}

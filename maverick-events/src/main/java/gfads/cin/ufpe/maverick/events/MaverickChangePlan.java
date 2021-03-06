package gfads.cin.ufpe.maverick.events;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import gfads.cin.ufpe.maverick.events.symtoms.DockerSymptom;

public class MaverickChangePlan extends MaverickEvent implements Comparable<MaverickChangePlan> {
	
	private static final long serialVersionUID = -1624665312886096601L;

	private MaverickPolicy policy;
	private MaverickChangeRequest changeRequest;
	
	public MaverickChangePlan(MaverickPolicy policy, MaverickChangeRequest changeRequest) {
		this.policy = policy;
		this.changeRequest = changeRequest;
	}
	
	public int getPriority() {
		return policy.getPriority();
	}
	
	public MaverickPolicy getPolicy() {
		return policy;
	}
	
	public Map getAction() {
		return policy.getAction();
	}
	
	public String getActionName() {
		return (String) getAction().get("name");
	}
	
	public Object getActionAttribute(String key) {
		return getAction().get(key);
	}
	
	public MaverickChangeRequest getChangeRequest() {
		return changeRequest;
	}
	
	public Object get(String property) {
		if(property.equalsIgnoreCase("priority")) {
			return getPriority();
		}
		
		if(property.equalsIgnoreCase("action")) {
			return getAction();
		}
		
		return changeRequest.get(property);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(! (o instanceof DockerSymptom)) return false;
		
		MaverickChangePlan changePlan = (MaverickChangePlan) o;
		
		return Objects.equals(policy, changePlan.getPolicy())
			   && Objects.equals(changeRequest, changePlan.getChangeRequest());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getPolicy(), getChangeRequest());
	}

	@Override
	public String toString() {
		return "MaverickChangePlan [policy=" + policy + ", changeRequest=" + changeRequest + "]";
	}

	@Override
	public int compareTo(MaverickChangePlan o) {
		if(o.getPriority() == getPriority()) {
			return policy.compareTo(o.getPolicy());
		}
		return Integer.compare(o.getPriority(), getPriority());
	}
}

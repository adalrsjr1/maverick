package gfads.cin.ufpe.maverick.events;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
	
	public String getAction() {
		return policy.getAction();
	}
	
	public long getElapsedTime(TimeUnit timeUnit) {
		return changeRequest.getElapsedTime(timeUnit);
	}
	
	public MaverickChangeRequest getChangeRequest() {
		return changeRequest;
	}
	
	@Override
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
		if(! (o instanceof MaverickSymptom)) return false;
		
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

package gfads.cin.ufpe.maverick.monitor.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gfads.cin.ufpe.maverick.monitor.endpoint.Sender;

@Component
public abstract class Worker {
	@Autowired
	private Sender sender;
	
	public void sendMonitoredEvent(String json) {
		sender.send(json);
	}
	
	public abstract void monitoring();
}

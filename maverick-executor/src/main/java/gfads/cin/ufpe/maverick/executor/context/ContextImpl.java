package gfads.cin.ufpe.maverick.executor.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class ContextImpl implements Context {

	private Map<String, Object> storage;

	public ContextImpl() {
		storage = new HashMap<String, Object>();
	}
	
	@Override
	public boolean addEntry(String key, Object value) {
		Object tmp = storage.put(key, value);
		return Objects.nonNull(value) && value == tmp;
	}

	@Override
	public boolean removeEntry(String key) {
		Object tmp = storage.get(key);
		return storage.remove(key) == tmp;
	}

	@Override
	public boolean checkConsistency() {
		return true;
	}
	
}

package gfads.cin.ufpe.maverick.executor.context;

public interface Context {

	public boolean addEntry(String key, Object value);
	public boolean removeEntry(String key);
	public boolean checkConsistency();
	
}

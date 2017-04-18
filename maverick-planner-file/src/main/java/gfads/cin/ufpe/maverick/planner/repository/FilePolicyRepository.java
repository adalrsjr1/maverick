package gfads.cin.ufpe.maverick.planner.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import gfads.cin.ufpe.maverick.events.MaverickChangeRequest;
import gfads.cin.ufpe.maverick.events.MaverickPolicy;

public class FilePolicyRepository implements PolicyRepository {

	private Path path;

	public FilePolicyRepository(String pathname) {
		path = Paths.get(pathname);
	}
	
	public synchronized boolean wasRecentlyUpdated(long updateWindowInMillis) {
		try {
			FileTime lastModification = Files.getLastModifiedTime(path);
			return Math.abs(lastModification.toMillis() - Instant.now().toEpochMilli()) < updateWindowInMillis;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized void storePolicy(MaverickPolicy policy) {
		try(BufferedWriter writer = Files.newBufferedWriter(path, 
											StandardOpenOption.CREATE,
											StandardOpenOption.APPEND, 
											StandardOpenOption.WRITE)) {
			
			writer.write(policy.serializeToJson() + "\n");
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized List<MaverickPolicy> fetchAdaptationPlans(MaverickChangeRequest changeRequest) {
		List<MaverickPolicy> policies = null;

		try(BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
			policies = reader.lines()
					.map(l -> MaverickPolicy.deserialize(l))
					.filter(p -> p.changeRequestMatch(changeRequest))
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		

		return Objects.nonNull(policies) ? policies : new ArrayList<>();
	}

	@Override
	public synchronized List<MaverickPolicy> fetchAll() {
		List<MaverickPolicy> policies = null;

		try(BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
			policies = reader.lines()
					.map(l -> MaverickPolicy.deserialize(l))
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return Objects.nonNull(policies) ? policies : new ArrayList<>();
	}

}

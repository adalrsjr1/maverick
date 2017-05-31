package gfads.cin.ufpe.maverick.planner.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;

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
//	@Cacheable (cacheNames = "policies")
	public synchronized Iterable<MaverickPolicy> findByChangeRequest(String changeRequestName) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		List<MaverickPolicy> policies = null;

		try(BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
			policies = reader.lines()
					.map(l -> MaverickPolicy.deserialize(l))
					.filter(p -> Objects.equals(p.getName(), changeRequestName))
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		

		return Objects.nonNull(policies) ? policies : new ArrayList<>();
	}

	@Override
	public synchronized List<MaverickPolicy> findAll() {
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

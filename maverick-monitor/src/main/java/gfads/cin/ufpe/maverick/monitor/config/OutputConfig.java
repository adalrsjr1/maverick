package gfads.cin.ufpe.maverick.monitor.config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OutputConfig {
	
	@Bean
	public String fluentdAddress(@Value("${maverick.monitor.fluentd.address}") String address) {
		return address;
	}
	
	@Bean
	public Integer fluentdPort(@Value("${maverick.monitor.fluentd.port}") Integer port) {
		return port;
	}
	
	@Bean String fluentdTag(@Value("${maverick.monitor.fluentd.tag}") String tag) {
		if(Objects.isNull(tag) || tag.equals("")) {
			return "";
		}
		return tag;
	}
	
	@Bean
	public URL targetURL(String fluentdAddress, Integer fluentdPort, String fluentdTag) {
		URL url = null;
		try {
			url = new URL("http://"+ fluentdAddress + ":" + fluentdPort + "/" + fluentdTag );
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
	
}
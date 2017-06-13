package gfads.cin.ufpe.maverick.monitor.endpoint;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

	@Autowired
	private URL targetURL;

	private HttpURLConnection httpConnection(URL targetURL) {
		HttpURLConnection httpConnection = null;
		try {
			httpConnection = (HttpURLConnection)targetURL.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);
			
			httpConnection.setRequestProperty("Content-Type", "application/json;");
			httpConnection.setRequestProperty("Method", "POST");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return httpConnection;
	}
	
	public int send(String json) {
		HttpURLConnection httpConnection = httpConnection(targetURL);
		
		OutputStream os = null;
		try {
			httpConnection.connect();
			os = httpConnection.getOutputStream();
			os.write(json.toString().getBytes("UTF-8"));
			os.flush();
			os.close();
			return httpConnection.getResponseCode();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
}

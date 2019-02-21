package dk.jarry.wildfly.demo.ping.boundary;

import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PingResourceIT {
	
	Logger logger = Logger.getLogger(PingResourceIT.class.getCanonicalName());

	private WebTarget pingTarget;

	@BeforeEach
	public void init() {		
		String pingUri = "http://0.0.0.0:8080/simple-ping-test/resources/ping";
		pingTarget = ClientBuilder.newClient().target(pingUri);	
	}

	@Test
	void get() {
		Response response = null;
		try {
			Builder request = pingTarget.register(MediaType.APPLICATION_JSON).request();		
			response = request.get();
			
			logger.info("Status : " + response.getStatus() + " - " + response.getStatusInfo().getReasonPhrase() );
			
		}finally {
			if(response != null) {
				response.close();
			}
		}		
	}


}

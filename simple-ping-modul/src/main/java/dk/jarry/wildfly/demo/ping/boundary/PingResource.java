package dk.jarry.wildfly.demo.ping.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("ping")
public class PingResource{

    private static Logger logger = LogManager.getLogger(PingResource.class);

    @GET
    public String ping() {
        logger.error("This is an error message");
        return "Enjoy Java EE 8!";
    }

}

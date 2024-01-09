package org.acme;

import java.text.NumberFormat;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class GreetingResource {

    static NumberFormat shortN = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);

    @GET
    @Path("sysresources")
    @Produces(MediaType.TEXT_PLAIN)
    public String getSystemResources() {
        long memory = Runtime.getRuntime().maxMemory();

        return "Memory: " + shortN.format(memory);
    }
}
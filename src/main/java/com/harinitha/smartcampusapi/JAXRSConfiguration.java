package com.harinitha.smartcampusapi;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api/v1")
public class JAXRSConfiguration extends ResourceConfig {

    public JAXRSConfiguration() {
        packages("com.senadhi.smartcampusapi");
    }
}
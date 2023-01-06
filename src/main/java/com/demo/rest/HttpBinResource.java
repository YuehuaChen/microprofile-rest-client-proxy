package com.demo.rest;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.net.URI;

@ApplicationScoped
@Path("/get")
public class HttpBinResource {
    @GET
    public MyRequest getRequest(){
        HttpBinService service = RestClientBuilder.newBuilder()
                .baseUri(URI.create("http://httpbin.org"))
                .proxyAddress("ec2-15-237-127-47.eu-west-3.compute.amazonaws.com", 3128)
                .register(ProxyAuthorizationRequestFilter.class)
                .build(HttpBinService.class);
        return service.getRequest("1");
    }
}

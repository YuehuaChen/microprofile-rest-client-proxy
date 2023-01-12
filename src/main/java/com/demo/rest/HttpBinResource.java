package com.demo.rest;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;

@ApplicationScoped
@Path("/get")
public class HttpBinResource {
    @GET
    public MyRequest getRequest(){
        System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                if(RequestorType.PROXY.equals(getRequestorType()) && "http".equalsIgnoreCase(getRequestingProtocol())) {
                    return new PasswordAuthentication("test_user", "T0pS3cr3t".toCharArray());
                }
                return null;
            }
        });
        HttpBinService service = RestClientBuilder.newBuilder()
//                .baseUri(URI.create("https://dsrv.eservicet-drv.de/komzfdr-api-war"))
                .baseUri(URI.create("https://httpbin.org"))
//                .property("com.ibm.ws.jaxrs.client.proxy.username", "test_user")
//                .property("com.ibm.ws.jaxrs.client.proxy.password", "T0pS3cr3t")
//                .property("com.ibm.ws.jaxrs.client.proxy.host", "ec2-13-38-36-150.eu-west-3.compute.amazonaws.com")
//                .property("com.ibm.ws.jaxrs.client.proxy.port", "3128")
                .proxyAddress("ec2-13-38-36-150.eu-west-3.compute.amazonaws.com", 3128)
                .register(LoggingFilter.class)
                .build(HttpBinService.class);
        return service.getRequest("1");
    }
}

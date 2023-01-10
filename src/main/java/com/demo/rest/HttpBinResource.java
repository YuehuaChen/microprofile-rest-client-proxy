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
                if(RequestorType.PROXY.equals(getRequestorType())) {
                    return new PasswordAuthentication("test_user", "T0pS3cr3t".toCharArray());
                }
                return null;
            }
        });
        HttpBinService service = RestClientBuilder.newBuilder()
                .baseUri(URI.create("https://httpbin.org"))
                .proxyAddress("ec2-35-180-92-16.eu-west-3.compute.amazonaws.com", 3128)
                .build(HttpBinService.class);
        return service.getRequest("1");
    }
}

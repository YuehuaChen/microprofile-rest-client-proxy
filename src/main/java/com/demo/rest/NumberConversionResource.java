package com.demo.rest;

import com.dataaccess.numberconversion.NumberConversion;
import com.dataaccess.numberconversion.NumberConversionSoapType;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.math.BigInteger;
import java.net.*;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/number-conversion/get")
public class NumberConversionResource {

    private NumberConversionSoapType numberConversionService;

    private static final Logger LOGGER = Logger.getLogger(NumberConversionResource.class.getName());

    @PostConstruct
    public void init() {
        ProxySelector.setDefault(new MyProxySelector());
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                if(RequestorType.PROXY.equals(getRequestorType()) && "basic".equalsIgnoreCase(getRequestingScheme())) {
                    LOGGER.info("getting Name/Password for Basic");
                    return new PasswordAuthentication("test_user", "T0pS3cr3t".toCharArray());
                }
                if(RequestorType.PROXY.equals(getRequestorType()) && "negotiate".equalsIgnoreCase(getRequestingScheme())) {
                    LOGGER.info("getting Name/Password for Kerberos");
                    return new PasswordAuthentication("employee", "Secret123".toCharArray());
                }
                return null;
            }
        });
        try {
            numberConversionService = new NumberConversion(new URL("https", "www.dataaccess.com", "/webservicesserver/numberconversion.wso?wsdl"))
                    .getNumberConversionSoap();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    @GET
    public String getNumber() {
        return numberConversionService.numberToWords(BigInteger.TEN);
    }
}

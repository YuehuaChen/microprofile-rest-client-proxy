package com.demo.rest;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

//@RegisterRestClient(configKey = "httpBin")
public interface HttpBinService {
    @GET
    @Path("get")
    MyRequest getRequest(@QueryParam("show_env") String showEnv);
}

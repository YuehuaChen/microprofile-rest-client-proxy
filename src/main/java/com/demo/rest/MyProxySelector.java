package com.demo.rest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyProxySelector extends java.net.ProxySelector {

    private static final Logger LOGGER = Logger.getLogger(MyProxySelector.class.getName());
    @Override
    public List<Proxy> select(URI uri) {
        if(uri.getHost().equals("www.dataaccess.com") || uri.getHost().equals("httpbin.org")) {
            LOGGER.info("select amazon proxy");
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("ec2-35-180-92-168.eu-west-3.compute.amazonaws.com", 3128));
            return List.of(proxy);
        }
        return null;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        LOGGER.log(Level.SEVERE, ioe, () -> "Connection failed");
    }
}

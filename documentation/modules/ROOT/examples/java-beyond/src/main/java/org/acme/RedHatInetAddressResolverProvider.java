package org.acme;

import java.net.spi.InetAddressResolver;
import java.net.spi.InetAddressResolverProvider;

public class RedHatInetAddressResolverProvider extends InetAddressResolverProvider {

    @Override
    public InetAddressResolver get(Configuration configuration) {
        return new RedHatInetAddressResolver();
    }

    @Override
    public String name() {
        return "Red Hat Inet Address Resolver";
    }
    
}

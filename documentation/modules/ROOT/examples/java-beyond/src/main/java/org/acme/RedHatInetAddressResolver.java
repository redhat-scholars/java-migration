package org.acme;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.spi.InetAddressResolver;
import java.util.stream.Stream;

public class RedHatInetAddressResolver implements InetAddressResolver {

    @Override
    public Stream<InetAddress> lookupByName(String host, LookupPolicy lookupPolicy) throws UnknownHostException {
        System.out.println(host);
        return Stream.of(InetAddress.getByAddress(new byte[] {127, 0, 0, 1}));
    }

    @Override
    public String lookupByAddress(byte[] addr) throws UnknownHostException {
        throw new UnsupportedOperationException();
    }

}

package org.acme;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws UnknownHostException {
        InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
        System.out.println("addresses = " + Arrays.toString(addresses));

        Object dev = new Developer("Alex", 42);

        if (dev instanceof Developer(String name, int age)) {
            System.out.println(name + " " + age);
        }

        switch (dev) {
            case Developer d -> System.out.println("I am a developer");
            default -> System.out.println("I don't know");
        }
        


    }

    /**
     * {@snippet :
     *   a.myMethod()
     * }
     *
     */
    public void myMethod() {

    }
}

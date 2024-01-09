package org.acme;

import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.crypto.DecapsulateException;
import javax.crypto.KEM;

public class NewF {

    public enum Numbers {
        ONE, TWO, THREE
    }
    
    public static void main( String[] args ) throws NoSuchAlgorithmException, InvalidKeyException, DecapsulateException {

        String msg = """
                {
                    "name": Alex,
                    "age": 42
                }
                """;

        System.out.println(msg);


        String msg2 = """
                single \
                always""";

        System.out.println(msg2);

        //  breaks noise avoid fall through

        int counter = 3;

        switch (counter) {
            case 0 -> System.out.println("Not too much");
            case 1 -> System.out.println("Not too much");
            case 2 -> System.out.println("Not too much");
            case 3 -> System.out.println("It's ok");
            case 4 -> System.out.println("It's ok");
            case 5 -> System.out.println("It's ok");
            case 6 -> System.out.println("Too much");
            case 7 -> System.out.println("Too much");
            case 8 -> System.out.println("Too much");
        }

        // yield

        String result = switch (counter) {
            case 0 -> "Not too much";
            case 1 -> "Not too much";
            case 2 -> "Not too much";
            case 3 -> "It's ok";
            case 4 -> "It's ok";
            case 5 -> "It's ok";
            case 6 -> "Too much";
            case 7 -> "Too much";
            case 8 -> "Too much";
            case 9 -> {
                System.out.println("Fantastic");
                yield "Great";
            }
            default -> "Don't know";
        };

        System.out.println(result);

        Numbers one = Numbers.ONE;

        String result2 = switch(one) {
            case ONE -> "one";
            case TWO -> "two";
            case THREE -> "three";
            // no default
        };

        System.out.println(result2);

        Object numberObject = Integer.parseInt("1");

        if (numberObject instanceof Integer i) {
            System.out.println(i.intValue());
        }

        // i out of scope
        
        // Records

        var developers = Stream.of(new Developer("Alex", 42), new Developer("Ada", 10));
        developers.forEach(System.out::println);

        var developer = new Developer("Alexandra", 8);
        if (developer instanceof Developer(String name, int age)) {
            System.out.println(name + " " + age);
        }

        // Guarded switch

        Object price = 1000;

        String description = switch (price) {
          case Integer i when i > 900 -> "This is a big number " + i.toString();
          case Integer i -> "This is a normal number " + i.toString();
          case Long l -> "A long number " + l.toString();
          default -> price.toString();
        };

      System.out.println(description);
      
      try {
        final List<String> strings = List.of("1");
        strings.get(2);
      } catch (Exception _) {
        System.out.println("ups");
      }

      var name = "Duke";
      var info = STR."My name is \{name}";

      System.out.println(info);

      // Virtual Threads
      
      try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
        IntStream.range(0, 10).forEach(i -> {
          executor.submit(() -> {
            System.out.println(STR."\{i}");
          });
        });
      }
      
      // Structured Concurrency
      // If any of the subtask fails then no result is produced
      
      Random r = new Random();
      
      try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
        Supplier<Integer> number1  = scope.fork(r::nextInt);
        Supplier<Integer> number2 = scope.fork(r::nextInt);

          try {
              scope.join().throwIfFailed();
            System.out.println(number1.get());
            System.out.println(number2.get());
            
          } catch (ExecutionException | InterruptedException e) {
              throw new RuntimeException(e);
          }
          
      }

      var kpg = KeyPairGenerator.getInstance("X25519");
      var kp = kpg.generateKeyPair();

      // Sender 
      var kem1 = KEM.getInstance("DHKEM");
      var sender = kem1.newEncapsulator(kp.getPublic());
      var encapsulated = sender.encapsulate();
      var k1 = encapsulated.key();

      // Receiver 
      var kem2 = KEM.getInstance("DHKEM");
      var receiver = kem2.newDecapsulator(kp.getPrivate());
      var k2 = receiver.decapsulate(encapsulated.encapsulation());

      System.out.println(k2.equals(k1));
      
      ScopedValue.where(NAME, "Ada").run(() ->
          System.out.println(NAME.get()));
      
      ScopedValue.where(NAME, "Aixa").run(() ->
          System.out.println(NAME.get()));
      // END METHOD
    }

  private static final ScopedValue<String> NAME = ScopedValue.newInstance();

}

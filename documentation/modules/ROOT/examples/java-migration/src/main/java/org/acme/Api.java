package org.acme;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.InstantSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class Api {
  
  public record Stock(String name, int value){}
  
  public static void main(String[] args) throws IOException {

    String message = "Hello World";
    System.out.println(message);

    // NEW JAVA 12

    // NEW
    System.out.println(message.indent(4));

    // NEW
    System.out.println((String) message.transform(String::toUpperCase));

    // NEW
    Path path1 = Paths.get("src/main/resources/a.txt");
    Path path2 = Paths.get("src/main/resources/b.txt");
    System.out.println(Files.mismatch(path1, path1) == -1 ? "Equal" : "Not Equal");
    System.out.println(Files.mismatch(path1, path2) + " is the first different char");

    // NEW
    NumberFormat shortN = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
    NumberFormat longN = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.LONG);

    System.out.println("      628,969 short -> " + shortN.format(628_969));
    System.out.println("      628,969 short -> " + longN.format(628_969));

    // NEW JAVA 13
    Path zipFile = Paths.get("src/main/resources/fs.zip");
    try (FileSystem zipFileSys = FileSystems.newFileSystem(zipFile);) {
      zipFileSys.getRootDirectories().forEach(System.out::println);
    }

    // NEW JAVA 15

    System.out.println("my name is %s and I am %d years old".formatted("Alex", 42));

    String msg = "Hello \\nWorld";
    System.out.println(msg);
    System.out.println(msg.translateEscapes());
    // NEW JAVA 14

    List<String> collect = Stream.of("foo", "bar", "baz").collect(Collectors.toList());
    System.out.println(collect);

    List<String> newList = Stream.of("foo", "bar", "baz").toList();
    System.out.println(newList);

    LocalDateTime ldt = LocalDateTime.of(2012, 12, 5, 23, 11, 0);

    System.out.println(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy, h:mm B", Locale.US).format(ldt));

    RandomGenerator randomGenerator = new Random();
    RandomGenerator randomGenerator2 = RandomGenerator.of("L128X256MixRandom");

    System.out.println(randomGenerator.nextInt(10));
    System.out.println(randomGenerator2.nextInt(10));

    // JDK 18
    System.out.println(Integer.MIN_VALUE/-1);
    try {
      System.out.println(Math.divideExact(Integer.MIN_VALUE, -1));
    } catch(ArithmeticException e) {
      System.out.println("Overflow");
    }
    
    // JDK 19
    final Locale ca = Locale.of("ca", "es");
    System.out.println(ca);

    BigInteger bi = new BigInteger("1000");
    System.out.println(bi.parallelMultiply(new BigInteger("1000")));

    // JDK 21
    // Gets the value or min or max
    System.out.println(Math.clamp(23, 25, 30));

    // JDK 21
    StringBuilder sb = new StringBuilder();
    sb.append("Hello").repeat(" world", 5);
    System.out.println(sb.toString());

    final InstantSource system = InstantSource.system();
    System.out.println(system.millis());

    Stock s1 = new Stock("A", 123);
    Stock s2 = new Stock("B", 12);
    Stock s3 = new Stock("C", 125);
    Stock s4 = new Stock("D", 1);

    List<Stock> result = List.of(s1, s2, s3, s4).stream().collect(
      Collectors.teeing(
        Collectors.maxBy(Comparator.comparing(Stock::value)),
        Collectors.minBy(Comparator.comparing(Stock::value)),
        (e1, e2) -> List.of(e1.get(), e2.get())
      ));

    System.out.println(result);

      List<String> elements = new ArrayList<>();
    elements.add(null);

    System.out.println(elements.get(0).toLowerCase());
  }
}

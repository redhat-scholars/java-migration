package org.acme;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.time.Instant;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;
import java.util.stream.Gatherers;
import javax.crypto.KDF;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;

public class Gatherings {
    
    public static List<String> titles = Arrays.asList("1", "2", "3", "4", "5", "6","7", "8","9"); 

    public static void main(String args[]) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        
        titles.stream()
          .gather(Gatherers.windowFixed(3))
          .forEach(System.out::println);

        titles.stream()
          .gather(Gatherers.windowSliding(2))
          .forEach(System.out::println);

      titles.stream()
        .gather(Gatherers.fold(
          () -> "List of numbers: ",
          (result, num) -> result + num + ", "))
        .forEach(System.out::println);

      titles.stream()
        .gather(Gatherers.scan(
          () -> "List of numbers: ",
          (result, num) -> result + num + ", "))
        .forEach(System.out::println);

      titles.stream()
        .gather(Gatherers.mapConcurrent(2,
           word -> "- " + word
          )
        )
        .forEach(System.out::println);

      final Instant now = Instant.now();
      final Instant plusSeconds = now.plusSeconds(50);
      System.out.println(now.until(plusSeconds));

      KDF hkdf = KDF.getInstance("HKDF-SHA256");
      AlgorithmParameterSpec params =
        HKDFParameterSpec.ofExtract()
          .addIKM("the super secret passphrase".getBytes(StandardCharsets.UTF_8))
          .addSalt("the salt".getBytes(StandardCharsets.UTF_8))
          .thenExpand("my derived key description".getBytes(StandardCharsets.UTF_8), 32);

      SecretKey key = hkdf.deriveKey("AES", params);

      System.out.println("key = " + HexFormat.of().formatHex(key.getEncoded()));
      
    }

}

package org.gosloto.example.performance;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import lombok.Setter;
import org.gosloto.example.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RandomUserCreator {

  @Setter
  @Value("${app.counter}")
  private int counter;

  public User randomUser() {
    return User.builder()
        .id(new Random().nextInt(counter))
        .login(randomString())
        .email(randomString())
        .last_name(randomString())
        .first_name(randomString())
        .build();
  }

  public String randomString() {
    byte[] array = new byte[20];
    new Random().nextBytes(array);
    return new String(array, StandardCharsets.UTF_8);
  }

}

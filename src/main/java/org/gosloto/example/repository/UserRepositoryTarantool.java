package org.gosloto.example.repository;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.gosloto.example.model.User;
import org.springframework.stereotype.Repository;
import org.tarantool.Iterator;
import org.tarantool.TarantoolClient;

@Repository
@AllArgsConstructor
public class UserRepositoryTarantool {

  private final TarantoolClient tarantoolClient;

  private static final String SPACE_NAME = "user";


  public User save(User user) {
    final var resultList = tarantoolClient.syncOps().insert(
        SPACE_NAME,
        List.of(user.getId(), user.getFirst_name(), user.getLast_name(), user.getLogin(), user.getEmail())
    );
    return User.fromTarantool(resultList);
  }


  public User findById(long id) {
    final var resultList = tarantoolClient.syncOps().select(
        SPACE_NAME,
        "primary",
        Collections.singletonList(id),
        0,
        1,
        Iterator.EQ
    );
    return User.fromTarantool(resultList);
  }

}

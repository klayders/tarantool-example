package org.gosloto.example.web;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.gosloto.example.model.User;
import org.gosloto.example.repository.UserRepositoryDB;
import org.gosloto.example.repository.UserRepositoryTarantool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {


  private final UserRepositoryDB userRepositoryDB;
  private final UserRepositoryTarantool userRepositoryTarantool;


  @GetMapping("/find")
  User find(@RequestParam long id) {
    final Optional<User> byId = userRepositoryDB.findById(id);
    return userRepositoryTarantool.findById(id);
  }

  @GetMapping("/add")
  User add(@RequestParam long id) {
    var usr = new User(id, "das", "dasd", "log", "email");
    userRepositoryDB.save(usr);
    return userRepositoryTarantool.save(usr);
  }
}

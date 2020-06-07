package org.gosloto.example.web;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tarantool.Iterator;
import org.tarantool.TarantoolClient;

@RestController
@AllArgsConstructor
@RequestMapping("/band")
public class TarantoolController {

  private final TarantoolClient tarantoolClient;

  @GetMapping("/find")
  List<?> find(@RequestParam int key){

    final List<?> select = tarantoolClient.syncOps().select(
        "tester",
        "primary",
        Collections.singletonList(key),
        0,
        1,
        Iterator.EQ
    );

    return select;
  }

  @GetMapping("/add")
  List<?> add(@RequestParam int key){


    final List<?> insert = tarantoolClient.syncOps().insert(
        "tester",
        List.of(key, "simple", 1234)
    );

    return insert;
  }
}

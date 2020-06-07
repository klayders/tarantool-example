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
public class BandController {

  private final TarantoolClient tarantoolClient;

  private static final String SPACE_NAME = "tester";


  @GetMapping("/find")
  List<?> find(@RequestParam int key){

    return tarantoolClient.syncOps().select(
        SPACE_NAME,
        "primary",
        Collections.singletonList(key),
        0,
        1,
        Iterator.EQ
    );
  }

  @GetMapping("/add")
  List<?> add(@RequestParam int key){
    return tarantoolClient.syncOps().insert(
        SPACE_NAME,
        List.of(key, "simple", 1234)
    );
  }
}

package org.gosloto.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tarantool.TarantoolClient;
import org.tarantool.TarantoolClientConfig;
import org.tarantool.TarantoolClientImpl;

@Configuration
public class TarantoolConfig {

  @Bean
  TarantoolClient tarantoolClient() {
    var config = new TarantoolClientConfig();
    config.username = "guest";

    return new TarantoolClientImpl("localhost:3301", config);
  }
}

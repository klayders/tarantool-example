package org.gosloto.example.performance.db;

import java.util.Random;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.gosloto.example.metrics.StorageMetrics;
import org.gosloto.example.model.User;
import org.gosloto.example.repository.UserRepositoryDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@Profile("pg")
@RequiredArgsConstructor
public class UserSelectorDB {

  private final UserRepositoryDB userRepositoryDB;
  private final StorageMetrics storageMetrics;

  private static final String POSTGRES = "postgres";

  @Setter
  @Value("${app.counter}")
  private int counter;

  @PostConstruct
  void initReactiveRepo() {

    Flux.range(0, counter)
        .map(integer -> userRepositoryDB.findById((long) new Random().nextInt(counter)))
        .onErrorContinue((throwable, ss) -> new User())
        .switchIfEmpty(ss -> new User())
        .doOnNext(ignore -> storageMetrics.incrSelect(POSTGRES))
        .subscribeOn(Schedulers.parallel())
        .subscribe();
  }


}

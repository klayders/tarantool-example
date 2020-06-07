package org.gosloto.example.performance.tarantool;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.gosloto.example.metrics.StorageMetrics;
import org.gosloto.example.model.User;
import org.gosloto.example.performance.RandomUserCreator;
import org.gosloto.example.repository.UserRepositoryTarantool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@Profile("tarantool")
@RequiredArgsConstructor
public class UserCreatorTarantool {

  private final UserRepositoryTarantool userRepositoryTarantool;
  private final RandomUserCreator randomUserCreator;
  private final StorageMetrics storageMetrics;

  private static final String TARANTOOL = "tarantool";

  @Setter
  @Value("${app.counter}")
  private int counter;

  @PostConstruct
  void initReactiveRepo() {
    Flux.range(0, counter)
        .map(integer -> userRepositoryTarantool.save(randomUserCreator.randomUser()))
        .onErrorContinue((throwable, ss) -> new User())
        .doOnNext(ignore -> storageMetrics.incrInsert(TARANTOOL))
        .subscribeOn(Schedulers.parallel())
        .subscribe();
  }

}

package org.gosloto.example.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class StorageMetrics {


  private final MeterRegistry meterRegistry;

  public void incrInsert(String storageName) {
    var tags = makeTags(storageName);
    meterRegistry.counter("insert", tags).increment();
  }


  public void incrSelect(String storageName) {
    var tags = makeTags(storageName);
    meterRegistry.counter("select", tags).increment();
  }


  private List<Tag> makeTags(String storageName) {
    return List.of(
        Tag.of("storage", storageName)
    );
  }


}

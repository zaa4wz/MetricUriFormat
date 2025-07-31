package com.example.metricuriformat;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

  private static final String HELLO_WORD_URL = "/hello-word";
  private static final String HELLO_WORD_VARIABLE_ID = "/hello-word-variable/{id}";
  private static final String HELLO_WORD_PARAM_URL = "/hello-word-param?id={id}";

  private final RestTemplate restTemplate;
  private final RestClient restClient;
  private final TestHttpClient testHttpClient;
  private final MeterRegistry meterRegistry;

  @EventListener(ApplicationReadyEvent.class)
  public void doSomethingAfterStartup() {
    log.info("Hello world, I have just started up");
    restTemplate.getForObject(HELLO_WORD_URL, String.class);
    removeMetrics();
    log.info("");

    log.info("RestTemplate");
    restTemplate.getForObject(HELLO_WORD_URL, String.class);
    restTemplate.getForObject(HELLO_WORD_VARIABLE_ID, String.class, 1);
    restTemplate.getForObject(HELLO_WORD_VARIABLE_ID, String.class, 2);
    restTemplate.getForObject(HELLO_WORD_PARAM_URL, String.class, 1);
    restTemplate.getForObject(HELLO_WORD_PARAM_URL, String.class, 2);
    printRegisterUris();
    removeMetrics();

    log.info("RestClient");
    restClient.get().uri(HELLO_WORD_URL).retrieve().body(String.class);
    restClient.get().uri(HELLO_WORD_VARIABLE_ID, 1).retrieve().body(String.class);
    restClient.get().uri(HELLO_WORD_VARIABLE_ID, 2).retrieve().body(String.class);
    restClient.get().uri(HELLO_WORD_PARAM_URL, 1).retrieve().body(String.class);
    restClient.get().uri(HELLO_WORD_PARAM_URL, 2).retrieve().body(String.class);
    printRegisterUris();
    removeMetrics();

    log.info("HTTP Interface");
    testHttpClient.getHelloWord();
    testHttpClient.getHelloWordVariable(1);
    testHttpClient.getHelloWordVariable(2);
    testHttpClient.getHelloWordParam(1);
    testHttpClient.getHelloWordParam(2);
    printRegisterUris();
  }

  private void printRegisterUris() {
    log.info("Registered URIs:");
    var metric = restTemplate.getForObject("/actuator/metrics/http.client.requests", MetricResponse.class);
    metric.availableTags().stream()
        .filter(tag -> "uri".equals(tag.tag))
        .map(AvailableTag::values)
        .flatMap(List::stream)
        .forEach(log::info);
    log.info("");
  }

  private void removeMetrics() {
    meterRegistry.getMeters()
        .forEach(meterRegistry::remove);
  }

  record MetricResponse(
      String name,
      String baseUnit,
      List<AvailableTag> availableTags) {

  }

  record AvailableTag(
      String tag,
      List<String> values) {

  }
}

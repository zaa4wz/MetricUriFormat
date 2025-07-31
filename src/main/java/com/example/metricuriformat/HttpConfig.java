package com.example.metricuriformat;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpConfig {

  private static final String BASE_URL = "http://localhost:8080";

  @Bean
  public RestTemplate exampleRestTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder
        .rootUri(BASE_URL)
        .build();
  }

  @Bean
  public RestClient exampleRestClient(RestClient.Builder restClientBuilder) {
    return restClientBuilder
        .baseUrl(BASE_URL)
        .build();
  }

  @Bean
  public TestHttpClient exampleHttpClient(RestClient.Builder restClientBuilder) {
    RestClient restClient = restClientBuilder
        .baseUrl(BASE_URL)
        .build();
    RestClientAdapter adapter = RestClientAdapter.create(restClient);

    return HttpServiceProxyFactory.builderFor(adapter).build()
        .createClient(TestHttpClient.class);
  }
}

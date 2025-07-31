package com.example.metricuriformat;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface TestHttpClient {

  @GetExchange(url = "/hello-word")
  String getHelloWord();

  @GetExchange(url = "/hello-word-variable/{id}")
  String getHelloWordVariable(@PathVariable("id") long id);

  @GetExchange(url = "/hello-word-param")
  String getHelloWordParam(@RequestParam("id") long id);
}

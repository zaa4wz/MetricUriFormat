package com.example.metricuriformat;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  @GetMapping("/hello-word")
  String getHelloWord() {
    return "Hello Word";
  }

  @GetMapping("/hello-word-variable/{id}")
  String getHelloWordVariable(@PathVariable("id") long id) {
    return "Hello Word";
  }

  @GetMapping("/hello-word-param")
  String getHelloWordParam(@RequestParam("id") long id) {
    return "Hello Word";
  }
}

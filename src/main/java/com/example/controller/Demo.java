package com.example.controller;

import java.lang.String;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1")
class Demo {
  @GetMapping("/ping")
  void ping(@RequestParam("id") String id) {
    ResponseData resData = helper.methodName();
  }

  @PostMapping("/ping2")
  void ping2(@RequestBody String id) {
    ResponseData resData = helper.methodName();
  }
}

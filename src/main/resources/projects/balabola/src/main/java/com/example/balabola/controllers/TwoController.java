package com.example.balabola.controllers;

import java.lang.String;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TwoController")
public class TwoController {
  @PostMapping("/someAdd")
  public void post(@RequestBody String name) {

  }
}

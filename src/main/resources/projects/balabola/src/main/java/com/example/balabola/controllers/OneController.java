package com.example.balabola.controllers;

import java.lang.String;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/OneController")
public class OneController {
  @GetMapping("")
  public String get() {
    return "Hello, World!!!";
  }
}

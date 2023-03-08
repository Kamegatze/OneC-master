package com.example.balabola.controllers;

import java.lang.String;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ThreeController")
public class ThreeController {
  @GetMapping("/getAll/{firstName}/{lastName}")
  public String getAll(@PathVariable String firstName, @PathVariable String lastName) {
    return firstName + lastName;
  }
}

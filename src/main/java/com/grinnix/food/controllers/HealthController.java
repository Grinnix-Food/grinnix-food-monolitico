package com.grinnix.food.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {

  @GetMapping
  public ResponseEntity<Void> findAll() {
    return ResponseEntity.ok().build();
  }
}

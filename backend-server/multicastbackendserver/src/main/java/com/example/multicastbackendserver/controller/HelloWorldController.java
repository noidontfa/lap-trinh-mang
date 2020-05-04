package com.example.multicastbackendserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/")
    public ResponseEntity helloWorld() {
        return ResponseEntity.ok("Hello world 1");
    }
}

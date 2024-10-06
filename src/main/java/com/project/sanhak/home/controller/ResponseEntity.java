package com.project.sanhak.home.controller;

public class ResponseEntity<T> {
    public static ResponseEntity<String> ok(String helloFromSpringBoot) {
        return ResponseEntity.ok("ok");
    }
}

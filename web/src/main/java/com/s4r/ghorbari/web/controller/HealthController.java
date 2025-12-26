package com.s4r.ghorbari.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "message", "Application is running"
        ));
    }

    @GetMapping("/test-auth")
    public ResponseEntity<?> testAuth() {
        return ResponseEntity.ok(Map.of(
                "message", "Public endpoint - no auth required",
                "timestamp", System.currentTimeMillis()
        ));
    }
}

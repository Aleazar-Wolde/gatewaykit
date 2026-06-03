package com.gatewaykit.gatewaykit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Simple health endpoint used to verify
 * that the gateway is running.
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health(){
        return Map.of(
                "status","health","uptime_seconds",0
        );
    }
}

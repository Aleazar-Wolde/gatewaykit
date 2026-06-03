package com.gatewaykit.gatewaykit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockUpstreamController {

    @GetMapping("/mock-users")
    public String users() {
        return "Users service response";
    }
}
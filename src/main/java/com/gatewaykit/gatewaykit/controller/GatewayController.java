package com.gatewaykit.gatewaykit.controller;

import com.gatewaykit.gatewaykit.config.RouteConfig;
import com.gatewaykit.gatewaykit.service.RouteMatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;

@RestController
public class GatewayController {

    private final RouteMatcher routeMatcher;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public GatewayController(RouteMatcher routeMatcher) {
        this.routeMatcher = routeMatcher;
    }

    @RequestMapping("/**")
    public ResponseEntity<String> handle(HttpServletRequest request) throws Exception {
        String path = request.getRequestURI();
        String method = request.getMethod();

        RouteConfig route = routeMatcher.findRoute(path);

        if (route == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"route_not_found\"}");
        }

        if (route.getMethods() != null && !route.getMethods().contains(method)) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body("{\"error\":\"method_not_allowed\"}");
        }

        String upstreamUrl = route.getUpstream().getUrl() + path;

        HttpRequest upstreamRequest = HttpRequest.newBuilder()
                .uri(URI.create(upstreamUrl))
                .method(method, HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> upstreamResponse = httpClient.send(
                upstreamRequest,
                HttpResponse.BodyHandlers.ofString()
        );

        return ResponseEntity
                .status(upstreamResponse.statusCode())
                .headers(httpHeaders -> httpHeaders.putAll(Collections.emptyMap()))
                .body(upstreamResponse.body());
    }
}
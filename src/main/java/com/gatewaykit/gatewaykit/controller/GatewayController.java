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

/**
 * Main entry point for gateway traffic.
 *
 * Responsibilities:
 * - Route matching
 * - Method validation
 * - Proxying requests to upstream services
 */
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

        // Find the route configuration for the incoming request.
        RouteConfig route = routeMatcher.findRoute(path);

        // Return 404 when no configured route matches.
        if (route == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"route_not_found\"}");
        }

        // Return 405 when the HTTP method is not allowed.
        if (route.getMethods() != null && !route.getMethods().contains(method)) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body("{\"error\":\"method_not_allowed\"}");
        }

        // Forward the request to the configured upstream service.
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
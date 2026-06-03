package com.gatewaykit.gatewaykit.service;

import com.gatewaykit.gatewaykit.config.RouteConfig;
import org.springframework.stereotype.Service;

/**
 * Responsible for finding the matching route
 * configuration for an incoming request path.
 */
@Service
public class RouteMatcher {

    private final ConfigLoader configLoader;

    public RouteMatcher(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    public RouteConfig findRoute(String path) {

        // Search all configured routes and return
        // the first route whose path matches.
        for (RouteConfig route : configLoader
                .getGatewayConfig()
                .getRoutes()) {

            if (route.getPath().equals(path)) {
                return route;
            }
        }

        return null;
    }
}
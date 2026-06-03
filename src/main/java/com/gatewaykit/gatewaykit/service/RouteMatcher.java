package com.gatewaykit.gatewaykit.service;

import com.gatewaykit.gatewaykit.config.RouteConfig;
import org.springframework.stereotype.Service;

@Service
public class RouteMatcher {

    private final ConfigLoader configLoader;

    public RouteMatcher(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    public RouteConfig findRoute(String path) {

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
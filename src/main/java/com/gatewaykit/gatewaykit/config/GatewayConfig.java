package com.gatewaykit.gatewaykit.config;

import java.util.ArrayList;
import java.util.List;

public class GatewayConfig {
    private GatewaySettings gateway;
    private List<RouteConfig> routes = new ArrayList<>();

    public GatewaySettings getGateway() {
        return gateway;
    }

    public void setGateway(GatewaySettings gateway) {
        this.gateway = gateway;
    }

    public List<RouteConfig> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteConfig> routes) {
        this.routes = routes;
    }
}
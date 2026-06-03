package com.gatewaykit.gatewaykit.config;

import java.util.List;

public class RouteConfig {
    private String path;
    private List<String> methods;
    private boolean stripPrefix;
    private UpstreamConfig upstream;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public boolean isStripPrefix() {
        return stripPrefix;
    }

    public void setStripPrefix(boolean stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    public UpstreamConfig getUpstream() {
        return upstream;
    }

    public void setUpstream(UpstreamConfig upstream) {
        this.upstream = upstream;
    }
}
package com.gatewaykit.gatewaykit.service;

import com.gatewaykit.gatewaykit.config.GatewayConfig;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.LoaderOptions;

import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class ConfigLoader {

    private final GatewayConfig gatewayConfig;

    public ConfigLoader() {
        this.gatewayConfig = loadConfig();
    }

    private GatewayConfig loadConfig() {
        String configPath = System.getenv().getOrDefault("GATEWAY_CONFIG", "gateway.yaml");

        try (InputStream inputStream = new FileInputStream(configPath)) {
            Yaml yaml = new Yaml(new Constructor(GatewayConfig.class, new LoaderOptions()));
            return yaml.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load gateway config from: " + configPath, e);
        }
    }

    public GatewayConfig getGatewayConfig() {
        return gatewayConfig;
    }
}
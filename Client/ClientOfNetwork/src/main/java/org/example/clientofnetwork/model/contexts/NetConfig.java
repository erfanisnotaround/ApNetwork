package org.example.clientofnetwork.model.contexts;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class NetConfig {
    public String host;
    public int port;

    static NetConfig load(ObjectMapper json) {
        try (var is = NetConfig.class.getClassLoader().getResourceAsStream("/org/example/clientofnetwork/configs/net-config.json")) {
            if (is == null) throw new IllegalStateException("net-config.json not found");
            return json.readValue(is, NetConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load net-config.json", e);
        }
    }
}
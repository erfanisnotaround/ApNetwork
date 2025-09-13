package org.example.clientofnetwork.model.contexts;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

public final class NetConfig {
    public String host;
    public int port;

    public static NetConfig load(ObjectMapper json) {
        final String path = "/org/example/clientofnetwork/configs/net-config.json";
        try (InputStream is = NetConfig.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalStateException("net-config.json not found at " + path);
            }
            return json.readValue(is, NetConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load net-config.json", e);
        }
    }
}
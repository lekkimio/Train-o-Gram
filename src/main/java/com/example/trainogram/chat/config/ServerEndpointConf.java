package com.example.trainogram.chat.config;

import org.springframework.context.annotation.Configuration;

import javax.websocket.Extension;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Collections;
import java.util.List;

@Configuration
public class ServerEndpointConf extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(sec, request, response);
        sec.getUserProperties().put("userAgent", getHeaderValue(request, "User-Agent"));
        sec.getUserProperties().put("requestedExtensions", getHeaderValue(request, "Sec-WebSocket-Extensions"));
    }

    private String getHeaderValue(HandshakeRequest request, String key) {
        List<String> value = request.getHeaders().get(key);
        return String.join(",", value);
    }

    @Override
    public List<Extension> getNegotiatedExtensions(List<Extension> installed, List<Extension> requested) {
        return Collections.emptyList();
    }

}

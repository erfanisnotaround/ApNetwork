package org.example.clientofnetwork.model.passingAndRecievingData.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.KindOfCommunication;

import java.time.Instant;

public class EnvelopeBuilder {
    private final ObjectMapper json;
    public EnvelopeBuilder(ObjectMapper json) { this.json = json; }

    public <P> String buildRequest(String id , String username, CommandType cmd, String token, P dataPas) {
        ObjectNode root = json.createObjectNode();

        root.put("userName" , username);
        root.put("id",id);
        root.put("kindOfCommunication", KindOfCommunication.REQUEST.name());
        root.put("commandType", cmd.name());

//        root.put("ts", Instant.now().toString());
        if (dataPas != null) root.set("dataPas", json.valueToTree(dataPas));
        try { return json.writeValueAsString(root); }
        catch (Exception e) { throw new RuntimeException("Cannot serialize envelope", e); }
    }
}

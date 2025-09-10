package org.example.clientofnetwork.model.passingAndRecievingData.process;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;

public interface ResponseListener {
    void onNotify(EnvelopeData<Void, JsonNode> env);
    void onResponse(String id, CommandType cmd, EnvelopeData<?, ?> env); // central dispatch
}
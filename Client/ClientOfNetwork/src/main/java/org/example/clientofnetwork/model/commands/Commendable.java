package org.example.clientofnetwork.model.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;

public interface Commendable<R , T>{
    CommandType type();

    Object buildArgs(ClientInfo clientInfo , T data);

    TypeReference<R> responseType();

    void onSuccess(EnvelopeData< Void , R> env, ClientInfo clientInfo);

    void onFailure(EnvelopeData< Void , R> env, ClientInfo clientInfo);
}

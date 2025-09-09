package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.RegisterInformation;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.RegisterDataRes;

public class RegisterCommand implements Commendable<RegisterDataRes , RegisterInformation> {
    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, RegisterInformation data) {
        return null;
    }

    @Override
    public TypeReference<RegisterDataRes> responseType() {
        return null;
    }

    @Override
    public void onSuccess(EnvelopeData<Void, RegisterDataRes> env, ClientInfo clientInfo) {

    }

    @Override
    public void onFailure(EnvelopeData<Void, RegisterDataRes> env, ClientInfo clientInfo) {

    }
}

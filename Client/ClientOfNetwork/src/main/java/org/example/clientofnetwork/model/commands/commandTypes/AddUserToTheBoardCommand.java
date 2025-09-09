package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.AddingUserToTheBoardInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.AddingUserToTheBoardDataRes;

public class AddUserToTheBoardCommand implements Commendable<AddingUserToTheBoardDataRes , AddingUserToTheBoardInfo> {
    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, AddingUserToTheBoardInfo data) {
        return null;
    }

    @Override
    public TypeReference<AddingUserToTheBoardDataRes> responseType() {
        return null;
    }

    @Override
    public void onSuccess(EnvelopeData<Void, AddingUserToTheBoardDataRes> env, ClientInfo clientInfo) {

    }

    @Override
    public void onFailure(EnvelopeData<Void, AddingUserToTheBoardDataRes> env, ClientInfo clientInfo) {

    }
}

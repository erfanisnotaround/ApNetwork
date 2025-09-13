package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.AddingUserToTheBoardPass;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.AddingUserToTheBoardRes;

public class AddUserToTheBoardCommand implements Commendable<AddingUserToTheBoardPass, AddingUserToTheBoardRes> {
    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, AddingUserToTheBoardPass data) {
        return null;
    }

    @Override
    public TypeReference<AddingUserToTheBoardRes> responseType() {
        return null;
    }

    @Override
    public void onSuccess(EnvelopeData<Void, AddingUserToTheBoardRes> env, ClientInfo clientInfo) {

    }

    @Override
    public void onFailure(EnvelopeData<Void, AddingUserToTheBoardRes> env, ClientInfo clientInfo) {

    }
}

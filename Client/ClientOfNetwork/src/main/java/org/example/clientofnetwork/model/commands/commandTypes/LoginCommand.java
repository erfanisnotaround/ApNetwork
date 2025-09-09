package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.LoginInformation;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.LoginDataRes;

public class LoginCommand implements Commendable<LoginDataRes, LoginInformation> {

    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, LoginInformation data) {

    }


    @Override
    public TypeReference<LoginDataRes> responseType() {
        return null;
    }

    @Override
    public void onSuccess(EnvelopeData<Void, LoginDataRes> env, ClientInfo clientInfo) {

    }

    @Override
    public void onFailure(EnvelopeData<Void, LoginDataRes> env, ClientInfo clientInfo) {

    }
}

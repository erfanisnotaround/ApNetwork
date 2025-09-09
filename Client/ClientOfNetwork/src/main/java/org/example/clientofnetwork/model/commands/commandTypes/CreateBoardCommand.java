package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.CreateBoardInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.CreateBoardDataRes;

public class CreateBoardCommand implements Commendable<CreateBoardDataRes , CreateBoardInfo> {

    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, CreateBoardInfo data) {
        return null;
    }

    @Override
    public TypeReference<CreateBoardDataRes> responseType() {
        return null;
    }

    @Override
    public void onSuccess(EnvelopeData<Void, CreateBoardDataRes> env, ClientInfo clientInfo) {

    }

    @Override
    public void onFailure(EnvelopeData<Void, CreateBoardDataRes> env, ClientInfo clientInfo) {

    }
}

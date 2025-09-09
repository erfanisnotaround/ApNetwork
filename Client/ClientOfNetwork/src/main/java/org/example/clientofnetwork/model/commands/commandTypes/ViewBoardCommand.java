package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.ViewBoardINfo;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.ViewBoardDataRes;

public class ViewBoardCommand implements Commendable<ViewBoardDataRes , ViewBoardINfo> {
    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, ViewBoardINfo data) {
        return null;
    }

    @Override
    public TypeReference<ViewBoardDataRes> responseType() {
        return null;
    }

    @Override
    public void onSuccess(EnvelopeData<Void, ViewBoardDataRes> env, ClientInfo clientInfo) {

    }

    @Override
    public void onFailure(EnvelopeData<Void, ViewBoardDataRes> env, ClientInfo clientInfo) {

    }
}

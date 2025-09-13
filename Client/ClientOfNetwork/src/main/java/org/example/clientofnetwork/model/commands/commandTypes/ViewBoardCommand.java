package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.ViewBoardPass;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.ViewBoardRes;

public class ViewBoardCommand implements Commendable<ViewBoardPass, ViewBoardRes> {
    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, ViewBoardPass data) {
        return null;
    }

    @Override
    public TypeReference<ViewBoardRes> responseType() {
        return null;
    }

    @Override
    public void onSuccess(EnvelopeData<Void, ViewBoardRes> env, ClientInfo clientInfo) {

    }

    @Override
    public void onFailure(EnvelopeData<Void, ViewBoardRes> env, ClientInfo clientInfo) {

    }
}

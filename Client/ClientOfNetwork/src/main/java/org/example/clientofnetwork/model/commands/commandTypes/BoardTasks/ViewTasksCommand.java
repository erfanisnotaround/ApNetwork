package org.example.clientofnetwork.model.commands.commandTypes.BoardTasks;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands.ViewTasksInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.responseBoardInfo.ViewTasksDataRes;

public class ViewTasksCommand implements Commendable<ViewTasksInfo , ViewTasksDataRes> {
    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, ViewTasksInfo data) {
        return null;
    }

    @Override
    public TypeReference<ViewTasksDataRes> responseType() {
        return null;
    }

    @Override
    public void onSuccess(EnvelopeData<Void, ViewTasksDataRes> env, ClientInfo clientInfo) {

    }

    @Override
    public void onFailure(EnvelopeData<Void, ViewTasksDataRes> env, ClientInfo clientInfo) {

    }
}

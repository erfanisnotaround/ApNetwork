package org.example.clientofnetwork.model.commands.commandTypes.BoardTasks;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands.DeleteTasksInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.responseBoardInfo.DeleteTaskDataRes;

public class DeleteTaskCommand implements Commendable<DeleteTaskDataRes , DeleteTasksInfo> {
    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, DeleteTasksInfo data) {
        return null;
    }

    @Override
    public TypeReference<DeleteTaskDataRes> responseType() {
        return null;
    }

    @Override
    public void onSuccess(EnvelopeData<Void, DeleteTaskDataRes> env, ClientInfo clientInfo) {

    }

    @Override
    public void onFailure(EnvelopeData<Void, DeleteTaskDataRes> env, ClientInfo clientInfo) {

    }
}

package org.example.clientofnetwork.model.commands.commandTypes.BoardTasks;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands.UpdateTaskStatusInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.responseBoardInfo.UpdateTaskStatusDateRes;

public class UpdateTaskStatusCommand implements Commendable<UpdateTaskStatusDateRes , UpdateTaskStatusInfo> {

    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, UpdateTaskStatusInfo data) {
        return null;
    }

    @Override
    public TypeReference<UpdateTaskStatusDateRes> responseType() {
        return null;
    }

    @Override
    public void onSuccess(EnvelopeData<Void, UpdateTaskStatusDateRes> env, ClientInfo clientInfo) {

    }

    @Override
    public void onFailure(EnvelopeData<Void, UpdateTaskStatusDateRes> env, ClientInfo clientInfo) {

    }
}


package org.example.clientofnetwork.model.commands.commandTypes.BoardTasks;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands.AddTaskInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.responseBoardInfo.AddTaskDAtaRes;

public class AddTaskCommand implements Commendable<AddTaskInfo , AddTaskDAtaRes> {
    @Override
    public CommandType type() {
        return null;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, AddTaskInfo data) {
        return null;
    }

    @Override
    public TypeReference<AddTaskDAtaRes> responseType() {
        return null;
    }

    @Override
    public void onSuccess(EnvelopeData<Void, AddTaskDAtaRes> env, ClientInfo clientInfo) {

    }

    @Override
    public void onFailure(EnvelopeData<Void, AddTaskDAtaRes> env, ClientInfo clientInfo) {

    }
}

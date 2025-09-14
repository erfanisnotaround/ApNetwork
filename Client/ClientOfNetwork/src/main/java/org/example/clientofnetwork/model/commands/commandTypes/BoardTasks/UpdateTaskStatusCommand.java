package org.example.clientofnetwork.model.commands.commandTypes.BoardTasks;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands.UpdateTaskStatusPass;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.responseBoardInfo.UpdateTaskStatusRes;

import java.util.function.Consumer;

public class UpdateTaskStatusCommand implements Commendable<UpdateTaskStatusPass, UpdateTaskStatusRes> {

    private final Consumer<UpdateTaskStatusRes> onOk;
    private final Consumer<String> onFail;

    public UpdateTaskStatusCommand(Consumer<UpdateTaskStatusRes> onOk, Consumer<String> onFail) {
        this.onOk = onOk!=null? onOk : r->{}; this.onFail = onFail!=null? onFail : m->{};
    }

    @Override
    public CommandType type() {
        return CommandType.UPDATE_TASK_STATUS;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, UpdateTaskStatusPass data) {
        return data;
    }

    @Override
    public TypeReference<UpdateTaskStatusRes> responseType() {
        return new TypeReference<UpdateTaskStatusRes>() {};
    }

    @Override
    public void onSuccess(EnvelopeData<Void, UpdateTaskStatusRes> env, ClientInfo clientInfo) {
        onOk.accept(env.getDataRec());
    }

    @Override
    public void onFailure(EnvelopeData<Void, UpdateTaskStatusRes> env, ClientInfo clientInfo) {
        onFail.accept(env.getMessage());
    }
}


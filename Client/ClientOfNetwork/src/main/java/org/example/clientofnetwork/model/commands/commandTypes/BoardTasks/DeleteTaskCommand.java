package org.example.clientofnetwork.model.commands.commandTypes.BoardTasks;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands.DeleteTaskPass;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.responseBoardInfo.DeleteTaskRes;

import java.util.function.Consumer;

public class DeleteTaskCommand implements Commendable<DeleteTaskPass, DeleteTaskRes> {
    private final Consumer<DeleteTaskRes> onOk; private final Consumer<String> onFail;
    public DeleteTaskCommand(Consumer<DeleteTaskRes> ok, Consumer<String> fail){
        this.onOk = ok!=null? ok : r->{}; this.onFail = fail!=null? fail : m->{};
    }
    @Override public CommandType type(){ return CommandType.DELETE_TASK; }
    @Override public Object buildArgs(ClientInfo ci, DeleteTaskPass p){ return p; }
    @Override public TypeReference<DeleteTaskRes> responseType(){ return new TypeReference<>(){}; }
    @Override public void onSuccess(EnvelopeData<Void, DeleteTaskRes> env, ClientInfo ci){ onOk.accept(env.getDataRec()); }
    @Override public void onFailure(EnvelopeData<Void, DeleteTaskRes> env, ClientInfo ci){ onFail.accept(env.getMessage()); }
}


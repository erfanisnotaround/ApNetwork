package org.example.clientofnetwork.model.commands.commandTypes.BoardTasks;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands.AddTaskPass;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.responseBoardInfo.AddTaskRes;

import java.util.function.Consumer;


public class AddTaskCommand implements Commendable<AddTaskPass, AddTaskRes> {
    private final Consumer<AddTaskRes> onOk; private final Consumer<String> onFail;
    public AddTaskCommand(Consumer<AddTaskRes> ok, Consumer<String> fail){
        this.onOk = ok!=null? ok : r->{}; this.onFail = fail!=null? fail : m->{};
    }
    @Override public CommandType type(){ return CommandType.ADD_TASK; }
    @Override public Object buildArgs(ClientInfo ci, AddTaskPass p){ return p; }
    @Override public TypeReference<AddTaskRes> responseType(){ return new TypeReference<>(){}; }
    @Override public void onSuccess(EnvelopeData<Void, AddTaskRes> env, ClientInfo ci){ onOk.accept(env.getDataRec()); }
    @Override public void onFailure(EnvelopeData<Void, AddTaskRes> env, ClientInfo ci){ onFail.accept(env.getMessage()); }
}

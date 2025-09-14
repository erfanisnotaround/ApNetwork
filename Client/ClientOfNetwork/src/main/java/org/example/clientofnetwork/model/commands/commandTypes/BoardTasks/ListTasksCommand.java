package org.example.clientofnetwork.model.commands.commandTypes.BoardTasks;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands.ListTasksPass;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.responseBoardInfo.ListTasksRes;

import java.util.function.Consumer;

public class ListTasksCommand implements Commendable<ListTasksPass, ListTasksRes> {
    private final Consumer<ListTasksRes> onOk; private final Consumer<String> onFail;
    public ListTasksCommand(Consumer<ListTasksRes> ok, Consumer<String> fail){
        this.onOk = ok!=null? ok : r->{}; this.onFail = fail!=null? fail : m->{};
    }
    @Override public CommandType type(){ return CommandType.LIST_TASKS; }
    @Override public Object buildArgs(ClientInfo ci, ListTasksPass p){ return p; }
    @Override public TypeReference<ListTasksRes> responseType(){ return new TypeReference<>(){}; }
    @Override public void onSuccess(EnvelopeData<Void, ListTasksRes> env, ClientInfo ci){ onOk.accept(env.getDataRec()); }
    @Override public void onFailure(EnvelopeData<Void, ListTasksRes> env, ClientInfo ci){ onFail.accept(env.getMessage()); }
}

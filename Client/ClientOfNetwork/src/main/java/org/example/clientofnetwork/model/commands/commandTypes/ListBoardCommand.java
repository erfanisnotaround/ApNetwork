package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.BoardListPass;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.BoardListRes;

import java.util.function.Consumer;

public class ListBoardCommand implements Commendable<BoardListPass, BoardListRes> {


    private final Consumer<BoardListRes> onOk;
    private final Consumer<String> onFail;

    public ListBoardCommand(Consumer<BoardListRes> onOk, Consumer<String> onFail) {
        this.onOk  = (onOk  != null ? onOk  : r -> {});
        this.onFail= (onFail!= null ? onFail: m -> {});
    }
    @Override
    public CommandType type() {
        return CommandType.LIST_BOARDS;
    }

    @Override
    public BoardListPass buildArgs(ClientInfo clientInfo, BoardListPass data) {
        return data;
    }

    @Override
    public TypeReference<BoardListRes> responseType() {
        return new TypeReference<BoardListRes>() {};
    }

    @Override
    public void onSuccess(EnvelopeData<Void, BoardListRes> env, ClientInfo clientInfo) {
        onOk.accept(env.getDataRec());
    }

    @Override
    public void onFailure(EnvelopeData<Void, BoardListRes> env, ClientInfo clientInfo) {
        onFail.accept(env.getMessage());
    }
}

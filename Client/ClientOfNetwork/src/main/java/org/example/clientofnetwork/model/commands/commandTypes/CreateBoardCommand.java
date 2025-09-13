// org/example/clientofnetwork/model/commands/commandTypes/CreateBoardCommand.java
package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.function.Consumer;

import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;

import org.example.clientofnetwork.model.commands.buildInfo.CreateBoardPass;
import org.example.clientofnetwork.model.responseTypes.CreateBoardRes;

public final class CreateBoardCommand implements Commendable<CreateBoardPass , CreateBoardRes> {

    private final Consumer<CreateBoardRes> onOk;
    private final Consumer<String> onFail;

    public CreateBoardCommand(Consumer<CreateBoardRes> onOk, Consumer<String> onFail) {
        this.onOk  = (onOk  != null ? onOk  : r -> {});
        this.onFail= (onFail!= null ? onFail: m -> {});
    }

    @Override
    public CommandType type() {
        return CommandType.CREATE_BOARD;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, CreateBoardPass data) {
        return data;
    }

    @Override
    public TypeReference<CreateBoardRes> responseType() {
        return new TypeReference<>() {};
    }

    @Override
    public void onSuccess(EnvelopeData<Void, CreateBoardRes> env, ClientInfo clientInfo) {
        onOk.accept(env.getDataRec());
    }

    @Override
    public void onFailure(EnvelopeData<Void, CreateBoardRes> env, ClientInfo clientInfo) {
        onFail.accept(env.getMessage());
    }


}

package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.AddingUserToTheBoardPass;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.AddingUserToTheBoardRes;

import java.util.function.Consumer;

public class AddUserToTheBoardCommand implements Commendable<AddingUserToTheBoardPass, AddingUserToTheBoardRes> {

    private final Consumer<AddingUserToTheBoardRes> onOk;
    private final Consumer<String> onFail;

    public AddUserToTheBoardCommand(Consumer<AddingUserToTheBoardRes> onOk, Consumer<String> onFail) {
        this.onOk = (onOk != null ? onOk : r -> {});
        this.onFail = (onFail != null ? onFail : m -> {});
    }

    @Override
    public CommandType type() {
        return CommandType.ADD_USER_TO_BOARD;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, AddingUserToTheBoardPass data) {
        return data;
    }

    @Override
    public TypeReference<AddingUserToTheBoardRes> responseType() {
        return new TypeReference<>() {};
    }

    @Override
    public void onSuccess(EnvelopeData<Void, AddingUserToTheBoardRes> env, ClientInfo clientInfo) {
        onOk.accept(env.getDataRec());
    }

    @Override
    public void onFailure(EnvelopeData<Void, AddingUserToTheBoardRes> env, ClientInfo clientInfo) {
        onFail.accept(env.getMessage());
    }
}

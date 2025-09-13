package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.controller.uiChanging.types.FxRegisterPresenter;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.RegisterPass;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.RegisterRes;

public class RegisterCommand implements Commendable<RegisterPass, RegisterRes> {
    private final FxRegisterPresenter fxRegisterPresenter;

    public RegisterCommand(FxRegisterPresenter fxRegisterPresenter) {
        this.fxRegisterPresenter = fxRegisterPresenter;
    }
    @Override
    public CommandType type() {
        return CommandType.REGISTER;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, RegisterPass data) {
        return data;
    }

    @Override
    public TypeReference<RegisterRes> responseType() {
        return new TypeReference<RegisterRes>() {};
    }

    @Override
    public void onSuccess(EnvelopeData<Void, RegisterRes> env, ClientInfo clientInfo) {
        fxRegisterPresenter.showSuccess(env.getDataRec());
    }

    @Override
    public void onFailure(EnvelopeData<Void, RegisterRes> env, ClientInfo clientInfo) {
        fxRegisterPresenter.showFailure(env.getMessage());
    }
}

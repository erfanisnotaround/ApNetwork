package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.controller.uiChanging.types.interfaces.CommandPresenter;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.LoginPass;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.responseTypes.LoginRes;

public class LoginCommand implements Commendable<LoginPass, LoginRes> {


    CommandPresenter<LoginRes> loginPresenter;

    public LoginCommand(CommandPresenter<LoginRes> loginPresenter) { this.loginPresenter = loginPresenter; }
    @Override
    public CommandType type() {
        return CommandType.LOGIN;
    }

    @Override
    public Object buildArgs(ClientInfo clientInfo, LoginPass data) {
        return data;
    }


    @Override
    public TypeReference<LoginRes> responseType() {
        return new TypeReference<LoginRes>() {};
    }

    @Override
    public void onSuccess(EnvelopeData<Void, LoginRes> env, ClientInfo clientInfo) {
        var res = env.getDataRec();

        loginPresenter.showSuccess(env.getDataRec());
    }

    @Override
    public void onFailure(EnvelopeData<Void, LoginRes> env, ClientInfo clientInfo) {
        var msg = (env.getMessage() != null ? env.getMessage() : "Login failed");
        loginPresenter.showFailure(msg);
    }
}

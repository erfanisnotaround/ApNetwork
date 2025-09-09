package org.example.clientofnetwork.model.commands;

import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.process.ProtocolProcessor;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandResponseStatus;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.KindOfCommunication;

import java.util.concurrent.CompletableFuture;

public final class CommandManager {
    private final ProtocolProcessor processor;
    private final ClientInfo info;

    public CommandManager(ProtocolProcessor processor, ClientInfo info) {
        this.processor = processor;
        this.info = info;
    }

    public <R , T> CompletableFuture<EnvelopeData<Void,R>> execute(Commendable<R , T> cmd , T data) {
        return processor.send(cmd.type(), cmd.buildArgs(info , data ), cmd.responseType())
                .thenApply(env -> {
                    if (env != null && env.getKindOfCommunication() == KindOfCommunication.RESPONSE) {
                        if (CommandResponseStatus.FAILURE.equals(env.getStatus())) cmd.onFailure(env, info);
                        else if (CommandResponseStatus.SUCCESS.equals(env.getStatus()))cmd.onSuccess(env, info);
                    }
                    return env;
                });
    }
}

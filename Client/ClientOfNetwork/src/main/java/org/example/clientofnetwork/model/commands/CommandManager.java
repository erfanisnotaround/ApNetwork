package org.example.clientofnetwork.model.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.clientofnetwork.model.commands.commandTypes.Registered;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.process.ProtocolProcessor;
import org.example.clientofnetwork.model.passingAndRecievingData.process.ResponseListener;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandResponseStatus;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
public final class CommandManager implements ResponseListener {
    private final ProtocolProcessor processor;
    private final ClientInfo info;

    private final Map<String, Registered> inflight = new ConcurrentHashMap<>();

    // MULTICAST instead of single sink
    private final CopyOnWriteArrayList<Consumer<EnvelopeData<Void, JsonNode>>> notifySinks
            = new CopyOnWriteArrayList<>();

    public CommandManager(ProtocolProcessor processor, ClientInfo info) {
        this.processor = processor;
        this.info = info;
        this.processor.setListener(this);
    }

    /** Subscribe to NOTIFY events. */
    public void addNotifyListener(Consumer<EnvelopeData<Void, JsonNode>> sink) {
        if (sink != null) notifySinks.add(sink);
    }
    /** Optional: unsubscribe if you have lifecycle hooks. */
    public void removeNotifyListener(Consumer<EnvelopeData<Void, JsonNode>> sink) {
        notifySinks.remove(sink);
    }

    public <T , R> void send(Commendable<T , R> cmd, T data) {
        Object args = cmd.buildArgs(info, data);
        String id = processor.sendReactive(cmd.type(), args, cmd.responseType());
        inflight.put(id, new Registered(cmd, cmd.responseType()));
    }

    @Override
    public void onNotify(EnvelopeData<Void, JsonNode> env) {
        for (var sink : notifySinks) sink.accept(env);
    }

    @Override @SuppressWarnings({"unchecked","rawtypes"})
    public void onResponse(String id, CommandType cmdType, EnvelopeData<?, ?> rawEnv) {
        Registered reg = inflight.remove(id);
        if (reg == null) return;
        Commendable cmd = reg.cmd;
        EnvelopeData<Void, ?> env = (EnvelopeData<Void, ?>) rawEnv;

        if (CommandResponseStatus.SUCCESS.equals(env.getStatus())) {
            cmd.onSuccess((EnvelopeData) env, info);
        } else if (CommandResponseStatus.FAILURE.equals(env.getStatus())) {
            cmd.onFailure((EnvelopeData) env, info);
        }
    }
}
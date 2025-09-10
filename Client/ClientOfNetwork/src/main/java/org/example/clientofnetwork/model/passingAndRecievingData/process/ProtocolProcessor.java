package org.example.clientofnetwork.model.passingAndRecievingData.process;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.example.clientofnetwork.model.listeningAndReading.Communicable;
import org.example.clientofnetwork.model.passingAndRecievingData.EnvelopeData;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.KindOfCommunication;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public final class ProtocolProcessor implements
         Consumer<String>, AutoCloseable {
    private final ObjectMapper json;
    private final Communicable transport;
    private final EnvelopeBuilder envelopeBuilder;

    private static final class Pending<R> {
        final TypeReference<R> recType;
        final CommandType cmd;
        Pending(TypeReference<R> t, CommandType c) { this.recType=t; this.cmd=c; }
    }
    private final Map<String, Pending<?>> pending = new ConcurrentHashMap<>();
    private volatile String token;
    private volatile ResponseListener listener = new ResponseListener() {
        public void onNotify(EnvelopeData<Void, JsonNode> env) {}
        public void onResponse(String id, CommandType cmd, EnvelopeData<?, ?> env) {}
    };

    public ProtocolProcessor(ObjectMapper json, Communicable transport) {
        this.json = requireNonNull(json);
        this.transport = requireNonNull(transport);
        this.envelopeBuilder = new EnvelopeBuilder(json);
        transport.setLineProcessor(this);
    }

    public void setToken(String token) { this.token = token; }
    public void setListener(ResponseListener l) { this.listener = (l!=null? l : this.listener); }


    public <P,R> String sendReactive(CommandType cmd, P dataPas, TypeReference<R> recType) {
        String id = UUID.randomUUID().toString();
        pending.put(id, new Pending<>(recType, cmd));
        String payload = envelopeBuilder.buildRequest(id, cmd, token, dataPas);
        transport.addString(payload); // one-line JSON; WriterTCP appends '\n'
        return id;
    }

    @Override public void accept(String rawLine) {
        try {
            var root = json.readTree(rawLine);
            var kind = KindOfCommunication
                    .valueOf(root.path("kindOfCommunication").asText());

            if (kind == KindOfCommunication.NOTIFY) {
                var tf = json.getTypeFactory();
                var envType = tf.constructParametricType(EnvelopeData.class, Void.class,
                        JsonNode.class);
                EnvelopeData<Void, JsonNode> n = json.convertValue(root, envType);
                listener.onNotify(n);
                return;
            }
            if (kind != KindOfCommunication.RESPONSE) return;

            String id = root.path("id").asText(null);
            if (id == null) return;
            Pending<?> p = pending.remove(id);
            if (p == null) return;

            completeTypedAndDispatch(id, p, root);

        } catch (Exception ignore) { /* optionally log */ }
    }

    private <R> void completeTypedAndDispatch(String id, Pending<R> p, JsonNode root) {
        try {
            var tf = json.getTypeFactory();
            var rType    = tf.constructType(p.recType);
            var envType  = tf.constructParametricType(EnvelopeData.class, tf.constructType(Void.class), rType);
            EnvelopeData<Void, R> env = json.convertValue(root, envType);
            listener.onResponse(id, p.cmd, env);
        } catch (Exception ex) {
            // If deserialization fails, you can route an error envelope or log it.
        }
    }

    @Override public void close() { pending.clear(); }
}
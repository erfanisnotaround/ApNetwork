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

public final class ProtocolProcessor implements java.util.function.Consumer<String>, AutoCloseable {
    private final ObjectMapper json;
    private final Communicable transport;

    private static final class Pending<R> {
        final CompletableFuture<EnvelopeData<Void,R>> fut;
        final TypeReference<R> recType;
        final CommandType cmd;
        Pending(CompletableFuture<EnvelopeData<Void,R>> f, TypeReference<R> t, CommandType c) { this.fut=f; this.recType=t; this.cmd=c; }
    }
    private final Map<String, Pending<?>> pending = new ConcurrentHashMap<>();
    private volatile String token;
    private volatile Consumer<EnvelopeData<Void, JsonNode>> notifyHandler = n -> {};

    public ProtocolProcessor(ObjectMapper json, Communicable transport) {
        this.json = java.util.Objects.requireNonNull(json);
        this.transport = java.util.Objects.requireNonNull(transport);
    }

    public void setToken(String token) { this.token = token; }
    public void onNotify(Consumer<EnvelopeData<Void, JsonNode>> h) { this.notifyHandler = (h!=null?h:n->{}); }

    public <P,R> CompletableFuture<EnvelopeData<Void,R>> send(CommandType cmd, P dataPas, TypeReference<R> recType) {
        String id = UUID.randomUUID().toString();

        ObjectNode root = json.createObjectNode();
        root.put("id", id);
        root.put("kindOfCommunication", KindOfCommunication.REQUEST.name());
        root.put("commandType", cmd.name());
        if (token != null) root.put("token", token);
        root.put("ts", java.time.Instant.now().toString());
        if (dataPas != null) root.set("dataPas", json.valueToTree(dataPas));

        var fut = new CompletableFuture<EnvelopeData<Void,R>>();
        pending.put(id, new Pending<>(fut, recType, cmd));

        try {
            transport.addString(json.writeValueAsString(root));
        } catch (Exception e) {
            pending.remove(id);
            fut.completeExceptionally(e);
        }
        fut.orTimeout(10, TimeUnit.SECONDS).whenComplete((ok, ex)->{ if (ex!=null) pending.remove(id); });
        return fut;
    }

    @Override public void accept(String rawLine) {
        try {
            JsonNode root = json.readTree(rawLine);
            var kind = KindOfCommunication.valueOf(root.path("kindOfCommunication").asText());

            if (kind == KindOfCommunication.NOTIFY) {
                TypeFactory tf = json.getTypeFactory();
                JavaType envNotifyType = tf.constructParametricType(EnvelopeData.class, Void.class, JsonNode.class);
                EnvelopeData<Void, JsonNode> n = json.convertValue(root, envNotifyType);
                notifyHandler.accept(n);
                return;
            }
            if (kind != KindOfCommunication.RESPONSE) return;

            String id = root.path("id").asText(null);
            if (id == null) return;
            Pending<?> p = pending.remove(id);
            if (p == null) return;

            completeTyped(p, root);

        } catch (Exception ignore) { /* optional log */ }
    }

    private <R> void completeTyped(Pending<R> p, JsonNode root) {
        try {
            TypeFactory tf = json.getTypeFactory();

            // R as JavaType, built from your stored TypeReference<R>
            JavaType rType    = tf.constructType(p.recType);        // R

            JavaType voidType = tf.constructType(Void.class);       // Void

            JavaType envType  = tf.constructParametricType(
                    EnvelopeData.class, voidType, rType);

            EnvelopeData<Void, R> env = json.convertValue(root, envType);

            p.fut.complete(env);
        } catch (Exception ex) {
            p.fut.completeExceptionally(ex);
        }
    }

    @Override public void close() {
        pending.values().forEach(p -> p.fut.completeExceptionally(new CancellationException("closing")));
        pending.clear();
    }
}

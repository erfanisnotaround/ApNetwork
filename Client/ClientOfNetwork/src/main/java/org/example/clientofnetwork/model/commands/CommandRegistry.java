package org.example.clientofnetwork.model.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class CommandRegistry implements CommandLookup {

    private final Map<CommandType, CommandEntry<?,?>> entries = new EnumMap<>(CommandType.class);

    public <R,T> void register(
            CommandType type,
            Class<T> argClass,
            TypeReference<R> responseType,
            Supplier<Commendable<R,T>> factory
    ) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(argClass);
        Objects.requireNonNull(responseType);
        Objects.requireNonNull(factory);

        entries.put(type, new EntryImpl<>(type, argClass, responseType, factory));
    }

    @Override
    public CommandEntry<?,?> entry(CommandType type) {
        CommandEntry<?,?> e = entries.get(type);
        if (e == null) throw new IllegalArgumentException("No command registered for type: " + type);
        return e;
    }

    private static final class EntryImpl<R,T> implements CommandEntry<R,T> {
        private final CommandType type;
        private final Class<T> argClass;
        private final TypeReference<R> responseType;
        private final Supplier<Commendable<R,T>> factory;

        EntryImpl(CommandType type, Class<T> argClass, TypeReference<R> responseType,
                  Supplier<Commendable<R,T>> factory) {
            this.type = type; this.argClass = argClass; this.responseType = responseType; this.factory = factory;
        }
        @Override public CommandType type() { return type; }
        @Override public Class<T> argClass() { return argClass; }
        @Override public TypeReference<R> responseType() { return responseType; }
        @Override public Commendable<R,T> newInstance() { return factory.get(); }
    }
}
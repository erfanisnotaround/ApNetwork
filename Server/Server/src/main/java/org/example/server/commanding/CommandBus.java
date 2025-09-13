package org.example.server.commanding;

import org.example.sameInfoes.CommandType;

import java.util.EnumMap;
import java.util.Map;

public final class CommandBus {
    public static final class Binding<P,R> {
        public final Class<P> passType;
        public final Class<R> resType;
        public final CommandHandler<P,R> handler;
        Binding(Class<P> p, Class<R> r, CommandHandler<P,R> h) {
            this.passType = p; this.resType = r; this.handler = h;
        }
    }

    private final Map<CommandType, Binding<?,?>> map = new EnumMap<>(CommandType.class);

    public <P,R> void register(CommandType t, Class<P> p, Class<R> r, CommandHandler<P,R> h) {
        map.put(t, new Binding<>(p, r, h));
    }

    @SuppressWarnings("unchecked")
    public <P,R> Binding<P,R> binding(CommandType t) {
        return (Binding<P,R>) map.get(t);
    }
}
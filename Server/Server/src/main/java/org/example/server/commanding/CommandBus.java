package org.example.server.commanding;

import org.example.sameInfoes.CommandType;

import java.util.EnumMap;
import java.util.Map;

public final class CommandBus {
    private final Map<CommandType, CommandHandler> handlers = new EnumMap<>(CommandType.class);
    public void register(CommandType type, CommandHandler h){ handlers.put(type, h); }
    public CommandHandler lookup(CommandType t){ return handlers.get(t); }
}

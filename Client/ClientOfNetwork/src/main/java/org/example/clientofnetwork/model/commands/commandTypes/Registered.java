package org.example.clientofnetwork.model.commands.commandTypes;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.commands.Commendable;

public final class Registered {
    public final Commendable<?,?> cmd;
    public final TypeReference<?> recType;
    public Registered(Commendable<?, ?> c, TypeReference<?> t) { this.cmd=c; this.recType=t; }
}
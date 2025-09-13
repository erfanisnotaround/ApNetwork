package org.example.clientofnetwork.model.commands;


import com.fasterxml.jackson.core.type.TypeReference;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;

public interface CommandLookup {
    CommandEntry<?,?> entry(CommandType type);

    /** One registered command entry (blueprint). */
    interface CommandEntry<R,T> {
        CommandType type();
        Class<T> argClass();
        TypeReference<R> responseType();
        Commendable<R,T> newInstance();
    }
}

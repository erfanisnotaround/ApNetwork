package org.example.server.commanding;

import org.example.EnvelopeData;

public interface CommandHandler<P , R> {
    void handle(RequestContext ctx, EnvelopeData<P , R> env);
}

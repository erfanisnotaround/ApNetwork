package org.example.core.port;

import org.example.EnvelopeData;
import org.example.sameInfoes.CommandType;

public interface EnvelopeCodec {
    String write(Object obj);
    <P,R> EnvelopeData<P,R> read(String line, Class<P> p, Class<R> r);
    CommandType peekCommandType(String raw);
}

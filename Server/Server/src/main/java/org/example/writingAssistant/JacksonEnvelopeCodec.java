package org.example.writingAssistant;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.EnvelopeData;
import org.example.core.port.EnvelopeCodec;
import org.example.sameInfoes.CommandType;

public final class JacksonEnvelopeCodec implements EnvelopeCodec {
    private final ObjectMapper M = new ObjectMapper();

    @Override public String write(Object o) {
        try { return M.writeValueAsString(o); } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override public <P,R> EnvelopeData<P,R> read(String line, Class<P> p, Class<R> r) {
        try {
            var tf = M.getTypeFactory();
            var type = tf.constructParametricType(EnvelopeData.class, p, r);
            return M.readValue(line, type);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override public CommandType peekCommandType(String raw) {
        try { return CommandType.valueOf(M.readTree(raw).path("commandType").asText()); }
        catch (Exception e) { return null; }
    }
}

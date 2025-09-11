package org.example.server.helpers;

import org.example.EnvelopeData;
import org.example.core.port.EnvelopeCodec;
import org.example.core.port.LineServer;
import org.example.sameInfoes.CommandResponseStatus;
import org.example.sameInfoes.CommandType;
import org.example.sameInfoes.KindOfCommunication;
import org.example.server.commanding.CommandBus;
import org.example.server.commanding.RequestContext;

public class ProtocolController {
    private final EnvelopeCodec codec;
    private final CommandBus bus;
    private final Sessions sessions;
    private final Services services;
    private final EnvelopeIO io;

    public ProtocolController(EnvelopeCodec codec, CommandBus bus, Sessions sessions, Services services) {
        this.codec=codec; this.bus=bus; this.sessions=sessions; this.services=services; this.io = new EnvelopeIO(codec);
    }

    public void attach(LineServer.Session session){
        sessions.add(session);
        session.setLineHandler(raw -> {
            try {
                CommandType type = codec.peekCommandType(raw);
                if (type == null) { writeBadJson(session, "missing/invalid commandType"); return; }
                var handler = bus.lookup(type);
                if (handler == null) { writeBadJson(session, "unknown command"); return; }

                // We don’t need typed dataPas at the controller; handlers downcast as needed.
                EnvelopeData<Object,Object> env = codec.read(raw, Object.class, Object.class);
                var ctx = new RequestContext(session, sessions, services, io);
                handler.handle(ctx, env);
            } catch (Exception ex) {
                writeBadJson(session, ex.getMessage());
            }
        });
    }

    private void writeBadJson(LineServer.Session s, String msg){
        EnvelopeData<Void,Void> err = new EnvelopeData<>();
        err.setKindOfCommunication( KindOfCommunication.RESPONSE);
        err.setStatus(CommandResponseStatus.FAILURE);
        err.setMessage("bad_json: " + msg);
        s.sendLine(codec.write(err));
    }
}

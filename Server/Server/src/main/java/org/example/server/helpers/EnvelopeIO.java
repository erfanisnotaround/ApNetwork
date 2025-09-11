package org.example.server.helpers;

import org.example.EnvelopeData;
import org.example.core.port.EnvelopeCodec;
import org.example.core.port.LineServer;
import org.example.sameInfoes.CommandResponseStatus;
import org.example.sameInfoes.KindOfCommunication;

public final class EnvelopeIO {
    private final EnvelopeCodec codec;
    public EnvelopeIO(EnvelopeCodec codec){ this.codec = codec; }

    public <P, R> void respondOk(LineServer.Session s,
                                 EnvelopeData<P, R> env,
                                 R dataRec,
                                 String msg) {
        env.setKindOfCommunication(KindOfCommunication.RESPONSE);
        env.setStatus(CommandResponseStatus.SUCCESS);
        env.setMessage(msg);
        env.setDataRec(dataRec);
        s.sendLine(codec.write(env));
    }

    public <P, R> void respondFail(LineServer.Session s,
                                   EnvelopeData<P, R> env,
                                   String code,
                                   String msg) {
        env.setKindOfCommunication(KindOfCommunication.RESPONSE);
        env.setStatus(CommandResponseStatus.FAILURE);
        env.setCode(code);
        env.setMessage(msg);
        s.sendLine(codec.write(env));
    }
}

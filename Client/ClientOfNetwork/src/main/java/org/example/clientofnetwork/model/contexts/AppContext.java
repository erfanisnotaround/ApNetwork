package org.example.clientofnetwork.model.contexts;

import org.example.clientofnetwork.SessionState;

public class AppContext {
    public final NetContext net;
    public final SessionState session;
    public AppContext(NetContext net, SessionState session){ this.net = net; this.session = session; }
}
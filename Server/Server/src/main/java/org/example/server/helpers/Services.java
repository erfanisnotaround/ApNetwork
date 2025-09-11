package org.example.server.helpers;

import org.example.core.port.BoardsRepo;
import org.example.core.port.NotificationPublisher;
import org.example.core.port.TasksRepo;
import org.example.core.port.UsersRepo;

public final class Services {
    public final UsersRepo users; public final BoardsRepo boards; public final TasksRepo tasks;
    public final AuthService.TokenService tokens; public final NotificationPublisher notifier;
    public Services(UsersRepo u, BoardsRepo b, TasksRepo t, AuthService.TokenService tok, NotificationPublisher pub){
        this.users=u; this.boards=b; this.tasks=t; this.tokens=tok; this.notifier=pub;
    }
}
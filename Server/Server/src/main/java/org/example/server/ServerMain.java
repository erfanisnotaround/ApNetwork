package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Networking.TcpLineServer;
import org.example.core.port.*;
import org.example.passingInformation.entry.*;
import org.example.passingInformation.exit.*;
import org.example.sameInfoes.CommandType;
import org.example.savingAndKeeping.FileBoardsRepo;
import org.example.savingAndKeeping.FileTasksRepo;
import org.example.savingAndKeeping.FileUsersRepo;
import org.example.server.commanding.CommandBus;
import org.example.server.commanding.handlers.*;
import org.example.server.helpers.*;
import org.example.writingAssistant.JacksonEnvelopeCodec;

import java.nio.file.Path;

public final class ServerMain {
    public static void main(String[] args) throws Exception {
        // Infra
        LineServer tcp = new TcpLineServer(8080);
        EnvelopeCodec codec = new JacksonEnvelopeCodec();
        var services = getServices(codec);
        var sessions = new Sessions();

        // Command bus + handlers (OCP)
        var bus = new CommandBus();
        bus.register(CommandType.LOGIN,        LoginPass.class,       LoginRes.class,       new LoginHandler());
        bus.register(CommandType.CREATE_BOARD, CreateBoardPass.class, CreateBoardRes.class, new CreateBoardHandler());
        bus.register(CommandType.LIST_BOARDS,  BoardListPass.class,            BoardListRes.class,  new ListBoardsHandler());
        bus.register(CommandType.VIEW_BOARD,   ViewBoardPass.class,   ViewBoardRes.class,   new ViewBoardHandler());
        bus.register(CommandType.ADD_TASK,     AddTaskPass.class,     AddTaskRes.class,     new AddTaskHandler());
        bus.register(CommandType.REGISTER , RegisterPass.class,       RegisterRes.class,     new RegisterHandler());
// … etc

        // bus.register(CommandType.LIST_TASKS, new ListTasksHandler()); etc.

        // Protocol controller
        var controller = new ProtocolController(codec, bus, sessions, services);

        // Start TCP server
        tcp.start(controller::attach);
        System.out.println("Server up on :8080");
    }

    private static Services getServices(EnvelopeCodec codec) {
        NotificationPublisher notifier = new TcpNotificationPublisher(codec);

        var mapper  = new ObjectMapper();
        var dataDir = Path.of("data");

        UsersRepo users = new FileUsersRepo(dataDir , mapper);
        BoardsRepo boards = new FileBoardsRepo(dataDir , mapper);
        TasksRepo tasks = new FileTasksRepo(dataDir , mapper);

        // App services
        var tokens = new AuthService.TokenService("super-secret");
        return new Services(users, boards, tasks, tokens, notifier);

    }
}
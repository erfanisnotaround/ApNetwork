package org.example.clientofnetwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.clientofnetwork.controller.responseActionControlling.LabelApplier;
import org.example.clientofnetwork.model.commands.CommandManager;
import org.example.clientofnetwork.model.contexts.NetConfig;
import org.example.clientofnetwork.model.contexts.NetContext;
import org.example.clientofnetwork.model.listeningAndReading.Communicable;
import org.example.clientofnetwork.model.listeningAndReading.SenderAndGetter;
import org.example.clientofnetwork.model.listeningAndReading.UdpNotificationReceiver;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.process.ProtocolProcessor;
import org.example.clientofnetwork.model.responseChangeMaker.LabelManaging;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class WiringChangeable {
    public record Result(
            LabelApplier labelApplier,
            CommandManager commandManager,
            ClientInfo info
    ){}

    public static Result boot(){


        ObjectMapper objectMapper = new ObjectMapper();
        NetConfig netConfig = NetConfig.load(objectMapper);

        Socket socket;
        NetContext netContext;

        try {


            socket = new  Socket(netConfig.host, netConfig.port);

            netContext = new NetContext(socket);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LabelManaging labelManaging = new LabelManaging();
        LabelApplier labelApplier = new LabelApplier(labelManaging);
        ClientInfo clientInfo = new ClientInfo();

        Communicable communicable = new SenderAndGetter(netContext);
        ProtocolProcessor processor = new ProtocolProcessor(objectMapper , communicable);
        UdpNotificationReceiver udpNotificationReceiver = new UdpNotificationReceiver(9090);
        udpNotificationReceiver.setOnMessage(processor);
        try {
            udpNotificationReceiver.start();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        CommandManager commandManager = new CommandManager(processor , clientInfo);


        communicable.StartCommunicatingTCP();
        return new Result(labelApplier, commandManager, clientInfo);
    }
}

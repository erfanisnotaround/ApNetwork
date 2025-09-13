package org.example.clientofnetwork.model.sceneInformation;

import org.example.clientofnetwork.model.commands.CommandManager;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;

public class GoingToLoginFromScratch {
    public CommandManager commandManager;
    public ClientInfo clientInfo;

    public GoingToLoginFromScratch(CommandManager commandManager, ClientInfo clientInfo) {
        this.commandManager = commandManager;
        this.clientInfo = clientInfo;
    }
}

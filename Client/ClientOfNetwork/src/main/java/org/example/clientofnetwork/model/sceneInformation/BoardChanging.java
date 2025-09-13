package org.example.clientofnetwork.model.sceneInformation;

import org.example.clientofnetwork.model.boardRelated.BoardSummary;
import org.example.clientofnetwork.model.commands.CommandManager;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;

public class BoardChanging {
    private final CommandManager commandManager;
    private final ClientInfo clientInfo;
    private final BoardSummary boardSummary;

    public BoardChanging(CommandManager commandManager, ClientInfo clientInfo, BoardSummary boardSummary) {
        this.commandManager = commandManager;
        this.clientInfo = clientInfo;
        this.boardSummary = boardSummary;

    }
    private CommandManager getCommandManager() {
        return commandManager;
    }
    private ClientInfo getClientInfo() {
        return clientInfo;
    }
    private BoardSummary getBoardSummary() {
        return boardSummary;
    }
}

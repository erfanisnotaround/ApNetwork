module org.example.clientofnetwork {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens org.example.clientofnetwork to javafx.fxml;
    exports org.example.clientofnetwork;
    exports org.example.clientofnetwork.controller;
    opens org.example.clientofnetwork.controller to javafx.fxml;
    exports org.example.clientofnetwork.model;
    opens org.example.clientofnetwork.model to javafx.fxml;
    exports org.example.clientofnetwork.model.agents;
    opens org.example.clientofnetwork.model.agents to javafx.fxml;
    exports org.example.clientofnetwork.model.agents.screenChanging;
    opens org.example.clientofnetwork.model.agents.screenChanging to javafx.fxml;
    exports org.example.clientofnetwork.model.contexts;
    opens org.example.clientofnetwork.model.contexts to javafx.fxml;
    exports org.example.clientofnetwork.controller.sceneControllers;
    exports org.example.clientofnetwork.model.sceneInformation to javafx.fxml;
    exports org.example.clientofnetwork.model.commands to javafx.fxml;
    opens org.example.clientofnetwork.model.passingAndRecievingData.client to javafx.fxml;
    exports org.example.clientofnetwork.model.passingAndRecievingData to com.fasterxml.jackson.databind;
    exports org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes to com.fasterxml.jackson.databind;
    exports org.example.clientofnetwork.model.commands.buildInfo to com.fasterxml.jackson.databind;
    exports org.example.clientofnetwork.model.responseTypes to com.fasterxml.jackson.databind;
    exports org.example.clientofnetwork.model.boardRelated to com.fasterxml.jackson.databind;
    opens org.example.clientofnetwork.model.responseTypes to com.fasterxml.jackson.databind;
    opens org.example.clientofnetwork.model.boardRelated to com.fasterxml.jackson.databind;
    opens org.example.clientofnetwork.controller.sceneControllers to javafx.fxml;
}
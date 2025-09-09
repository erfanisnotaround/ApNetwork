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
}
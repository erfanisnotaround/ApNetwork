package org.example.clientofnetwork.controller.responseActionControlling;

import javafx.scene.control.Label;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.NotifierTypes;

public interface LabelChengable {
    void registerNotifier(Label label, NotifierTypes notifierType);
    void registerCommandLabel(Label label , CommandType commandType);

    void applyNotifier(String resultText, NotifierTypes notifierType);
    void applyCommandLabel(String resultText, CommandType commandType);
}

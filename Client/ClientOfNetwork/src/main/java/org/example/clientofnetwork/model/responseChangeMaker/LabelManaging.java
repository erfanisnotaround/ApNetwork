package org.example.clientofnetwork.model.responseChangeMaker;

import javafx.scene.control.Label;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.NotifierTypes;

import java.util.HashMap;
import java.util.Map;

public class LabelManaging {

    private final Map<CommandType , Label> labelMap;
    private final Map<NotifierTypes , Label> notifierMap;



    public LabelManaging() {
        labelMap = new HashMap<>();
        notifierMap = new HashMap<>();
    }

    public void RegisterCommandLabel(Label Label , CommandType commandType) {
        labelMap.put(commandType , Label);
    }
    public void RegisterNotifier(Label Label , NotifierTypes notifierType) {
        notifierMap.put(notifierType , Label);
    }
    public Label GetCommandLabel(CommandType commandType) {
        return labelMap.get(commandType);
    }
    public Label GetNotifier(NotifierTypes notifierType) {
        return notifierMap.get(notifierType);
    }


}

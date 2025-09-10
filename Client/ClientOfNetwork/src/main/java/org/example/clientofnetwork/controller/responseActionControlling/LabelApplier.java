package org.example.clientofnetwork.controller.responseActionControlling;

import javafx.scene.control.Label;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.CommandType;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.NotifierTypes;
import org.example.clientofnetwork.model.responseChangeMaker.LabelManaging;

public class LabelApplier implements LabelChengable {

    private final LabelManaging labelManaging;

    public LabelApplier(LabelManaging labelManaging) {
        this.labelManaging = labelManaging;
    }

    @Override
    public void registerCommandLabel(Label Label , CommandType commandType) {
        labelManaging.RegisterCommandLabel(Label, commandType);
    }

    @Override
    public void registerNotifier(Label Label , NotifierTypes notifierType) {
        labelManaging.RegisterNotifier(Label, notifierType);
    }

    @Override
    public void applyNotifier(String resultText, NotifierTypes notifierType) {
        Label label = labelManaging.GetNotifier(notifierType);

        apply(label, resultText);
    }

    @Override
    public void applyCommandLabel(String resultText, CommandType commandType) {
        Label label = labelManaging.GetCommandLabel(commandType);

        apply(label, resultText);
    }



    private void apply(Label label , String text) {
        label.setText(text);
    }

}

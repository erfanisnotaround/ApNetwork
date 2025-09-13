package org.example.clientofnetwork.controller.uiChanging.types;

import org.example.clientofnetwork.controller.uiChanging.types.interfaces.CommandPresenter;
import org.example.clientofnetwork.model.responseTypes.BoardListRes;

public class FxListBoardsRegister implements CommandPresenter<BoardListRes> {

    private Runnable runnable;
    public void showSuccess(BoardListRes res) {
        runnable.run();
    }

    @Override
    public void showFailure(String message) {

    }
}

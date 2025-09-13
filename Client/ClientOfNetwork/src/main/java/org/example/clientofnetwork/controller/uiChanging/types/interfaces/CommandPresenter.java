package org.example.clientofnetwork.controller.uiChanging.types.interfaces;

public interface CommandPresenter<R> {
    default void showSubmitting() {}

    void showSuccess(R res);

    /** Called on failure with a human-readable message. */
    void showFailure(String message);
}

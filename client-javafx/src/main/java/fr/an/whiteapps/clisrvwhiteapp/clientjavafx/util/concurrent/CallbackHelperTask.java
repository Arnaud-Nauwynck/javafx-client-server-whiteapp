package fr.an.whiteapps.clisrvwhiteapp.clientjavafx.util.concurrent;

import javafx.application.Platform;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * helper class for working with javafx Tasks, but using typed callbacks
 */
@Slf4j
public class CallbackHelperTask<T> extends Task<T> {

    /**
     * mandatory function, to be executed in a separate Thread  (not javafx thread !)
     */
    protected final Callable<T> doCallFunc;

    /**
     * optional callback, to be executed later in javafx thread (in Platform.runLater(..)), with typed result when succeed
     */
    protected final Consumer<T> optOnFinishedFunc;

    /**
     * optional callback, to be executed later in javafx thread(in Platform.runLater(..)), with exception when failed
     */
    protected final Consumer<Throwable> optOnFailedFunc;

    /**
     * optional callback, to be executed later in javafx thread(in Platform.runLater(..)), whenever succeeded/failed/cancelled
     */
    protected final Consumer<Task<T>> optOnFinallyFunc;

    //---------------------------------------------------------------------------------------------

    public CallbackHelperTask(Callable<T> doCallFunc, //
                              CallbacksBuilder<T> callbacks
    ) {
        this.doCallFunc = Objects.requireNonNull(doCallFunc);
        CallbacksBuilder<T> cb = (callbacks != null)? callbacks : new CallbacksBuilder<>();
        this.optOnFinishedFunc = cb.optOnFinishedFunc;
        this.optOnFailedFunc = cb.optOnFailedFunc;
        this.optOnFinallyFunc = cb.optOnFinallyFunc;
        if (optOnFinallyFunc != null) {
            this.setOnCancelled(e -> optOnFinallyFunc.accept(this));
        }
    }

    //---------------------------------------------------------------------------------------------

    @Override
    protected T call() throws Exception {
        try {
            T resp = doCallFunc.call();
            if (optOnFinishedFunc  != null) {
                Platform.runLater(
                        () -> optOnFinishedFunc.accept(resp)
                );
            }
            return resp;
        } catch (Exception ex) {
            log.error("Failed", ex);
            if (optOnFailedFunc != null) {
                Platform.runLater(
                        () -> optOnFailedFunc.accept(ex)
                );
            }
            throw ex;
        } finally {
            if (optOnFinallyFunc != null) {
                if (! isCancelled()) {
                    Platform.runLater(
                            () -> optOnFinallyFunc.accept(this)
                    );
                } // else already called once
            }
        }
    }

}

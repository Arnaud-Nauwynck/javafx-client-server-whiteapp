package fr.an.whiteapps.clisrvwhiteapp.clientjavafx.util.concurrent;

import javafx.concurrent.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

/**
 * builder pattern for callbacks, to use in <code>CallbackHelperTask</code>
 *
 * @param <T> return type for corresponding task
 */
@Getter
@NoArgsConstructor @AllArgsConstructor
public class CallbacksBuilder<T> {

    /**
     * optional callback, to be executed later in javafx thread (in Platform.runLater(..)), with typed result when succeed
     */
    public Consumer<T> optOnFinishedFunc;

    /**
     * optional callback, to be executed later in javafx thread(in Platform.runLater(..)), with exception when failed
     */
    public Consumer<Throwable> optOnFailedFunc;

    /**
     * optional callback, to be executed later in javafx thread(in Platform.runLater(..)), whenever succeeded/failed/cancelled
     */
    public Consumer<Task<T>> optOnFinallyFunc;

    //---------------------------------------------------------------------------------------------

    public CallbacksBuilder<T> onFinished(Consumer<T> optOnFinishedFunc) {
        this.optOnFinishedFunc = optOnFinishedFunc;
        return this;
    }

    public CallbacksBuilder<T> onFailed(Consumer<Throwable> optOnFailedFunc) {
        this.optOnFailedFunc = optOnFailedFunc;
        return this;
    }

    public CallbacksBuilder<T> setOptOnFinallyFunc(Consumer<Task<T>> optOnFinallyFunc) {
        this.optOnFinallyFunc = optOnFinallyFunc;
        return this;
    }

}

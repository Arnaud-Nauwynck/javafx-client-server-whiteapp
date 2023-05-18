package fr.an.whiteapps.clisrvwhiteapp.clientjavafx.model;

import fr.an.whiteapps.clisrvwhiteapp.clientjavafx.util.concurrent.CallbackHelperTask;
import fr.an.whiteapps.clisrvwhiteapp.clientjavafx.util.concurrent.CallbacksBuilder;
import fr.an.whiteapps.clisrvwhiteapp.common.restapi.dto.TestEchoRequestDTO;
import fr.an.whiteapps.clisrvwhiteapp.common.restapi.dto.TestEchoResponseDTO;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * javafx model class (single-thread-apartment-model) for Game
 *
 * all remote calls / slow computations should be done in javafx worker thread, then enqueue event when finished
 */
@Slf4j
public class SampleModel {

    protected SampleHttpClient gameClient;

    protected ExecutorService defaultExecutorService = Executors.newCachedThreadPool();
    // cf also ForkJoinPool.commonPool();

    //---------------------------------------------------------------------------------------------

    public SampleModel(SampleHttpClient gameClient) {
        this.gameClient = gameClient;
    }

    //---------------------------------------------------------------------------------------------

    public void asyncTextEcho(TestEchoRequestDTO req,
                              CallbacksBuilder<TestEchoResponseDTO> callbacksBuilder) {
        val task = new CallbackHelperTask<TestEchoResponseDTO>(
                () -> gameClient.testEcho(req),
                callbacksBuilder);
        defaultExecutorService.submit(task);
    }

    // TODO add more server api calls here ..
    // TODO add statefull logic for MVC

}

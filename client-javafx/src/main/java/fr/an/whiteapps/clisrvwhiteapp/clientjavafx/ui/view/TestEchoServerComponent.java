package fr.an.whiteapps.clisrvwhiteapp.clientjavafx.ui.view;

import fr.an.whiteapps.clisrvwhiteapp.clientjavafx.model.SampleModel;
import fr.an.whiteapps.clisrvwhiteapp.clientjavafx.util.concurrent.CallbacksBuilder;
import fr.an.whiteapps.clisrvwhiteapp.common.restapi.dto.TestEchoRequestDTO;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class TestEchoServerComponent {

    protected final SampleModel model;

    HBox component;
    TextField testEchoRequestTextField;
    Label testEchoResponseText;
    Label testEchoCallStatus;
    Button testEchoApiButton;

    //---------------------------------------------------------------------------------------------

    public TestEchoServerComponent(SampleModel model) {
        this.model = Objects.requireNonNull(model);
        this.component = new HBox();
        Label testEchoLabel = new Label("test-echo server");
        this.testEchoRequestTextField = new TextField();
        this.testEchoResponseText = new Label();
        this.testEchoCallStatus = new Label();

        this.testEchoApiButton = new Button("Test-Echo (server)");
        testEchoApiButton.setOnAction(e -> onClickTestEchoApi());

        component.getChildren().addAll(
                testEchoLabel, testEchoRequestTextField, testEchoApiButton, testEchoCallStatus, testEchoResponseText);
    }

    //---------------------------------------------------------------------------------------------

    public Node getComponent() {
        return component;
    }

    private void onClickTestEchoApi() {
        testEchoApiButton.setDisable(true); // avoid clicking multiple times... re-enable at end
        TestEchoRequestDTO req = new TestEchoRequestDTO();
        req.fieldInt = 1;
        req.fieldString = this.testEchoRequestTextField.getText();
        log.info("async send request to server");
        this.testEchoCallStatus.setText("calling server..");
        model.asyncTextEcho(req, new CallbacksBuilder<>(
                resp -> {
                    log.info("updating UI with server succeed response: " + resp);
                    this.testEchoCallStatus.setText("done call to server, echo result:" + resp.fieldString);
                },
                err -> {
                    log.warn("updating UI with server failure exception", err);
                    this.testEchoCallStatus.setText("Failed call to server, exception:" + err.getClass().getName());
                },
                task -> {
                    log.info("updating UI (succeed/failed/cancelled)");
                    testEchoApiButton.setDisable(false); // now re-enable button
                }
        ));
    }

}

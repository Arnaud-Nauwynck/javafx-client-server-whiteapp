package fr.an.whiteapps.clisrvwhiteapp.clientjavafx.ui.view;

import fr.an.whiteapps.clisrvwhiteapp.clientjavafx.model.SampleModel;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleView {

    private SampleModel model;

    private BorderPane component;

    //---------------------------------------------------------------------------------------------

    public SampleView(SampleModel model, boolean showTestEchoComponent) {
        this.model = model;
        this.component = new BorderPane();

        HBox buttonToolbars = new HBox();

        if (showTestEchoComponent) {
            TestEchoServerComponent testEchoServerComponent = new TestEchoServerComponent(model);
            buttonToolbars.getChildren().add(testEchoServerComponent.getComponent());
        }

        component.setCenter(new Label("TODO ... SampleView .. implement your view here"));
        component.setBottom(buttonToolbars);
    }

    //---------------------------------------------------------------------------------------------

    public Node getComponent() {
        return component;
    }

}

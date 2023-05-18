package fr.an.whiteapps.clisrvwhiteapp.clientjavafx.ui;

import fr.an.whiteapps.clisrvwhiteapp.clientjavafx.model.SampleHttpClient;
import fr.an.whiteapps.clisrvwhiteapp.clientjavafx.model.SampleModel;
import fr.an.whiteapps.clisrvwhiteapp.clientjavafx.ui.view.SampleView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SampleApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainBorderPanel = new BorderPane();

        VBox menuAndToolbar = new VBox();
        { // MenuBar with "File" menu
            MenuBar mb = new MenuBar();
            menuAndToolbar.getChildren().add(mb);

            Menu fileMenu = new Menu("File");
            mb.getMenus().add(fileMenu);
            MenuItem openMenuItem = new MenuItem("Open");
            fileMenu.getItems().add(openMenuItem);
            MenuItem saveMenuItem = new MenuItem("Save");
            fileMenu.getItems().add(saveMenuItem);
        }

        { // button Toolbar
            ToolBar toolBar = new ToolBar();
            menuAndToolbar.getChildren().add(toolBar);

            Button button1 = new Button("button1");
            toolBar.getItems().add(button1);
        }
        mainBorderPanel.setTop(menuAndToolbar);

        String serverBaseUrl = "http://localhost:8080"; // TODO default connection settings to server..
        SampleHttpClient gameHttpClient = SampleHttpClient.createDefault(serverBaseUrl);
        SampleModel gameModel = new SampleModel(gameHttpClient);

        SampleView gameView = new SampleView(gameModel, true);
        mainBorderPanel.setCenter(gameView.getComponent());

        Scene scene = new Scene(mainBorderPanel, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

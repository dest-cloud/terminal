package com.bgconsole.desktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainWindow extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL resource = getClass().getResource("/com/bgconsole/desktop/main_window.fxml");
        FXMLLoader loader = new FXMLLoader(resource);

        Parent root = loader.load();

        MainWindowController controller = loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/bgconsole/desktop/styles.css").toExternalForm());

        stage.setTitle("BG Console");
        stage.setScene(scene);
        stage.show();

        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            Platform.runLater(() -> {
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Terminal");
//                controller.getMenuBar().setUseSystemMenuBar(true);

            });
        }
        controller.setStage(stage);
        controller.setLocationList(MainWindowData.instance.getLocations());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
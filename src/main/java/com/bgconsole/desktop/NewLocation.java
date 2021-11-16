package com.bgconsole.desktop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class NewLocation {

    public NewLocation(MainWindowController mainWindowController) {
        try {
            URL resource = getClass().getResource("/com/bgconsole/desktop/new_location.fxml");
            FXMLLoader loader = new FXMLLoader(resource);

            Parent root = loader.load();

            NewLocationController controller = loader.getController();
            controller.setMainWindowController(mainWindowController);
//            controller.setCommandLists(lists);

            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/com/bgconsole/desktop/styles.css").toExternalForm());

//            stage.setOnCloseRequest(event -> trigger.trigger());

            stage.setTitle("New location");
            stage.setScene(scene);
            stage.show();

            controller.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

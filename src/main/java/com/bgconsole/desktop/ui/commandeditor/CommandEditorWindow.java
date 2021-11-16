package com.bgconsole.desktop.ui.commandeditor;

import com.bgconsole.desktop.command.CommandList;
import com.bgconsole.desktop.ui.SimpleTrigger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class CommandEditorWindow {

    public CommandEditorWindow(List<CommandList> lists, SimpleTrigger trigger) {
        try {
            URL resource = getClass().getResource("/com/bgconsole/desktop/command_editor.fxml");
            FXMLLoader loader = new FXMLLoader(resource);

            Parent root = loader.load();

            CommandEditorController controller = loader.getController();
            controller.setCommandLists(lists);

            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/com/bgconsole/desktop/styles.css").toExternalForm());

            stage.setOnCloseRequest(event -> trigger.trigger());

            stage.setTitle("Command Editor");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

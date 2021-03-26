package cloud.dest.terminal.ui.commandeditor;

import cloud.dest.terminal.command.CommandList;
import cloud.dest.terminal.ui.SimpleTrigger;
import cloud.dest.terminal.variable.VariableList;
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
            URL resource = getClass().getResource("/cloud/dest/terminal/command_editor.fxml");
            FXMLLoader loader = new FXMLLoader(resource);

            Parent root = loader.load();

            CommandEditorController controller = loader.getController();
            controller.setCommandLists(lists);

            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/cloud/dest/terminal/styles.css").toExternalForm());

            stage.setOnCloseRequest(event -> trigger.trigger());

            stage.setTitle("Command Editor");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package cloud.dest.terminal.ui.vareditor;

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

public class VarEditorWindow {

    public VarEditorWindow(List<VariableList> lists, SimpleTrigger trigger) {
        try {
            URL resource = getClass().getResource("/cloud/dest/terminal/var_editor.fxml");
            FXMLLoader loader = new FXMLLoader(resource);

            Parent root = loader.load();

            VarEditorController controller = loader.getController();
            controller.setVariableLists(lists);

            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/cloud/dest/terminal/styles.css").toExternalForm());

            stage.setOnCloseRequest(event -> trigger.trigger());

            stage.setTitle("Variable Editor");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package cloud.dest.terminal.ui.vareditor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class VarEditorWindow {

    public VarEditorWindow() {
        try {
            URL resource = getClass().getResource("/cloud/dest/terminal/var_editor.fxml");
            Parent root = FXMLLoader.load(resource);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/cloud/dest/terminal/styles.css").toExternalForm());

            stage.setTitle("Variable Editor");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

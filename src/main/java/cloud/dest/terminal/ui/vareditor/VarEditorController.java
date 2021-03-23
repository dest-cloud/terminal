package cloud.dest.terminal.ui.vareditor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class VarEditorController {

    @FXML
    private AnchorPane varTab;

    public void initialize() {
        URL resource = getClass().getResource("/cloud/dest/terminal/var_editor_tab.fxml");
        Parent root = null;
        try {
            root = FXMLLoader.load(resource);
//            Stage stage = new Stage();
//            Scene scene = new Scene(root);
//            stage.setScene(scene);

            varTab.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

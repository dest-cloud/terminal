package com.bgconsole.desktop.ui.vareditor;

import com.bgconsole.desktop.variable.VariableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class VarEditorController {

    @FXML
    private AnchorPane varTab;

    private List<VariableList> variableLists;

    public void initialize() {
    }

    public void setVariableLists(List<VariableList> variableLists) {
        this.variableLists = variableLists;
        URL resource = getClass().getResource("/com/bgconsole/desktop/var_editor_tab.fxml");
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(resource);
            root = loader.load();
            VarEditorTabController controller = loader.getController();
            variableLists.stream().findFirst().ifPresent(controller::setVariableList);

            varTab.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

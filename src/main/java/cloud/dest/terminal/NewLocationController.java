package cloud.dest.terminal;

import cloud.dest.terminal.location.Location;
import cloud.dest.terminal.utils.WriteYAMLFile;
import cloud.dest.terminal.workspace.Workspace;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NewLocationController {

    @FXML
    private TextField name;

    @FXML
    private TextField path;

    private Stage stage;

    private MainWindowController mainWindowController;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void openDialog(ActionEvent event) {
        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File selectedDirectory =
                directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            path.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    public void createLocation(ActionEvent event) {
        String strPath = path.getText();
        String strName = name.getText();
        Path location = Paths.get(strPath);
        if (!Files.exists(location)) {
            try {
                Files.createDirectories(location);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Files.isDirectory(location)) {
            Workspace workspace = new Workspace();
            workspace.setId(UUID.randomUUID().toString());
            workspace.setName(strName);
            workspace.setCommands(new ArrayList<>());
            workspace.setVariables(new ArrayList<>());

            Location newLocation = new Location(UUID.randomUUID().toString(), strName, strPath);
            try {
                WriteYAMLFile.writeWorkspace(workspace, Paths.get(path.getText(), "workspace.yaml").toString());
                List<Location> locations = new ArrayList<>(MainWindowData.instance.getLocations());
                locations.add(newLocation);
                WriteYAMLFile.writeLocations(locations, MainWindowData.DEFAULT_LOCATION.toString());
                MainWindowData.instance.reloadLocations();
                mainWindowController.setLocationList(MainWindowData.instance.getLocations());
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }
}

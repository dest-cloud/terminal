package com.bgconsole.desktop;

import com.bgconsole.desktop.location.Location;
import com.bgconsole.desktop.ui.TerminalWindow;
import com.bgconsole.desktop.utils.ParseYAMLFile;
import com.bgconsole.desktop.utils.WriteYAMLFile;
import com.bgconsole.desktop.workspace.Workspace;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
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

public class MainWindowController {

    @FXML
    private MenuBar menuBar;

    @FXML
    private ListView<String> locationList;

    private List<Location> locationCache;

    private Stage stage;

    public MainWindowController() {
    }

    @FXML
    public void quit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void newLocation(ActionEvent event) {
        new NewLocation(this);
    }

    @FXML
    public void openLocation(ActionEvent event) {
        final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File selectedDirectory =
                directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            String strPath = selectedDirectory.getAbsolutePath();
            Path path = Paths.get(strPath);
            if (Files.isDirectory(path)) {
                try {
                    Workspace workspace = ParseYAMLFile.readWorkspace(Paths.get(strPath, "workspace.yaml").toString());

                    Location newLocation = new Location(UUID.randomUUID().toString(), workspace.getName(), strPath);
                    List<Location> locations = new ArrayList<>(MainWindowData.instance.getLocations());
                    locations.add(newLocation);

                    WriteYAMLFile.writeLocations(locations, MainWindowData.DEFAULT_LOCATION.toString());

                    MainWindowData.instance.reloadLocations();
                    setLocationList(MainWindowData.instance.getLocations());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLocationList(List<Location> locations) {
        locationCache = new ArrayList<>(locations);
        locationList.getItems().clear();
        locationCache.forEach(location -> {
            locationList.getItems().add(location.getName());
            locationList.setOnMouseClicked(click -> {
                if (click.getClickCount() == 2) {
                    Location loc = locationCache.get(locationList.getSelectionModel().getSelectedIndex());
                    try {
                        new TerminalWindow(loc);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }
}

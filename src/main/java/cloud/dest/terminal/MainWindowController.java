package cloud.dest.terminal;

import cloud.dest.terminal.location.Location;
import cloud.dest.terminal.ui.TerminalWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

package cloud.dest.terminal;

import cloud.dest.terminal.location.Location;
import cloud.dest.terminal.utils.ParseYAMLFile;
import cloud.dest.terminal.utils.WriteYAMLFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainWindowData {

    public static final Path DEFAULT_LOCATION = Paths.get(System.getProperty("user.home"), ".cloud.dest.terminal", "locations.yaml");

    public static final MainWindowData instance = new MainWindowData();

    private List<Location> locations;

    private MainWindowData() {
        if (!Files.exists(DEFAULT_LOCATION)) {
            try {
                Files.createDirectories(DEFAULT_LOCATION.getParent());
                WriteYAMLFile.writeLocations(new ArrayList<>(), DEFAULT_LOCATION.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reloadLocations();
    }

    public void reloadLocations() {
        try {
            locations = ParseYAMLFile.readLocations(DEFAULT_LOCATION.toString());
            AppData.instance.setLocationList(locations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Location> getLocations() {
        return locations;
    }
}

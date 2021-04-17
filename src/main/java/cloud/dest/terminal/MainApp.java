package cloud.dest.terminal;

import cloud.dest.terminal.global_input.GlobalKeyListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL resource = getClass().getResource("/cloud/dest/terminal/scene.fxml");
        FXMLLoader loader = new FXMLLoader(resource);

        Parent root = loader.load();

        FXMLController controller = loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cloud/dest/terminal/styles.css").toExternalForm());

        stage.setTitle("Boris Grishenko@Terminal");
        stage.setScene(scene);
        stage.show();

        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            Platform.runLater(() -> {
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Terminal");
                controller.getMenuBar().setUseSystemMenuBar(true);
                controller.loadConfig(AppData.instance.getEnvironment().getCommandLists());
            });
        }

//        try {
//            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
//            logger.setLevel(Level.OFF);
//            logger.setUseParentHandlers(false);
//
//            GlobalScreen.registerNativeHook();
//        } catch (NativeHookException ex) {
//            System.err.println("There was a problem registering the native hook.");
//            System.err.println(ex.getMessage());
//
//            System.exit(1);
//        }
//
//        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
        new GlobalKeyListener();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
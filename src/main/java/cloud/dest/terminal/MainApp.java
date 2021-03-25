package cloud.dest.terminal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/cloud/dest/terminal/scene.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/cloud/dest/terminal/styles.css").toExternalForm());

        stage.setTitle("Boris Grishenko@Terminal");
        stage.setScene(scene);
        stage.show();

        try {
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
            logger.setUseParentHandlers(false);

            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
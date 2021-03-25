package cloud.dest.terminal;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    private boolean ctrl = false;
    private boolean alt = false;
    private boolean x = false;

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrl = true;
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
            alt = true;
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_X) {
            x = true;
        }

        if (ctrl && alt && x) {
            Platform.runLater(() -> {
                Stage newWindow = new Stage(StageStyle.UTILITY);
                newWindow.setTitle("Exec command");
                newWindow.initModality(Modality.APPLICATION_MODAL);

                TextField text = new TextField();
                text.setPromptText("Type a command");
                text.setMinHeight(60);
                text.setMinWidth(400);
                text.setStyle("-fx-text-box-border: #242424;-fx-border-width: 5px; -fx-focus-color: #242424;");
                text.setFont(new Font(24));
                text.requestFocus();
                text.setOnKeyPressed(ke -> {
                    if (ke.getCode().equals(KeyCode.ESCAPE)) {
                        newWindow.close();
                    }
                    if (ke.getCode().equals(KeyCode.ENTER)) {
                        String command = text.getText();
                        newWindow.close();
                        AppData.instance.runCommand(command);
                    }
                });

                StackPane secondaryLayout = new StackPane();
                secondaryLayout.getChildren().add(text);

                Scene secondScene = new Scene(secondaryLayout);

                newWindow.setAlwaysOnTop(true);
                newWindow.setResizable(false);
                newWindow.requestFocus();
                newWindow.setScene(secondScene);
                newWindow.toFront();
                newWindow.show();

            });
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {

        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
            ctrl = false;
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
            alt = false;
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_X) {
            x = false;
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }

}

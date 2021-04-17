package cloud.dest.terminal.global_input;

import cloud.dest.terminal.AppData;
import cloud.dest.terminal.command.Command;
import cloud.dest.terminal.command.CommandList;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.simplenativehooks.NativeHookInitializer;
import org.simplenativehooks.NativeKeyHook;
import org.simplenativehooks.events.NativeKeyEvent;
import org.simplenativehooks.staticResources.BootStrapResources;
import org.simplenativehooks.utilities.Function;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GlobalKeyListener implements ExecCommand {

    private boolean ctrl = false;
    private boolean alt = false;
    private boolean x = false;

    private static final int CTRL = 17;
    private static final int ALT = 18;
    private static final int X = 88;

    private Stage newWindow;

    public GlobalKeyListener() {
        final GlobalKeyListener keyListener = this;
        // Thread thread = new Thread(() -> {

        try {
            BootStrapResources.extractResources();
        } catch (IOException e) {
            System.out.println("Cannot extract bootstrap resources.");
            e.printStackTrace();
            System.exit(2);
        }
        /* Initializing global hooks */
        NativeHookInitializer.of().start();


        /* Set up callbacks */
        NativeKeyHook key = NativeKeyHook.of();
        key.setKeyPressed(new Function<>() {
            @Override
            public Boolean apply(NativeKeyEvent d) {
                if (d.getKey() == CTRL) {
                    ctrl = true;
                }
                if (d.getKey() == ALT) {
                    alt = true;
                }
                if (d.getKey() == X) {
                    x = true;
                }
                if (ctrl && alt && x) {
                    Platform.runLater(() -> {
                        newWindow = new Stage(StageStyle.UTILITY);
                        newWindow.setTitle("Exec command");
                        newWindow.initModality(Modality.APPLICATION_MODAL);

//                        AutoCompletionTextField text = new AutoCompletionTextField();

                        List<Command> commands = new ArrayList<>();
                        for (CommandList commandList : AppData.instance.getEnvironment().getCommandLists()) {
                            //                                commands.add(command.getName() + "(" + command.getAlias() + ")");
                            commands.addAll(commandList.getCommands());
                        }

                        ComboBox<Command> text = new ComboBox<>();
                        text.getItems().addAll(commands);
//                        text.s
                        new AutoCompleteBox(text, keyListener);

//                        AutoCompleteTextField text = new AutoCompleteTextField(commands);
////                        TextField text = new TextField();
                        text.setPromptText("Type a command");
                        text.setMinHeight(60);
                        text.setMinWidth(400);
                        text.setStyle("-fx-text-box-border: #242424;-fx-border-width: 5px; -fx-focus-color: #242424;");
//                        text.setFont(new Font(24));
                        text.requestFocus();
//                        text.setOnAction((event) -> {
////                            int selectedIndex = text.getSelectionModel().getSelectedIndex();
//                            Command command = text.getSelectionModel().getSelectedItem();
//                            newWindow.close();
//                            AppData.instance.runCommand(command.getAlias());
//                        });


                        StackPane secondaryLayout = new StackPane();
                        secondaryLayout.getChildren().add(text);

                        Scene secondScene = new Scene(secondaryLayout);
                        secondScene.getStylesheets().add(getClass().getResource("/cloud/dest/terminal/styles.css").toExternalForm());

                        newWindow.setAlwaysOnTop(true);
                        newWindow.setResizable(false);
                        newWindow.requestFocus();
                        newWindow.setScene(secondScene);
                        newWindow.toFront();
                        newWindow.show();

                    });
                }
                return true;
            }
        });
        key.setKeyReleased(new Function<>() {
            @Override
            public Boolean apply(NativeKeyEvent d) {
                if (d.getKey() == CTRL) {
                    ctrl = false;
                }
                if (d.getKey() == ALT) {
                    alt = false;
                }
                if (d.getKey() == X) {
                    x = false;
                }
                return true;
            }
        });
        key.startListening();
        // });
        // thread.start();
    }

    @Override
    public void exec(Command command) {
        newWindow.close();
        AppData.instance.runCommand(command.getAlias());
    }
}

//public class GlobalKeyListener implements NativeKeyListener {
//
//    private boolean ctrl = false;
//    private boolean alt = false;
//    private boolean x = false;
//
//    public void nativeKeyPressed(NativeKeyEvent e) {
//        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
//            ctrl = true;
//        }
//        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
//            alt = true;
//        }
//        if (e.getKeyCode() == NativeKeyEvent.VC_X) {
//            x = true;
//        }
//
//        if (ctrl && alt && x) {
//            Platform.runLater(() -> {
//                Stage newWindow = new Stage(StageStyle.UTILITY);
//                newWindow.setTitle("Exec command");
//                newWindow.initModality(Modality.APPLICATION_MODAL);
//
//                TextField text = new TextField();
//                text.setPromptText("Type a command");
//                text.setMinHeight(60);
//                text.setMinWidth(400);
//                text.setStyle("-fx-text-box-border: #242424;-fx-border-width: 5px; -fx-focus-color: #242424;");
//                text.setFont(new Font(24));
//                text.requestFocus();
//                text.setOnKeyPressed(ke -> {
//                    if (ke.getCode().equals(KeyCode.ESCAPE)) {
//                        newWindow.close();
//                    }
//                    if (ke.getCode().equals(KeyCode.ENTER)) {
//                        String command = text.getText();
//                        newWindow.close();
//                        AppData.instance.runCommand(command);
//                    }
//                });
//
//                StackPane secondaryLayout = new StackPane();
//                secondaryLayout.getChildren().add(text);
//
//                Scene secondScene = new Scene(secondaryLayout);
//
//                newWindow.setAlwaysOnTop(true);
//                newWindow.setResizable(false);
//                newWindow.requestFocus();
//                newWindow.setScene(secondScene);
//                newWindow.toFront();
//                newWindow.show();
//
//            });
//        }
//    }
//
//    public void nativeKeyReleased(NativeKeyEvent e) {
//
//        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
//            ctrl = false;
//        }
//        if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
//            alt = false;
//        }
//        if (e.getKeyCode() == NativeKeyEvent.VC_X) {
//            x = false;
//        }
//    }
//
//    public void nativeKeyTyped(NativeKeyEvent e) {
//    }
//
//}

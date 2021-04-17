package cloud.dest.terminal.global_input;

import cloud.dest.terminal.command.Command;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.util.StringConverter;

import java.util.Optional;

public class AutoCompleteBox implements EventHandler<KeyEvent> {
    private ComboBox<Command> comboBox;
    final private ObservableList<Command> data;
    private ExecCommand execCommand;
    private Integer sid;
    private boolean executed;

    public AutoCompleteBox(final ComboBox<Command> comboBox, ExecCommand execCommand) {
        this(comboBox, execCommand, null);
    }

    public AutoCompleteBox(final ComboBox<Command> comboBox, ExecCommand execCommand, Integer sid) {
        this.comboBox = comboBox;
        this.data = comboBox.getItems();
        this.execCommand = execCommand;
        this.sid = sid;
        this.executed = false;
        this.doAutoCompleteBox();
    }

    private void doAutoCompleteBox() {
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Command command) {
                return commandToString(command);
            }

            @Override
            public Command fromString(String s) {
                return findCommand(s).orElseGet(() -> new Command(s));
            }
        });

        this.comboBox.setEditable(true);
        this.comboBox.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {//mean onfocus
                this.comboBox.show();
            }
        });

        this.comboBox.getEditor().setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    return;
                }
            }
            this.comboBox.show();
        });

        this.comboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            moveCaret(this.comboBox.getEditor().getText().length());
        });

        this.comboBox.setOnKeyPressed(t -> comboBox.hide());

        this.comboBox.setOnKeyReleased(AutoCompleteBox.this);

        if (this.sid != null)
            this.comboBox.getSelectionModel().select(this.sid);
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN
                || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB
        ) {
            return;
        }

        if (!executed && event.getCode() == KeyCode.ENTER) {
            Command command = comboBox.getValue();
            executed = true;
            execCommand.exec(command);
            return;
        }
        if (event.getCode() == KeyCode.BACK_SPACE) {
            String str = this.comboBox.getEditor().getText();
            if (str != null && str.length() > 0) {
                str = str.substring(0, str.length() - 1);
            }
            if (str != null) {
                this.comboBox.getEditor().setText(str);
                moveCaret(str.length());
            }
            this.comboBox.getSelectionModel().clearSelection();
        }

        if (event.getCode() == KeyCode.ENTER && comboBox.getSelectionModel().getSelectedIndex() > -1)
            return;

        setItems();
    }

    private Optional<Command> findCommand(String value) {
        return data.stream().filter(command -> commandToString(command).equals(value)).findFirst();
    }

    private void setItems() {
        ObservableList<Command> list = FXCollections.observableArrayList();

        for (Command command : this.data) {
            String s = this.comboBox.getEditor().getText().toLowerCase();
            if (commandToString(command).toLowerCase().contains(s.toLowerCase())) {
                list.add(command);
            }
        }

        if (list.isEmpty()) this.comboBox.hide();

        this.comboBox.setItems(list);
        this.comboBox.show();
    }

    private void moveCaret(int textLength) {
        this.comboBox.getEditor().positionCaret(textLength);
    }

    private String commandToString(Command command) {
        if (command == null) {
            return "";
        } else {
            return command.getName() + "(" + command.getAlias() + ")";
        }
    }

}
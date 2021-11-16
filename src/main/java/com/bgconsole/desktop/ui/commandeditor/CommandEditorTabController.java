package com.bgconsole.desktop.ui.commandeditor;

import com.bgconsole.desktop.AppData;
import com.bgconsole.desktop.command.Command;
import com.bgconsole.desktop.command.CommandList;
import com.bgconsole.desktop.utils.WriteYAMLFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandEditorTabController {

    @FXML
    private ListView<String> comList;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField aliasField;

    @FXML
    private TextArea commandArea;

    @FXML
    private TextField shellField;

    @FXML
    private TextField consoleIdField;

    @FXML
    private TextField beforeField;

    @FXML
    private TextField afterField;

    @FXML
    private Button editButton;

    @FXML
    private Button delButton;

    @FXML
    private Button upButton;

    @FXML
    private Button downButton;

    private AppData appData;

    private CommandList commandList;

    private String absolutePath;

    private List<Command> commands;

    private Command command;

    private boolean isEditing = false;

    public void initialize() {
        appData = AppData.instance;
    }

    public void setCommandList(CommandList commandList) {
        this.commandList = commandList;
        this.absolutePath = commandList.getAbsolutePath();
        commands = new ArrayList<>(commandList.getCommands());
        comList.getItems().addAll(commands.stream().map(Command::getName).collect(Collectors.toList()));
        comList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 1) {
                editCom(comList.getSelectionModel().getSelectedIndex());
            }
        });
    }

    @FXML
    public void add(ActionEvent event) {
        newCom();
    }

    @FXML
    public void delete(ActionEvent event) {
        int pos = comList.getSelectionModel().getSelectedIndex();
        commands.remove(pos);
        comList.getItems().remove(pos);
        if (commands.isEmpty()) {
            newCom();
        } else {
            editCom(pos - 1);
        }
        saveList();
        enableButtons();
    }

    @FXML
    public void moveUp(ActionEvent event) {
        int pos = comList.getSelectionModel().getSelectedIndex();
        Command com = commands.remove(pos);
        commands.add(pos - 1, com);
        String item = comList.getItems().remove(pos);
        comList.getItems().add(pos - 1, item);
        comList.getSelectionModel().select(pos - 1);
        saveList();
        enableButtons();
    }

    @FXML
    public void moveDown(ActionEvent event) {
        int pos = comList.getSelectionModel().getSelectedIndex();
        Command com = commands.remove(pos);
        commands.add(pos + 1, com);
        String item = comList.getItems().remove(pos);
        comList.getItems().add(pos + 1, item);
        comList.getSelectionModel().select(pos + 1);
        saveList();
        enableButtons();
    }

    @FXML
    public void saveChanges(ActionEvent event) {
        if (isEditing) {
            buildCommand(command);
            saveList();
        } else {
            command = new Command();
            buildCommand(command);
            comList.getItems().add(command.getName());
            commands.add(command);
            saveList();
            comList.getSelectionModel().selectLast();
            editCom(comList.getItems().size() - 1);
        }
    }

    private void buildCommand(Command command) {
        command.setId(idField.getText());
        command.setName(nameField.getText());
        command.setAlias(aliasField.getText());
        command.setCommand(commandArea.getText());
        command.setShellType(shellField.getText());
        command.setConsoleId(consoleIdField.getText());
        command.setExecBefore(beforeField.getText());
        command.setExecAfter(afterField.getText());
    }

    private void newCom() {
        command = null;
        isEditing = false;
        idField.setText("");
        nameField.setText("");
        aliasField.setText("");
        commandArea.setText("");
        shellField.setText("");
        consoleIdField.setText("");
        beforeField.setText("");
        afterField.setText("");
        editButton.setText("Add");
        enableButtons();
    }

    private void editCom(int pos) {
        command = commands.get(pos);
        idField.setText(command.getId());
        nameField.setText(command.getName());
        aliasField.setText(command.getAlias());
        commandArea.setText(command.getCommand());
        shellField.setText(command.getShellType());
        consoleIdField.setText(command.getConsoleId());
        beforeField.setText(command.getExecBefore());
        afterField.setText(command.getExecAfter());
        editButton.setText("Modify");
        isEditing = true;
        enableButtons();
    }

    private void enableButtons() {
        if (comList.getSelectionModel().isEmpty()) {
            delButton.setDisable(true);
            upButton.setDisable(true);
            downButton.setDisable(true);
        } else {
            int pos = comList.getSelectionModel().getSelectedIndex();
            delButton.setDisable(false);
            upButton.setDisable(pos == 0);
            downButton.setDisable(pos == comList.getItems().size() - 1);
        }
    }

    private void saveList() {
        try {
            WriteYAMLFile.writeCommands(commands, absolutePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

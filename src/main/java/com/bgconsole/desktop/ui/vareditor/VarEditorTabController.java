package com.bgconsole.desktop.ui.vareditor;

import com.bgconsole.desktop.AppData;
import com.bgconsole.desktop.Config;
import com.bgconsole.desktop.utils.WriteYAMLFile;
import com.bgconsole.desktop.variable.Variable;
import com.bgconsole.desktop.variable.VariableList;
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

public class VarEditorTabController {

    @FXML
    private ListView<String> varList;

    @FXML
    private TextField varField;

    @FXML
    private TextArea valArea;

    @FXML
    private Button editButton;

    @FXML
    private Button delButton;

    @FXML
    private Button upButton;

    @FXML
    private Button downButton;

    private AppData appData;

    private VariableList variableList;

    private Config config;

    private List<Variable> variables;

    private Variable variable;

    private boolean isEditing = false;

    public void initialize() {
        appData = AppData.instance;
    }

    public void setVariableList(VariableList variableList) {
        this.variableList = variableList;
        config = variableList.getConfig();
        variables = new ArrayList<>(variableList.getVariables());
        varList.getItems().addAll(variables.stream().map(Variable::getVariable).collect(Collectors.toList()));
        varList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 1) {
                editVar(varList.getSelectionModel().getSelectedIndex());
            }
        });
    }

    @FXML
    public void add(ActionEvent event) {
        newVar();
    }

    @FXML
    public void delete(ActionEvent event) {
        int pos = varList.getSelectionModel().getSelectedIndex();
        variables.remove(pos);
        varList.getItems().remove(pos);
        if (variables.isEmpty()) {
            newVar();
        } else {
            editVar(pos - 1);
        }
        saveList();
        enableButtons();
    }

    @FXML
    public void moveUp(ActionEvent event) {
        int pos = varList.getSelectionModel().getSelectedIndex();
        Variable var = variables.remove(pos);
        variables.add(pos - 1, var);
        String item = varList.getItems().remove(pos);
        varList.getItems().add(pos - 1, item);
        varList.getSelectionModel().select(pos - 1);
        saveList();
        enableButtons();
    }

    @FXML
    public void moveDown(ActionEvent event) {
        int pos = varList.getSelectionModel().getSelectedIndex();
        Variable var = variables.remove(pos);
        variables.add(pos + 1, var);
        String item = varList.getItems().remove(pos);
        varList.getItems().add(pos + 1, item);
        varList.getSelectionModel().select(pos + 1);
        saveList();
        enableButtons();
    }

    @FXML
    public void saveChanges(ActionEvent event) {
        if (isEditing) {
            variable.setVariable(varField.getText());
            variable.setValue(valArea.getText());
            saveList();
        } else {
            variable = new Variable();
            variable.setVariable(varField.getText());
            variable.setValue(valArea.getText());
            varList.getItems().add(variable.getVariable());
            variables.add(variable);
            saveList();
            varList.getSelectionModel().selectLast();
            editVar(varList.getItems().size() - 1);
        }
    }

    private void newVar() {
        variable = null;
        isEditing = false;
        varField.setText("");
        valArea.setText("");
        editButton.setText("Add");
        enableButtons();
    }

    private void editVar(int pos) {
        variable = variables.get(pos);
        varField.setText(variable.getVariable());
        valArea.setText(variable.getValue());
        editButton.setText("Modify");
        isEditing = true;
        enableButtons();
    }

    private void enableButtons() {
        if (varList.getSelectionModel().isEmpty()) {
            delButton.setDisable(true);
            upButton.setDisable(true);
            downButton.setDisable(true);
        } else {
            int pos = varList.getSelectionModel().getSelectedIndex();
            delButton.setDisable(false);
            upButton.setDisable(pos == 0);
            downButton.setDisable(pos == varList.getItems().size() - 1);
        }

    }

    private void saveList() {
        try {
            WriteYAMLFile.writeVariables(variables, config.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

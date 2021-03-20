package cloud.dest.terminal;

import com.kodedu.terminalfx.TerminalTab;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FXMLController {

    @FXML
    private StackPane termPane;

    @FXML
    private ListView<String> commandList;

    @FXML
    private TextField commandField;

    @FXML
    private Menu configMenu;

    private TabPane tabPane;
    private TerminalList terminalList;
    private CommandList commands;
    private VariableList variables;
    private List<Config> configs;
    private Config currentConfig;

    public FXMLController() {
        currentConfig = null;
    }

    public void initialize() {
        tabPane = new TabPane();
        terminalList = new TerminalList();
        commands = new CommandList();
        variables = new VariableList();
        configs = loadConfigs(new ArrayList<>(), Paths.get(System.getProperty("user.home"), ".console"));

        buildConfigMenu(configs, configMenu);

        termPane.getChildren().add(tabPane);
    }

    @FXML
    public void onEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String command = commandField.getText();
            Command alias = findAlias(command);
            if (alias != null) {
                execCommandInRightTerminal(tabPane, alias);
                commandField.setText("");
            } else {
                TerminalTab terminalTab = (TerminalTab) tabPane.getSelectionModel().getSelectedItem();
                if (terminalTab != null) {
                    sendCommand(terminalTab, command);
                    commandField.setText("");
                }
            }
        }
    }

    @FXML
    public void newTerminal(ActionEvent event) {
        Terminal terminal = terminalList.getOrOpen("term" + tabPane.getTabs().size() + 1, "Terminal " + tabPane.getTabs().size() + 1);
        addClosingEvent(terminal);
        tabPane.getTabs().add(terminal.getTerminalTab());
        tabPane.getSelectionModel().select(terminal.getTerminalTab());
    }

    @FXML
    public void quit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void reloadConfig(ActionEvent event) {
        if (currentConfig != null) {
            loadVariable(currentConfig);
        }
    }

    private void buildConfigMenu(List<Config> configs, Menu menu) {
        MenuItem item;
        Menu subMenu;
        for (Config config : configs) {
            if (config.getType().equals("file")) {
                item = new MenuItem(config.getConfig());
                item.setOnAction(t -> {
                    currentConfig = config;
                    loadVariable(config);
                });
                menu.getItems().add(item);
            } else {
                subMenu = new Menu(config.getConfig());
                buildConfigMenu(config.getConfigs(), subMenu);
                menu.getItems().add(subMenu);
            }
        }
    }

    private void execCommandInRightTerminal(TabPane tabPane, Command command) {
        Variable title = findVar(variables.getVariables(), "TITLE:" + command.getConsoleId());
        Terminal terminal = terminalList.getOrOpen(command.getConsoleId(), title != null && !title.getValue().isBlank() ? title.getValue() : command.getConsoleId());
        if (terminal != null) {
            addClosingEvent(terminal);
            TerminalTab terminalTab = findTab(tabPane, command.getConsoleId());
            if (terminalTab != null) {
                tabPane.getSelectionModel().select(terminalTab);
            } else {
                tabPane.getTabs().add(terminal.getTerminalTab());
                tabPane.getSelectionModel().select(terminal.getTerminalTab());
            }
            sendCommand(terminal.getTerminalTab(), command.getCommand());
        }
    }

    private void addClosingEvent(Terminal terminal) {
        terminal.getTerminalTab().setOnCloseRequest(event1 -> {
            terminalList.closeTerminal(terminal);
        });
    }

    private TerminalTab findTab(TabPane tabPane, String terminalId) {
        for (int i = 0; i < tabPane.getTabs().size(); i++) {
            TerminalTab terminalTab = (TerminalTab) tabPane.getTabs().get(i);
            if (terminalTab.getId() != null && terminalTab.getId().equals(terminalId)) {
                return terminalTab;
            }
        }
        return null;
    }

    private Command findAlias(String alias) {
        for (Command command : commands.getCommands()) {
            if (command.getAlias().equals(alias)) {
                return command;
            }
        }
        return null;
    }

    private void sendCommand(TerminalTab terminalTab, String command) {
        if (command.contains("${")) {
            resolveVariable(terminalTab, command);
        } else {
            terminalTab.onTerminalFxReady(() -> terminalTab.getTerminal().command(command + "\r"));
        }
    }

    private void resolveVariable(TerminalTab terminalTab, String command) {
        int pos1 = command.indexOf("${");
        int pos2 = command.indexOf("}", pos1);
        String var = command.substring(pos1 + 2, pos2);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Argument required");
        dialog.setHeaderText("An arugment is required, please enter the following value");
        dialog.setContentText("Value for \"" + var + "\"");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(value -> sendCommand(terminalTab, command.replace("${" + var + "}", value)));
    }

    private void replaceVars(List<Command> commands, List<Variable> vars) {
        for (Command command : commands) {
            for (Variable var : vars) {
                command.setCommand(command.getCommand().replace("${" + var.getVariable() + "}", var.getValue()));
            }
        }
    }

    private List<Config> loadConfigs(List<Config> configs, Path dir) {
        File currentDir = dir.toFile();
        File[] files = currentDir.listFiles();
        Config config;
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (!file.getName().startsWith(".")) {
                        config = new Config("dir", file.getName(), file.getAbsolutePath());
                        configs.add(config);
                        loadConfigs(config.getConfigs(), file.toPath());
                    }
                } else {
                    configs.add(new Config("file", file.getName(), file.getAbsolutePath()));
                }
            }
        }
        return configs;
    }

    private void loadVariable(Config config) {
        try {
            variables.setNewVariables(ParseJsonFile.readVariables(config.getAbsolutePath()));
            Variable commandFile = findVar(variables.getVariables(), "COMMAND_FILE");
            commandList.getItems().clear();
            if (commandFile != null) {
                commands.setNewList(ParseJsonFile.readCommands(commandFile.getValue()));
                commands.getCommands().forEach(command -> commandList.getItems().add(command.getName() + " (" + command.getAlias() + ")"));
                commandList.setOnMouseClicked(click -> {
                    if (click.getClickCount() == 2) {
                        Command command = commands.getCommands().get(commandList.getSelectionModel().getSelectedIndex());
                        execCommandInRightTerminal(tabPane, command);
                    }
                });
            }
            replaceVars(commands.getCommands(), variables.getVariables());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Variable findVar(List<Variable> variables, String var) {
        for (Variable variable : variables) {
            if (variable.getVariable().equals(var)) {
                return variable;
            }
        }
        return null;
    }

}

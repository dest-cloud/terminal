package cloud.dest.terminal;

import cloud.dest.terminal.command.Command;
import cloud.dest.terminal.command.CommandList;
import cloud.dest.terminal.command.CommandService;
import cloud.dest.terminal.config.ConfigService;
import cloud.dest.terminal.terminal.OpenerCallBack;
import cloud.dest.terminal.terminal.Terminal;
import cloud.dest.terminal.terminal.TerminalService;
import cloud.dest.terminal.variable.Variable;
import cloud.dest.terminal.variable.VariableService;
import com.kodedu.terminalfx.TerminalTab;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.nio.file.Path;
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

    private final AppData appData;
    private final CommandService commandService;
    private final TerminalService terminalService;
    private final VariableService variableService;
    private final ConfigService configService;

    private TabPane tabPane;

    private List<Config> configs;

    public FXMLController() {
        appData = AppData.instance;
        commandService = appData.getCommandService();
        terminalService = appData.getTerminalService();
        variableService = appData.getVariableService();
        configService = appData.getConfigService();
    }

    public void initialize() {
        tabPane = new TabPane();
        configs = loadConfigs(new ArrayList<>(), appData.getEnvironment().getDir());

        buildConfigMenu(configs, configMenu);

        termPane.getChildren().add(tabPane);
    }

    @FXML
    public void onEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String command = commandField.getText();
            Optional<Command> alias = commandService.findAlias(appData.getEnvironment(), command);
            if (alias.isPresent()) {
                execCommandInRightTerminal(tabPane, alias.get());
                commandField.setText("");
            } else {
                TerminalTab terminalTab = (TerminalTab) tabPane.getSelectionModel().getSelectedItem();
                if (terminalTab != null) {
                    commandService.sendCommand(terminalTab, command, this::resolveVariable);
                    commandField.setText("");
                }
            }
        }
    }

    @FXML
    public void newTerminal(ActionEvent event) {
        openTerminal("term" + (tabPane.getTabs().size() + 1), "Terminal " + (tabPane.getTabs().size() + 1), (terminal, isNew) -> {
            tabPane.getTabs().add(terminal.getTerminalTab());
            tabPane.getSelectionModel().select(terminal.getTerminalTab());
        });
    }

    @FXML
    public void quit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void reloadConfig(ActionEvent event) {
        appData.reloadEnv();
    }

    private void openTerminal(String id, String name, OpenerCallBack callBack) {
        terminalService.openTerminal(appData.getEnvironment().getTerminalList(), id, name, (terminal, isNew) -> {
            addClosingEvent(terminal);
            callBack.openerCallBack(terminal, isNew);
        });
    }

    private void buildConfigMenu(List<Config> configs, Menu menu) {
        MenuItem item;
        Menu subMenu;
        for (Config config : configs) {
            if (config.getType().equals("file")) {
                item = new MenuItem(config.getConfig());
                item.setOnAction(t -> {
                    configService.loadConfig(appData.getEnvironment(), config);
                    loadConfig();
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
        Optional<Variable> optTitle = variableService.findVar(appData.getEnvironment(), "TITLE:" + command.getConsoleId());
        String title = command.getConsoleId();
        if (optTitle.isPresent() && optTitle.get().getValue() != null && !optTitle.get().getValue().isBlank()) {
            title = optTitle.get().getValue();
        }

        openTerminal(command.getConsoleId(), title, (terminal, isNew) -> {
            TerminalTab terminalTab = findTab(tabPane, command.getConsoleId());
            if (terminalTab != null) {
                tabPane.getSelectionModel().select(terminalTab);
            } else {
                tabPane.getTabs().add(terminal.getTerminalTab());
                tabPane.getSelectionModel().select(terminal.getTerminalTab());
            }
            commandService.sendCommand(terminal.getTerminalTab(), command.getCommand(), this::resolveVariable);
        });
    }

    private void addClosingEvent(Terminal terminal) {
        terminal.getTerminalTab().setOnCloseRequest(event1 -> {
            terminalService.closeTerminal(appData.getEnvironment().getTerminalList(), terminal);
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

    private Optional<String> resolveVariable(String var) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Argument required");
        dialog.setHeaderText("An arugment is required, please enter the following value");
        dialog.setContentText("Value for \"" + var + "\"");
        return dialog.showAndWait();
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

    private void loadConfig() {
        commandList.getItems().clear();
        for (CommandList commands : appData.getEnvironment().getCommandLists()) {
            commands.getCommands().forEach(command -> commandList.getItems().add(command.getName() + " (" + command.getAlias() + ")"));
            commandList.setOnMouseClicked(click -> {
                if (click.getClickCount() == 2) {
                    Command command = commands.getCommands().get(commandList.getSelectionModel().getSelectedIndex());
                    execCommandInRightTerminal(tabPane, command);
                }
            });
        }
    }

}

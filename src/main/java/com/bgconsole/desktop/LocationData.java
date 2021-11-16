package com.bgconsole.desktop;

import com.bgconsole.desktop.command.CommandService;
import com.bgconsole.desktop.command.CommandServiceImpl;
import com.bgconsole.desktop.config.ConfigService;
import com.bgconsole.desktop.config.ConfigServiceImpl;
import com.bgconsole.desktop.environment.Environment;
import com.bgconsole.desktop.environment.EnvironmentService;
import com.bgconsole.desktop.environment.EnvironmentServiceImpl;
import com.bgconsole.desktop.location.Location;
import com.bgconsole.desktop.terminal.TerminalService;
import com.bgconsole.desktop.terminal.TerminalServiceImpl;
import com.bgconsole.desktop.variable.VariableService;
import com.bgconsole.desktop.variable.VariableServiceImpl;
import com.bgconsole.desktop.workspace.WorkspaceService;
import com.bgconsole.desktop.workspace.WorkspaceServiceImpl;

import java.nio.file.Paths;
import java.util.List;

public class LocationData {

    private final Location location;

    private final EnvironmentService environmentService;

    private final CommandService commandService;

    private final TerminalService terminalService;

    private final VariableService variableService;

    private final WorkspaceService workspaceService;

    private final ConfigService configService;

    private Environment environment;

    private CommandRunner commandRunner;

    public LocationData(Location location) {
        this.location = location;
        commandService = new CommandServiceImpl();
        variableService = new VariableServiceImpl();
        terminalService = new TerminalServiceImpl(variableService, commandService);
        workspaceService = new WorkspaceServiceImpl();
        environmentService = new EnvironmentServiceImpl(variableService, workspaceService, commandService);
        configService = new ConfigServiceImpl(variableService, commandService);
        environment = environmentService.initEnv(location.getId(), Paths.get(location.getPath(), "workspace.yaml"));
    }

    public void reloadEnv() {
        List<Config> configs = environment.getConfigs();
        environment = environmentService.initEnv(location.getId(), Paths.get(location.getPath(), "workspace.yaml"));
        for (Config config : configs) {
            configService.loadConfig(environment, config);
        }
    }

    public void setCommandRunner(CommandRunner commandRunner) {
        this.commandRunner = commandRunner;
    }

    public void runCommand(String command) {
        if (commandRunner != null) {
            commandRunner.exec(command);
        }
    }

    public EnvironmentService getEnvironmentService() {
        return environmentService;
    }

    public ConfigService getConfigService() {
        return configService;
    }

    public CommandService getCommandService() {
        return commandService;
    }

    public TerminalService getTerminalService() {
        return terminalService;
    }

    public VariableService getVariableService() {
        return variableService;
    }

    public Environment getEnvironment() {
        return environment;
    }
}

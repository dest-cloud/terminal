package cloud.dest.terminal;

import cloud.dest.terminal.command.CommandService;
import cloud.dest.terminal.command.CommandServiceImpl;
import cloud.dest.terminal.config.ConfigService;
import cloud.dest.terminal.config.ConfigServiceImpl;
import cloud.dest.terminal.environment.Environment;
import cloud.dest.terminal.environment.EnvironmentService;
import cloud.dest.terminal.environment.EnvironmentServiceImpl;
import cloud.dest.terminal.location.Location;
import cloud.dest.terminal.terminal.TerminalService;
import cloud.dest.terminal.terminal.TerminalServiceImpl;
import cloud.dest.terminal.variable.VariableService;
import cloud.dest.terminal.variable.VariableServiceImpl;
import cloud.dest.terminal.workspace.WorkspaceService;
import cloud.dest.terminal.workspace.WorkspaceServiceImpl;

import java.nio.file.Paths;
import java.util.List;

public class LocationData {

    private Location location;

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

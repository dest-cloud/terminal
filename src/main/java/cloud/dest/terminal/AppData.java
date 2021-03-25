package cloud.dest.terminal;

import cloud.dest.terminal.command.CommandService;
import cloud.dest.terminal.command.CommandServiceImpl;
import cloud.dest.terminal.config.ConfigService;
import cloud.dest.terminal.config.ConfigServiceImpl;
import cloud.dest.terminal.environment.Environment;
import cloud.dest.terminal.environment.EnvironmentService;
import cloud.dest.terminal.environment.EnvironmentServiceImpl;
import cloud.dest.terminal.terminal.TerminalService;
import cloud.dest.terminal.terminal.TerminalServiceImpl;
import cloud.dest.terminal.variable.VariableService;
import cloud.dest.terminal.variable.VariableServiceImpl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AppData {

    private static final Path DEFAULT_DIR = Paths.get(System.getProperty("user.home"), ".cloud.dest.terminal", "profiles");

    private final EnvironmentService environmentService;

    private final CommandService commandService;

    private final TerminalService terminalService;

    private final VariableService variableService;

    private final ConfigService configService;

    public static final AppData instance = new AppData();

    private Environment environment;

    private CommandRunner commandRunner;

    private AppData() {
        commandService = new CommandServiceImpl();
        variableService = new VariableServiceImpl();
        terminalService = new TerminalServiceImpl(variableService, commandService);
        environmentService = new EnvironmentServiceImpl(variableService);
        configService = new ConfigServiceImpl(variableService, commandService);
        environment = environmentService.initEnv("Default", DEFAULT_DIR);
    }

    public void reloadEnv() {
        List<Config> configs = environment.getConfigs();
        environment = environmentService.initEnv("Default", DEFAULT_DIR);
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

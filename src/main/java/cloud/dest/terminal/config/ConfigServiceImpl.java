package cloud.dest.terminal.config;

import cloud.dest.terminal.Config;
import cloud.dest.terminal.command.CommandList;
import cloud.dest.terminal.command.CommandService;
import cloud.dest.terminal.environment.Environment;
import cloud.dest.terminal.variable.Variable;
import cloud.dest.terminal.variable.VariableList;
import cloud.dest.terminal.variable.VariableService;

public class ConfigServiceImpl implements ConfigService {

    private final VariableService variableService;

    private final CommandService commandService;

    public ConfigServiceImpl(VariableService variableService, CommandService commandService) {
        this.variableService = variableService;
        this.commandService = commandService;
    }

    @Override
    public Environment loadConfig(Environment environment, Config config) {
        VariableList variables = variableService.loadVariables(config);
        environment.getVariableLists().add(variables);
        for (Variable variable : variableService.findVars(environment, "COMMAND_FILE")) {
            String commandFile = variable.getValue();
            if (commandFile != null && !commandFile.isBlank()) {
                CommandList commands = commandService.loadCommands(commandFile);
                environment.getCommandLists().add(commands);
            }
        }
        for (VariableList variableList : environment.getVariableLists()) {
            for (CommandList commandList : environment.getCommandLists()) {
                commandList.setNewList(commandService.replaceVars(variableList.getVariables(), commandList.getCommands()));
            }
        }
        return environment;
    }
}

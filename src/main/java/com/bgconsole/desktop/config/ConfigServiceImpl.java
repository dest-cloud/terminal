package com.bgconsole.desktop.config;

import com.bgconsole.desktop.Config;
import com.bgconsole.desktop.command.CommandList;
import com.bgconsole.desktop.command.CommandService;
import com.bgconsole.desktop.environment.Environment;
import com.bgconsole.desktop.variable.Variable;
import com.bgconsole.desktop.variable.VariableList;
import com.bgconsole.desktop.variable.VariableService;

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
        environment.getConfigs().clear();
        environment.getConfigs().add(config);
        return environment;
    }
}

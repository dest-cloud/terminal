package com.bgconsole.desktop.environment;

import com.bgconsole.desktop.Config;
import com.bgconsole.desktop.command.CommandList;
import com.bgconsole.desktop.command.CommandService;
import com.bgconsole.desktop.variable.VariableList;
import com.bgconsole.desktop.variable.VariableService;
import com.bgconsole.desktop.workspace.Workspace;
import com.bgconsole.desktop.workspace.WorkspaceCommand;
import com.bgconsole.desktop.workspace.WorkspaceService;
import com.bgconsole.desktop.workspace.WorkspaceVariable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EnvironmentServiceImpl implements EnvironmentService {

    private final VariableService variableService;

    private final CommandService commandService;

    private final WorkspaceService workspaceService;

    public EnvironmentServiceImpl(VariableService variableService, WorkspaceService workspaceService, CommandService commandService) {
        this.variableService = variableService;
        this.workspaceService = workspaceService;
        this.commandService = commandService;
    }

    @Override
    public Environment initEnv(String id, Path dir) {
        Workspace workspace = workspaceService.loadWorkspace(dir.toString());

        Environment environment = new Environment(id, dir, workspace);
        VariableList sysVar = new VariableList(new Config("file", "SYS_VAR", ""));
        sysVar.setNewVariables(variableService.getSystemVariable());
        environment.getVariableLists().add(sysVar);
        environment.getVariableLists().addAll(loadVariables(workspace));
        environment.getCommandLists().addAll(loadCommands(environment,workspace));
        return environment;
    }

    private List<CommandList> loadCommands(Environment environment,Workspace workspace) {
        List<CommandList> commands = new ArrayList<>();
        for (WorkspaceCommand command : workspace.getCommands()) {
            CommandList commandList = commandService.loadCommands(command.getPath());
            commandList.setNewList(commandService.replaceAllVars(environment.getVariableLists(), commandList.getCommands()));
            commands.add(commandList);
        }
        return commands;
    }

    private List<VariableList> loadVariables(Workspace workspace) {
        List<VariableList> variables = new ArrayList<>();
        for (WorkspaceVariable variable : workspace.getVariables()) {
            variables.add(variableService.loadVariables(new Config("file", variable.getId(), variable.getPath())));
        }
        return variables;
    }

}

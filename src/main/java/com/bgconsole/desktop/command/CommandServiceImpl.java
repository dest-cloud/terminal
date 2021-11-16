package com.bgconsole.desktop.command;

import com.bgconsole.desktop.environment.Environment;
import com.bgconsole.desktop.utils.ParseYAMLFile;
import com.bgconsole.desktop.variable.Variable;
import com.bgconsole.desktop.variable.VariableList;
import com.kodedu.terminalfx.TerminalTab;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CommandServiceImpl implements CommandService {

    private static final String LINE_RETURN = System.getProperty("line.separator");

    @Override
    public CommandList loadCommands(String path) {
        CommandList commands = new CommandList(path);
        try {
            commands.setNewList(ParseYAMLFile.readCommands(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commands;
    }

    @Override
    public Optional<Command> findAlias(Environment environment, String alias) {
        for (CommandList commandList : environment.getCommandLists()) {
            for (Command command : commandList.getCommands()) {
                if (command.getAlias().equals(alias)) {
                    return Optional.of(command);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void sendCommand(TerminalTab terminalTab, String command, VariableResolver resolver) {
        String[] commands = command.split("\n");
        for (String cmd : commands) {
            sendSplitCommand(terminalTab, cmd, resolver);
        }
    }

    @Override
    public List<Command> replaceVars(List<Variable> vars, List<Command> commands) {
        for (Command command : commands) {
            for (Variable var : vars) {
                command.setCommand(command.getCommand().replace("${" + var.getVariable() + "}", var.getValue()));
            }
        }
        return commands;
    }

    @Override
    public List<Command> replaceAllVars(List<VariableList> vars, List<Command> commands) {
        for (VariableList variableList : vars) {
            commands = replaceVars(variableList.getVariables(), commands);
        }
        return commands;
    }

    private void sendSplitCommand(TerminalTab terminalTab, String command, VariableResolver resolver) {
        boolean abort = false;
        while (!abort && command.contains("${")) {
            int pos1 = command.indexOf("${");
            int pos2 = command.indexOf("}", pos1);
            String var = command.substring(pos1 + 2, pos2);
            var res = resolver.resolve(var);
            if (res.isPresent()) {
                command = command.replace("${" + var + "}", res.get());
            } else {
                abort = true;
            }
        }
        String finalCommand = command;
        terminalTab.onTerminalFxReady(() -> terminalTab.getTerminal().command(finalCommand + LINE_RETURN));
    }

}

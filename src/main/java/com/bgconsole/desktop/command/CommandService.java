package com.bgconsole.desktop.command;

import com.bgconsole.desktop.environment.Environment;
import com.bgconsole.desktop.variable.Variable;
import com.bgconsole.desktop.variable.VariableList;
import com.kodedu.terminalfx.TerminalTab;

import java.util.List;
import java.util.Optional;

public interface CommandService {

    CommandList loadCommands(String path);

    void sendCommand(TerminalTab terminalTab, String command, VariableResolver resolver);

    Optional<Command> findAlias(Environment environment, String alias);

    List<Command> replaceVars(List<Variable> vars, List<Command> commands);

    List<Command> replaceAllVars(List<VariableList> vars, List<Command> commands);

}

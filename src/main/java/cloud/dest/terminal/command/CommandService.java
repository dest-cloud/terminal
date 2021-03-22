package cloud.dest.terminal.command;

import cloud.dest.terminal.environment.Environment;
import cloud.dest.terminal.variable.Variable;
import com.kodedu.terminalfx.TerminalTab;

import java.util.List;
import java.util.Optional;

public interface CommandService {

    CommandList loadCommands(String path);

    void sendCommand(TerminalTab terminalTab, String command, VariableResolver resolver);

    Optional<Command> findAlias(Environment environment, String alias);

    List<Command> replaceVars(List<Variable> vars, List<Command> commands);

}

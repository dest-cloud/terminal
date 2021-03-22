package cloud.dest.terminal.terminal;

import cloud.dest.terminal.command.VariableResolver;
import cloud.dest.terminal.environment.Environment;

public interface TerminalService {

    void openTerminal(Environment environment, String id, String name, OpenerCallBack callBack, VariableResolver resolver);

    void closeTerminal(Environment environment, Terminal terminal);
}

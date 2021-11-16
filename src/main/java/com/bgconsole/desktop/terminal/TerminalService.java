package com.bgconsole.desktop.terminal;

import com.bgconsole.desktop.command.VariableResolver;
import com.bgconsole.desktop.environment.Environment;

public interface TerminalService {

    void openTerminal(Environment environment, String id, String name, OpenerCallBack callBack, VariableResolver resolver);

    void closeTerminal(Environment environment, Terminal terminal);
}

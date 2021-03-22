package cloud.dest.terminal.terminal;

public interface TerminalService {

    void openTerminal(TerminalList terminalList, String id, String name, OpenerCallBack callBack);

    void closeTerminal(TerminalList terminalList, Terminal terminal);
}

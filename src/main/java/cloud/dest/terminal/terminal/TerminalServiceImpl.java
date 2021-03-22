package cloud.dest.terminal.terminal;

public class TerminalServiceImpl implements TerminalService {

    @Override
    public void openTerminal(TerminalList terminalList, String id, String name, OpenerCallBack callBack) {
        terminalList.getOrOpen(id, name, callBack);
    }

    @Override
    public void closeTerminal(TerminalList terminalList, Terminal terminal) {
        terminalList.closeTerminal(terminal);
    }
}

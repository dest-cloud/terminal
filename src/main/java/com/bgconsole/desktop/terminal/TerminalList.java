package com.bgconsole.desktop.terminal;

import java.util.ArrayList;
import java.util.List;

public class TerminalList {

    private List<Terminal> terminals;

    public TerminalList() {
        terminals = new ArrayList<>();
    }

    public void getOrOpen(String id, OpenerCallBack callBack) {
        getOrOpen(id, id, callBack);
    }

    public void getOrOpen(String id, String name, OpenerCallBack callBack) {
        int pos = indexOf(id);
        if (pos >= 0) {
            callBack.openerCallBack(terminals.get(pos), false);
        } else {
            Terminal terminal = new Terminal(id, name);
            terminals.add(terminal);
            callBack.openerCallBack(terminal, true);
        }
    }

    public void closeTerminal(Terminal terminal) {
        int pos = indexOf(terminal.getId());
        if (pos >= 0) {
            terminals.remove(pos);
        }
    }

    private int indexOf(String id) {
        Terminal terminal;
        for (int i = 0; i < terminals.size(); i++) {
            terminal = terminals.get(i);
            if (terminal.getId() != null && terminal.getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

}

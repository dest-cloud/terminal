package cloud.dest.terminal;

import java.util.ArrayList;
import java.util.List;

public class TerminalList {

    private List<Terminal> terminals;

    public TerminalList() {
        terminals = new ArrayList<>();
    }

    public Terminal getOrOpen(String id) {
        return getOrOpen(id, id);
    }

    public Terminal getOrOpen(String id, String name) {
        int pos = indexOf(id);
        if (pos >= 0) {
            return terminals.get(pos);
        } else {
            Terminal terminal = new Terminal(id, name);
            terminals.add(terminal);
            return terminal;
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

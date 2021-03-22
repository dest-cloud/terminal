package cloud.dest.terminal.command;

import java.util.ArrayList;
import java.util.List;

public class CommandList {

    private List<Command> commands;

    public CommandList() {
        commands = new ArrayList<>();
    }

    public void setNewList(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> getCommands() {
        return commands;
    }
}
